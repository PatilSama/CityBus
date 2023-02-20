package com.example.citybus;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.AdapterClasses.RvFindBusesAdapter;
import com.example.citybus.ModelClases.FindBusesResultBottomSheetModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class RouteBusMap extends AppCompatActivity implements OnMapReadyCallback {
    Context context;
    GoogleMap googleMap;
    TextInputEditText searchFrom, searchTo;
    BottomSheetBehavior bottomSheetBehavior;
    ImageView collapsExpandIcon;
    RecyclerView rvFindBusesBottomSheet;
    ArrayList<FindBusesResultBottomSheetModel> findBusesResultBottomSheetModelArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routebusmap_layout);
        context = getApplicationContext();
        searchTo = findViewById(R.id.searchTo);
        searchFrom = findViewById(R.id.searchFrom);
        collapsExpandIcon = findViewById(R.id.collapsExpandIcon);
        rvFindBusesBottomSheet = findViewById(R.id.rvFindBusesBottomSheet);

        String stopFrom = getIntent().getStringExtra("stopFrom");
        String stopTo = getIntent().getStringExtra("stopTo");
        if (stopFrom != null && stopTo != null) {
            searchFrom.setText(stopFrom);
            searchTo.setText(stopTo);
        }
        buildBottomSheet();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapBusAroundYou);
        mapFragment.getMapAsync(this);
        buildRecyclerView();
    }

    private void buildBottomSheet() {

        View bottomSheet = findViewById(R.id.nested_bottom_sheet);
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

        rvFindBusesBottomSheet.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.rcdevider_layout));
        rvFindBusesBottomSheet.addItemDecoration(itemDecorator);
        rvFindBusesBottomSheet.setAdapter(new RvFindBusesAdapter(context,findBusesResultBottomSheetModelArrayList));
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(18.467406, 73.864470), 15.5F));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.467406, 73.864470))
                .title("You Are Here"));
    }
    public void disableBottomSheet(Context context)
    {
        View bottomSheet = findViewById(R.id.nested_bottom_sheet);
       BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}
