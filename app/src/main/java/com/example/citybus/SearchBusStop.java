package com.example.citybus;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.AdapterClasses.RvSearchBusStopAdapter;
import com.example.citybus.ModelClases.SearchBusStopsModel;

import java.util.ArrayList;

public class SearchBusStop extends AppCompatActivity {
    RecyclerView rvSearchBusStop;
    RvSearchBusStopAdapter rvSearchBusStopAdapter;
    ArrayList<SearchBusStopsModel> searchBusStopsModelArrayList;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbusstop_layout);
        context = getApplicationContext();
        rvSearchBusStop = findViewById(R.id.rvSearchBusStop);
        BuildRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searchoption_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filterText(String newText) {
        ArrayList<SearchBusStopsModel> filterSearchBusStop = new ArrayList<>();
        for(SearchBusStopsModel item : searchBusStopsModelArrayList)
        {
            if(item.getStopName().toLowerCase().contains(newText.toLowerCase()))
            {
                filterSearchBusStop.add(item);
            }
        }
        if(!filterSearchBusStop.isEmpty())
        {
            rvSearchBusStopAdapter.filterList(filterSearchBusStop);
        }
    }

    private void BuildRecyclerView() {
        searchBusStopsModelArrayList = new ArrayList<>();
        searchBusStopsModelArrayList.add(new SearchBusStopsModel("Pune Station Bus Stand"));
        searchBusStopsModelArrayList.add(new SearchBusStopsModel("Swargate"));
        searchBusStopsModelArrayList.add(new SearchBusStopsModel("Pune Manapa Bus Station"));
        searchBusStopsModelArrayList.add(new SearchBusStopsModel("Upper Depot"));
        searchBusStopsModelArrayList.add(new SearchBusStopsModel("Swargate Bus Stand"));
        searchBusStopsModelArrayList.add(new SearchBusStopsModel("Marketyard"));

        rvSearchBusStop.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.rcdevider_layout));
        rvSearchBusStop.addItemDecoration(itemDecorator);
        rvSearchBusStopAdapter = new RvSearchBusStopAdapter(context,searchBusStopsModelArrayList);
        rvSearchBusStop.setAdapter(rvSearchBusStopAdapter);




    }
}
