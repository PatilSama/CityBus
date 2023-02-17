package com.example.citybus;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.textfield.TextInputEditText;

public class SearchBuses extends AppCompatActivity implements OnMapReadyCallback {
    Context context;
    GoogleMap googleMap;
    TextInputEditText searchFrom,searchTo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbuses_layout);
        context = getApplicationContext();
        searchTo = findViewById(R.id.searchTo);
        searchFrom = findViewById(R.id.searchFrom);

        String stopFrom = getIntent().getStringExtra("stopFrom");
        String stopTo = getIntent().getStringExtra("stopTo");
        if(stopFrom != null && stopTo != null)
        {
            searchFrom.setText(stopFrom);
            searchTo.setText(stopTo);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapBusAroundYou);
        mapFragment.getMapAsync(this);
        buildRecyclerView();
    }

    private void buildRecyclerView() {
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap=googleMap;
    }
}
