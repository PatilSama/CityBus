package com.example.citybus;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citybus.ConstantClass.Model1;
import com.example.citybus.MapActivitys.ShowNearestStopMap_Activity;
import com.example.citybus.ModelClases.NearestBusStopModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Context context;
    GoogleMap googleMap;
    private boolean lctStatus = true;
    Marker currentMarker;
    RecyclerView rcNearBusStop;
    TextInputEditText searchFrom, searchTo;
    Button btnSearchBuses, btnSeeAllBuses;
    LinearLayout searchBusNumber, searchBusStop, nearestBusStop;
    CardView busNearYou;
//    ImageButton btnBackPage;
    TextView nearBusStopName,changeNearBusStop,showNearestStop;
    private ArrayList<NearestBusStopModel> nearestBusStopModelArrayList;
    //    String selectBusStop = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_layout);
        context = getApplicationContext();

        searchTo = findViewById(R.id.SearchTo);
        searchFrom = findViewById(R.id.searchFrom);
        btnSearchBuses = findViewById(R.id.btnSearchBuses);
        searchBusNumber = findViewById(R.id.searchBusNumber);
        searchBusStop = findViewById(R.id.searchBusStop);
        busNearYou = findViewById(R.id.busNearYou);
        btnSeeAllBuses = findViewById(R.id.btnSeeAllBuses);
        changeNearBusStop = findViewById(R.id.changeNearBusStop);
        nearBusStopName = findViewById(R.id.nearBusStopName);
        showNearestStop = findViewById(R.id.showNearestStop);

        // Show Nearest Stop Map.
        showNearestStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShowNearestStopMap_Activity.class));
            }
        });

        // Set Near Stop To Text, if ChangeNearStop.
        String ChangeNearStop = getIntent().getStringExtra("ChangeNearStop");
        if(ChangeNearStop!=null)
        {
            nearBusStopName.setText(ChangeNearStop);
        }

        // Change Near Bus Stop Event.
        changeNearBusStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ChangeNearBustStop.class));
            }
        });

        // Set Text From and To.
        String selectedStop = getIntent().getStringExtra("SelectStop");
        if (Model1.SelectType.equals("Search Stop")) {
            Model1.stop_From = selectedStop;
            searchFrom.setText(Model1.stop_From);
            searchTo.setText(Model1.stop_To);
        } else if (Model1.SelectType.equals("Search Destination Stop")) {
            Model1.stop_To = selectedStop;
            searchTo.setText(Model1.stop_To);
            searchFrom.setText(Model1.stop_From);
        }

        // Check All Buses which are active now.
        btnSeeAllBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SeeAllBuses.class));

            }
        });

        // See All Buses Are Around your Location.
        busNearYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BusAroundYouMap.class));
            }
        });

        // Search Buses by Bus Number.
        searchBusNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchBusNumber.class));
            }
        });

        // Search Bus Stop By Stop Name.
        searchBusStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchBusStop.class));
            }
        });

        // Button call to search buses using from and to stop name.
        btnSearchBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchBusesTextValidation()) {
                    startActivity(new Intent(MainActivity.this, RouteBusMap.class)
                            .putExtra("stopFrom", searchFrom.getText().toString().trim()).putExtra("stopTo", searchTo.getText().toString().trim()));
                }
            }
        });

        // Select Current location Stop.
        searchFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model1.SelectType = "Search Stop";
                Model1.stop_From = "";
                startActivity(new Intent(MainActivity.this, SelectStop.class));
            }
        });

        // Select Destination location stop.
        searchTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model1.SelectType = "Search Destination Stop";
                Model1.stop_To = "";
                startActivity(new Intent(MainActivity.this, SelectStop.class));
            }
        });
        rcNearBusStop = findViewById(R.id.rcNearBusStop);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Build RecyclerView.
        buildRecyclerView();
    }

    private void buildRecyclerView()
    {

        // Initialized Arraylist.
        nearestBusStopModelArrayList = new ArrayList<>();
        nearestBusStopModelArrayList.add(new NearestBusStopModel("Karve","1","A1"));
        nearestBusStopModelArrayList.add(new NearestBusStopModel("Warge","2","B2"));
        nearestBusStopModelArrayList.add(new NearestBusStopModel("Deccan","2","A2"));
        nearestBusStopModelArrayList.add(new NearestBusStopModel("Kothud","1","C1"));
        nearestBusStopModelArrayList.add(new NearestBusStopModel("Swargate","1.5","C3"));

        // Set Linear Manager for Screen.
        rcNearBusStop.setLayoutManager(new LinearLayoutManager(context));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcNearBusStop.getContext(),
//                new LinearLayoutManager(context).getOrientation());
//        rcNearBusStop.addItemDecoration(dividerItemDecoration);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.rcdevider_layout));
        rcNearBusStop.addItemDecoration(itemDecorator);
        // Invoke RecyclerView Adapter Class for set UI Data.
        rcNearBusStop.setAdapter(new RcNearBusStop(context,nearestBusStopModelArrayList));
    }

    // Check text is empty or not.
    private boolean searchBusesTextValidation() {
        if (searchFrom.getText().toString().isEmpty()) {
            searchFrom.setError("Select User Location");
            searchFrom.requestFocus();
        } else if (searchTo.getText().toString().isEmpty()) {
            searchTo.setError("Select Destination Location");
            searchTo.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    // Map Object initialized.
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (locationPermission()) {
            getCurrentUserLocation();
            //   accPolylineLocationServer();
        }
        // Call the BusNearStop Activity, click on Map.
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                startActivity(new Intent(MainActivity.this, BusAroundYouMap.class));
            }
        });
    }

    // Get Runtime Location permission.
    private boolean locationPermission() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION
                    , Manifest.permission.ACCESS_FINE_LOCATION}, 111);
            return true;
        } else {
            return true;
        }
    }

    // Get The User Current Location.
    private void getCurrentUserLocation() {
        // get the current status 1 location within 2000 milliSecond(2 Second) by this class.
        com.google.android.gms.location.LocationRequest locationRequest = new com.google.android.gms.location.LocationRequest()
                .setInterval(10000)
                .setFastestInterval(10000)
                .setPriority(LocationRequest.QUALITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    double currentLatitude = locationResult.getLastLocation().getLatitude();
                    double currentLongitude = locationResult.getLastLocation().getLongitude();
                    //    Toast.makeText(getApplicationContext(),currentLatitude+" : "+currentLongitude,Toast.LENGTH_SHORT).show();
                    currentLocationChange(currentLatitude, currentLongitude);
                }
            }
        }, Looper.getMainLooper());
    }

    private void currentLocationChange(double currentLatitude, double currentLongitude) {
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        if (currentMarker != null) {
            currentMarker.remove();
        }
        currentMarker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(currentLatitude, currentLongitude))
                .title("YOU ARE HERE!"));
        // .icon(bitmapDescriptorVector(getApplicationContext(), R.drawable.currentlocationbus)));
//        // create marker
//        MarkerOptions marker = new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Hello Maps");
//
//// adding marker
//        googleMap.addMarker(marker);
        if (lctStatus != false) {
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.5F));
            lctStatus = false;
        }

        // Add Stop marker.
//        for (int i = 0; i < stopLat.length; i++) {
//            arrayListStopsCord.add(i, new LatLng(stopLat[i], stopLng[i]));
//            marker = googleMap.addMarker(new MarkerOptions()
//                    .position(arrayListStopsCord.get(i))
////                    .title(arrayListStopsName.get(i))
//                    .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.bus_route)));  //  R.drawable.busstopicon1_foreground
//
//            //   calculateDist(calculateLat(i,Double.parseDouble(object.getString("latitude"))),Double.parseDouble(object.getString("longitude")));
//        }
    }
}