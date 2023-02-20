package com.example.citybus.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.ModelClases.FindBusesResultBottomSheetModel;
import com.example.citybus.R;
import com.example.citybus.RouteBusMap;

import java.util.ArrayList;

public class RvFindBusesAdapter extends RecyclerView.Adapter<RvFindBusesAdapter.RvFindBusesAdapterViewHolder> {
    ArrayList<FindBusesResultBottomSheetModel> findBusesResultBottomSheetModelArrayList;
    Context context;
    public RvFindBusesAdapter(Context context, ArrayList<FindBusesResultBottomSheetModel> findBusesResultBottomSheetModelArrayList) {
        this.context = context;
        this.findBusesResultBottomSheetModelArrayList = findBusesResultBottomSheetModelArrayList;
    }

    @NonNull
    @Override
    public RvFindBusesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_buses_bottom_sheet_desing,parent,false);
        return new RvFindBusesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvFindBusesAdapterViewHolder holder, int position) {
        FindBusesResultBottomSheetModel findBusesResultBottomSheetModel = findBusesResultBottomSheetModelArrayList.get(position);
        holder.txtBusName.setText(findBusesResultBottomSheetModel.getBusName());
        holder.txtBusNumber.setText(findBusesResultBottomSheetModel.getBusNumber());
        holder.txtBusArrivalTime.setText(findBusesResultBottomSheetModel.getBusArrivalTime()+" Mins");
        holder.txtFromTo.setText(findBusesResultBottomSheetModel.getBusFrom()+" TO "+findBusesResultBottomSheetModel.getBusTo());
        holder.lyBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               new RouteBusMap().disableBottomSheet(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return findBusesResultBottomSheetModelArrayList.size();
    }

    class RvFindBusesAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtFromTo,txtBusArrivalTime,txtBusNumber,txtBusName;
        LinearLayout lyBottomSheet;
        public RvFindBusesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBusName = itemView.findViewById(R.id.txtBusName);
            txtBusNumber = itemView.findViewById(R.id.txtBusNumber);
            txtBusArrivalTime = itemView.findViewById(R.id.txtBusArrivalTime);
            txtFromTo = itemView.findViewById(R.id.txtFromTo);
            lyBottomSheet = itemView.findViewById(R.id.lyBottomSheet);
        }
    }
}
