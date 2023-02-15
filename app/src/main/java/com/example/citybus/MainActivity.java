package com.example.citybus;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.citybus.ConstantClass.Model1;
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
    //    String selectBusStop = null;
    String[] nearStop = {"Karve", "Warge", "Deccan", "Kothud"}, nearStopDist = {"1", "2", "1", "2"};

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

        btnSeeAllBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SeeAllBuses.class));
            }
        });

        busNearYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, BusAroundYou.class));
            }
        });

        searchBusNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchBusNumber.class));
            }
        });
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
                if (searchBuses()) {
                    startActivity(new Intent(MainActivity.this, SearchBuses.class)
                            .putExtra("stopFrom", searchFrom.getText().toString().trim()).putExtra("stopTo", searchTo.getText().toString().trim()));
                }
            }
        });

        searchFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model1.SelectType = "Search Stop";
                Model1.stop_From = "";
                startActivity(new Intent(MainActivity.this, SelectStop.class));
            }
        });
        searchTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model1.SelectType = "Search Destination Stop";
                Model1.stop_To = "";
                startActivity(new Intent(MainActivity.this, SelectStop.class));
            }
        });
        rcNearBusStop = findViewById(R.id.rcNearBusStop);
        rcNearBusStop.setLayoutManager(new LinearLayoutManager(context));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcNearBusStop.getContext(),
//                new LinearLayoutManager(context).getOrientation());
//        rcNearBusStop.addItemDecoration(dividerItemDecoration);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.rcdevider_layout));
        rcNearBusStop.addItemDecoration(itemDecorator);
        rcNearBusStop.setAdapter(new RcNearBusStop(context, nearStop, nearStopDist));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private boolean searchBuses() {
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
        // Call the BusNearStop Activity.
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                startActivity(new Intent(MainActivity.this, BusAroundYou.class));
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