package com.example.citybus.MapActivitys;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.citybus.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.textfield.TextInputEditText;

public class SelectBusMapByNumber extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    TextInputEditText txtFrom,txtTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectbusmapbynumber_layout);
        txtFrom = findViewById(R.id.txtFrom);
        txtTo = findViewById(R.id.txtTo);
        String from = getIntent().getStringExtra("From");
        String to = getIntent().getStringExtra("To");
        if(from != null && to != null)
        {
            txtFrom.setText(from);
            txtTo.setText(to);
        }
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.selectBusRouteMapFragment);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
