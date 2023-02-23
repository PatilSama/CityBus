package com.example.citybus.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.MainActivity;
import com.example.citybus.ModelClases.RvChangeNearBusStopModel;
import com.example.citybus.R;

import java.util.ArrayList;

public class RvChangeNearBusStopAdapter extends RecyclerView.Adapter<RvChangeNearBusStopAdapter.RvChangeNearBusStopViewHolder> {
    Context context;
    ArrayList<RvChangeNearBusStopModel> rvChangeNearBusStopModelArrayList;
    public RvChangeNearBusStopAdapter(Context context, ArrayList<RvChangeNearBusStopModel> rvChangeNearBusStopModelArrayList) {
        this.context = context;
        this.rvChangeNearBusStopModelArrayList = rvChangeNearBusStopModelArrayList;
    }

    @NonNull
    @Override
    public RvChangeNearBusStopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.changenearbusstop_layout_desing,parent,false);
        return new RvChangeNearBusStopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvChangeNearBusStopViewHolder holder, int position) {
        RvChangeNearBusStopModel rvChangeNearBusStopModel = rvChangeNearBusStopModelArrayList.get(position);
        holder.txtNearStopName.setText(rvChangeNearBusStopModel.getNearStop());
        holder.txtStopKm.setText(rvChangeNearBusStopModel.getStopKm()+" KM");
        holder.lyChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ChangeNearStop",rvChangeNearBusStopModel.getNearStop());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rvChangeNearBusStopModelArrayList.size();
    }

    class RvChangeNearBusStopViewHolder extends RecyclerView.ViewHolder {
        TextView txtNearStopName,txtStopKm;
        LinearLayout lyChange;
        public RvChangeNearBusStopViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNearStopName= itemView.findViewById(R.id.txtNearStopName);
            txtStopKm = itemView.findViewById(R.id.txtStopKm);
            lyChange = itemView.findViewById(R.id.lyChange);
        }
    }
}
