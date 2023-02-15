package com.example.citybus.AdapterClasses;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.ConstantClass.Model1;
import com.example.citybus.MainActivity;
import com.example.citybus.ModelClases.CourseModel;
import com.example.citybus.R;
import java.util.ArrayList;

public class RvSearchStopAdapter extends RecyclerView.Adapter<RvSearchStopAdapter.MyRvSearchStopViewHolder> {

//    private ArrayList<CourseModel> searchStopItem;
    ArrayList<String> searchStopItem;
    private Context context;
    public RvSearchStopAdapter(ArrayList<String> searchStopItem, Context context)
    {
        this.searchStopItem = searchStopItem;
        this.context=context;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<String> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        searchStopItem = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyRvSearchStopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_stop_rv_item_desing,parent,false);
        return new MyRvSearchStopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRvSearchStopViewHolder holder, int position) {

        // setting data to our views of recycler view.
        String selectedStop = searchStopItem.get(position);
        holder.searchStopItem.setText(selectedStop);
        holder.searchStopItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, ""+selectedStop, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("SelectStop",selectedStop);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchStopItem.size();
    }

    class MyRvSearchStopViewHolder extends RecyclerView.ViewHolder {
        TextView searchStopItem;
        public MyRvSearchStopViewHolder(@NonNull View itemView) {
            super(itemView);
            searchStopItem = itemView.findViewById(R.id.searchStopItem);
        }
    }
}
