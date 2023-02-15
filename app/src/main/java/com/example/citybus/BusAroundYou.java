package com.example.citybus;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

public class BusAroundYou extends AppCompatActivity  implements OnMapReadyCallback {
    GoogleMap googleMap;
    Context context;
    Marker currentMarker;
    private boolean lctStatus = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busaroundyou_layout);
        context = getApplicationContext();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapBusAroundYou);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap=googleMap;
        if (locationPermission()) {
            getCurrentUserLocation();
            //   accPolylineLocationServer();
        }
    }

    // Get Runtime Location permission.
    private boolean locationPermission() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION
                    , Manifest.permission.ACCESS_FINE_LOCATION}, 111);
            return true;
        } else {
            return true;
        }
    }
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
