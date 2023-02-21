package com.example.citybus.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.MapActivitys.SelectBusMapByNumber;
import com.example.citybus.ModelClases.SearchBusNumberModel;
import com.example.citybus.R;

import java.util.ArrayList;

public class RvSearchBusNumberAdapter extends RecyclerView.Adapter<RvSearchBusNumberAdapter.MySearchBusNumberViewHolder> {

    Context context;
    ArrayList<SearchBusNumberModel> searchBusNumberList;
    public RvSearchBusNumberAdapter(Context context, ArrayList<SearchBusNumberModel> searchBusNumberList)
    {
        this.searchBusNumberList = searchBusNumberList;
        this.context = context;
    }

    public void filteredAdapterList(ArrayList<SearchBusNumberModel> filterBusNumberList)
    {
        this.searchBusNumberList = filterBusNumberList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MySearchBusNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_bus_number_desing,parent,false);
        return new MySearchBusNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySearchBusNumberViewHolder holder, int position) {
        SearchBusNumberModel searchBusNumberModel= searchBusNumberList.get(position);
        holder.txtNameOfBus.setText(""+searchBusNumberModel.getNameOfBus());
        holder.txtBusNumber.setText("NO :"+searchBusNumberModel.getBusNumber());
        holder.txtFromTo.setText("From:"+searchBusNumberModel.getBusFrom()+" To: "+searchBusNumberModel.getBusTo());

        holder.cardSearchBusNumberTpSelectBusMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectBusMapByNumber.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("From",searchBusNumberModel.getBusFrom());
                intent.putExtra("To",searchBusNumberModel.getBusTo());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchBusNumberList.size();
    }

    class MySearchBusNumberViewHolder extends RecyclerView.ViewHolder {
        TextView txtFromTo,txtBusNumber,txtNameOfBus;
        CardView cardSearchBusNumberTpSelectBusMap;
        public MySearchBusNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameOfBus = itemView.findViewById(R.id.txtNameOfBus);
            txtBusNumber = itemView.findViewById(R.id.txtBusNumber);
            txtFromTo = itemView.findViewById(R.id.txtFromTo);
            cardSearchBusNumberTpSelectBusMap = itemView.findViewById(R.id.cardSearchBusNumberTpSelectBusMap);
        }
    }
}
