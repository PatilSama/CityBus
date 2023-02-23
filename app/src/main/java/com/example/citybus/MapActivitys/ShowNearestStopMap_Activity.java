package com.example.citybus.MapActivitys;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.citybus.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ShowNearestStopMap_Activity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    Context context;
    Marker currentMarker, marker;
    private boolean lctStatus = true;
    ArrayList<LatLng> busNearestStopLatLngList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showneareststoponmap_layout);
        context = getApplicationContext();

        buildMap();


    }

    private void getCurrentLocation() {
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
                    Log.d("", currentLatitude + "," + currentLongitude);
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
                .position(latLng)
                .title("YOU ARE HERE!"));
        // .icon(bitmapDescriptorVector(getApplicationContext(), R.drawable.currentlocationbus)));
        if (lctStatus != false) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));
            //  googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5F));
            lctStatus = false;
        }
    }

    private void buildMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.nearBusStopName);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        showBusStop();
        if (getLocationPermission()) {
            getCurrentLocation();
        }

        this.googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                // int position = Integer.valueOf((String) marker.getTag());
                Toast.makeText(ShowNearestStopMap_Activity.this, "" + marker.getTag(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public boolean getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 111);
            return true;
        } else {
            return true;
        }
    }

    private void showBusStop() {
        // Stop Latitude and Longitude.
        double[] stopLat = {18.46766, 18.472547, 18.481437, 18.492358, 18.500799, 18.512430}, stopLng = {73.86433, 73.863015, 73.861022, 73.857634, 73.856640, 73.843757};

        for (int i = 0; i < stopLat.length; i++) {
            busNearestStopLatLngList.add(i, new LatLng(stopLat[i], stopLng[i]));
            marker = googleMap.addMarker(new MarkerOptions()
                    .position(busNearestStopLatLngList.get(i))
                    .title("OK")
                    .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.bus_route)));
            marker.setTag(i);
        }
    }

    public static BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        Paint color = new Paint();
        color.setTextSize(20);
        color.setColor(Color.RED);
        vectorDrawable.draw(canvas);
        // canvas.drawText("Stop", 3, 40, color);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
