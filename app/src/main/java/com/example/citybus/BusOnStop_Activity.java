package com.example.citybus;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.AdapterClasses.RvFindBusesAdapter;
import com.example.citybus.ModelClases.FindBusesResultBottomSheetModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class BusOnStop_Activity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    ImageView collapsExpandIcon;
    BottomSheetBehavior bottomSheetBehavior;
    TextView txtBottomStatus;
    RecyclerView RvRouteBusesList;
    Context context;
    ArrayList<FindBusesResultBottomSheetModel> findBusesResultBottomSheetModelArrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busonstop_layout);
        context = getApplicationContext();
        collapsExpandIcon = findViewById(R.id.collapsExpandIcon);
        txtBottomStatus = findViewById(R.id.txtBottomStatus);
        RvRouteBusesList = findViewById(R.id.rvFindBusesBottomSheet);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.busOnStopMap);
        mapFragment.getMapAsync(this);
        buildBottomSheet();
        buildRecyclerView();
    }

    private void buildBottomSheet() {

        View bottomSheet = findViewById(R.id.nestedBottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(120);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    collapsExpandIcon.setImageResource(R.drawable.arrow_down);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    collapsExpandIcon.setImageResource(R.drawable.arrow_up);
                }
            }

            @Override
            public void onSlide(View view, float v) {

            }
        });
    }
    private void buildRecyclerView() {
        findBusesResultBottomSheetModelArrayList = new ArrayList<>();
        findBusesResultBottomSheetModelArrayList.add(new FindBusesResultBottomSheetModel("Bus1","MH12MK1","1","Upper Depot","Swargate"));
        findBusesResultBottomSheetModelArrayList.add(new FindBusesResultBottomSheetModel("Bus2","MH12MK2","5","Swargate","Indira Nagar Bus Stop"));
        findBusesResultBottomSheetModelArrayList.add(new FindBusesResultBottomSheetModel("Bus3","MH12MK3","11","Pune Manapa Bus Station","Swargate"));
        findBusesResultBottomSheetModelArrayList.add(new FindBusesResultBottomSheetModel("Bus4","MH12MK4","12","Pune Station Bus Stand","Pune Manapa Bus Station"));
        findBusesResultBottomSheetModelArrayList.add(new FindBusesResultBottomSheetModel("Bus5","MH12MK5","7","Pune Manapa Bus Station","Swargate"));
        findBusesResultBottomSheetModelArrayList.add(new FindBusesResultBottomSheetModel("Bus6","MH12MK6","9","Swargate","Upper Depot"));
        findBusesResultBottomSheetModelArrayList.add(new FindBusesResultBottomSheetModel("Bus4","MH12MK4","12","Pune Station Bus Stand","Pune Manapa Bus Station"));
        findBusesResultBottomSheetModelArrayList.add(new FindBusesResultBottomSheetModel("Bus5","MH12MK5","7","Pune Manapa Bus Station","Swargate"));
        findBusesResultBottomSheetModelArrayList.add(new FindBusesResultBottomSheetModel("Bus6","MH12MK6","9","Swargate","Upper Depot"));

        RvRouteBusesList.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.rcdevider_layout));
        RvRouteBusesList.addItemDecoration(itemDecorator);
        RvRouteBusesList.setAdapter(new RvFindBusesAdapter(context,findBusesResultBottomSheetModelArrayList));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
}
