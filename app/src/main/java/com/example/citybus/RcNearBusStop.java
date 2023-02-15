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

public class RcNearBusStop extends RecyclerView.Adapter<RcNearBusStop.NearBusStopViewHolder> {

    String[] nearStop, nearStopDist;
    Context context;

    public RcNearBusStop(Context context, String[] nearStop, String[] nearStopDist) {
        this.nearStopDist = nearStopDist;
        this.nearStop = nearStop;
        this.context = context;
    }

    @NonNull
    @Override
    public NearBusStopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearbusstop_layout_desing, parent, false);
        return new NearBusStopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearBusStopViewHolder holder, int position) {
        holder.nearStopName.setText(nearStop[position]);
        holder.nearStopDist.setText(nearStopDist[position] + " KM");
        holder.nearestBusStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeeAllBuses.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("stop",nearStop[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearStop.length;
    }

    class NearBusStopViewHolder extends RecyclerView.ViewHolder {
        ImageView stopIcon;
        TextView nearStopName, nearStopDist;
        LinearLayout nearestBusStop;

        public NearBusStopViewHolder(@NonNull View itemView) {
            super(itemView);
            nearStopDist = itemView.findViewById(R.id.nearStopDist);
            nearStopName = itemView.findViewById(R.id.nearStopName);
            stopIcon = itemView.findViewById(R.id.stopIcon);
            nearestBusStop = itemView.findViewById(R.id.nearestBusStop);
        }
    }
}
