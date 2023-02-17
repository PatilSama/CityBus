package com.example.citybus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.AdapterClasses.RvSearchBusNumberAdapter;
import com.example.citybus.ModelClases.SearchBusNumberModel;

import java.util.ArrayList;

public class SeeAllBuses extends AppCompatActivity {
    RecyclerView rvSearchBusNumber;
    RvSearchBusNumberAdapter rvSearchBusNumberAdapter;
    Context context;
    private ArrayList<SearchBusNumberModel> SearchBusNumberList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeallbuses_layout);
        context = getApplicationContext();
        rvSearchBusNumber = findViewById(R.id.rvSearchBusNumber);
        buildRecyclerView();
    }
    private void filterList(String nextText) {
        ArrayList<SearchBusNumberModel> filterBusNumberList = new ArrayList<>();
        for(SearchBusNumberModel item : SearchBusNumberList )
        {
            if(item.getNameOfBus().toLowerCase().contains(nextText.toLowerCase())
                    || item.getBusNumber().toLowerCase().contains(nextText.toLowerCase())
                    || item.getBusFrom().toLowerCase().contains(nextText.toLowerCase())
                    || item.getBusTo().toLowerCase().contains(nextText.toLowerCase()))
            {
                filterBusNumberList.add(item);
            }
        }
        if(!filterBusNumberList.isEmpty())
        {
            rvSearchBusNumberAdapter.filteredAdapterList(filterBusNumberList);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchoption_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String nextText) {
                filterList(nextText);
                return false;
            }
        });
        return true;
    }
    private void buildRecyclerView() {
        SearchBusNumberList = new ArrayList<SearchBusNumberModel>();
        SearchBusNumberList.add(new SearchBusNumberModel("Upper Depot","MH12MK7873","Upper Depot","Swargate"));
        SearchBusNumberList.add(new SearchBusNumberModel("Swargate","MH12MK8999","Swargate","Indira Nagar Bus Stop"));
        SearchBusNumberList.add(new SearchBusNumberModel("Pune Manapa Bus Station","MH12MK4028","Pune Manapa Bus Station","Swargate"));
        SearchBusNumberList.add(new SearchBusNumberModel("Pune Station Bus Stand","MH12MK0110","Pune Station Bus Stand","Pune Manapa Bus Station"));
        SearchBusNumberList.add(new SearchBusNumberModel("Swargate","MH12MK2112","Pune Manapa Bus Station","Swargate"));

        rvSearchBusNumber.setLayoutManager(new LinearLayoutManager(this));
        rvSearchBusNumber.setHasFixedSize(true);
        rvSearchBusNumberAdapter = new RvSearchBusNumberAdapter(context,SearchBusNumberList);
//        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.rcdevider_layout));
//        rvSearchBusNumber.addItemDecoration(itemDecorator);
        rvSearchBusNumber.setAdapter(rvSearchBusNumberAdapter);
    }
}
