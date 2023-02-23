package com.example.citybus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.ModelClases.NearestBusStopModel;

import java.util.ArrayList;

public class RcNearBusStop extends RecyclerView.Adapter<RcNearBusStop.NearBusStopViewHolder> {


    ArrayList<NearestBusStopModel> nearestBusStopModelArrayList;
    Context context;

    public RcNearBusStop(Context context, ArrayList<NearestBusStopModel> nearestBusStopModelArrayList) {
        this.context = context;
        this.nearestBusStopModelArrayList = nearestBusStopModelArrayList;
    }

    @NonNull
    @Override
    public NearBusStopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearbusstop_layout_desing, parent, false);
        return new NearBusStopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearBusStopViewHolder holder, int position) {
        NearestBusStopModel nearestBusStopModel = nearestBusStopModelArrayList.get(position);
        holder.nearStopName.setText(nearestBusStopModel.getStopName());
        holder.nearStopDist.setText(nearestBusStopModel.getStopDist() + " Mins");
        holder.txtBusTripNo.setText(nearestBusStopModel.getBusTripNo());
        holder.nearestBusStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, SeeAllBuses.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("stop",nearestBusStopModel.getStopName());
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearestBusStopModelArrayList.size();
    }

    class NearBusStopViewHolder extends RecyclerView.ViewHolder {
        ImageView stopIcon;
        TextView nearStopName, nearStopDist, txtBusTripNo;
        LinearLayout nearestBusStop;

        public NearBusStopViewHolder(@NonNull View itemView) {
            super(itemView);
            nearStopDist = itemView.findViewById(R.id.nearStopDist);
            nearStopName = itemView.findViewById(R.id.nearStopName);
            txtBusTripNo = itemView.findViewById(R.id.txtBusTripNo);
            stopIcon = itemView.findViewById(R.id.stopIcon);
            nearestBusStop = itemView.findViewById(R.id.nearestBusStop);
        }
    }
}
