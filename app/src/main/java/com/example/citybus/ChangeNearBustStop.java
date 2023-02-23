package com.example.citybus;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.AdapterClasses.RvChangeNearBusStopAdapter;
import com.example.citybus.ModelClases.RvChangeNearBusStopModel;

import java.util.ArrayList;

public class ChangeNearBustStop extends AppCompatActivity {
    RecyclerView rvChangeSelectBusStop;
    ArrayList<RvChangeNearBusStopModel> rvChangeNearBusStopModelArrayList;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.changenearestbusstop_layout);
        rvChangeSelectBusStop=findViewById(R.id.rvChangeSelectBusStop);

        buildRecyclerView();
    }

    private void buildRecyclerView()
    {
        rvChangeNearBusStopModelArrayList = new ArrayList<>();
        rvChangeNearBusStopModelArrayList.add(new RvChangeNearBusStopModel("Karve","1"));
        rvChangeNearBusStopModelArrayList.add(new RvChangeNearBusStopModel("Warge","2"));
        rvChangeNearBusStopModelArrayList.add(new RvChangeNearBusStopModel("Deccan","1.5"));
        rvChangeNearBusStopModelArrayList.add(new RvChangeNearBusStopModel("Kothud","3"));
        rvChangeNearBusStopModelArrayList.add(new RvChangeNearBusStopModel("Swargate","2.5"));

        rvChangeSelectBusStop.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.rcdevider_layout));
        rvChangeSelectBusStop.addItemDecoration(itemDecorator);
        rvChangeSelectBusStop.setAdapter(new RvChangeNearBusStopAdapter(context,rvChangeNearBusStopModelArrayList));

    }
}
