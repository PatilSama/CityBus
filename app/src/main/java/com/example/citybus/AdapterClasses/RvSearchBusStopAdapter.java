package com.example.citybus.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.ModelClases.SearchBusStopsModel;
import com.example.citybus.R;
import java.util.ArrayList;

public class RvSearchBusStopAdapter extends RecyclerView.Adapter<RvSearchBusStopAdapter.MySearchBusStopAdapter> {

    Context context;
    ArrayList<SearchBusStopsModel> searchBusStopsModelArrayList;
    public RvSearchBusStopAdapter(Context context, ArrayList<SearchBusStopsModel> searchBusStopsModelArrayList)
    {
        this.context=context;
        this.searchBusStopsModelArrayList=searchBusStopsModelArrayList;
    }

    @NonNull
    @Override
    public MySearchBusStopAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_stop_rv_item_desing,parent,false);
        return new MySearchBusStopAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySearchBusStopAdapter holder, int position) {
        SearchBusStopsModel searchBusStopsModel = searchBusStopsModelArrayList.get(position);
        holder.searchStopItem.setText(searchBusStopsModel.getStopName());
    }

    @Override
    public int getItemCount() {
        return searchBusStopsModelArrayList.size();
    }

    public void filterList(ArrayList<SearchBusStopsModel> filterSearchBusStop) {
        this.searchBusStopsModelArrayList = filterSearchBusStop;
        notifyDataSetChanged();
    }

    class MySearchBusStopAdapter extends RecyclerView.ViewHolder {
        TextView searchStopItem;
        public MySearchBusStopAdapter(@NonNull View itemView) {
            super(itemView);
            searchStopItem = itemView.findViewById(R.id.searchStopItem);
        }
    }
}
