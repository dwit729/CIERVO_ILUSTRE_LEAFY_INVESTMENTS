package com.example.ciervo_ilustre_leafy_investments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder>{

    Context context;
    ArrayList<ActLogs> actLogsArrayList;
    public MyListAdapter(Context context, ArrayList<ActLogs> actLogsArrayList)
    {
        this.context = context;
        this.actLogsArrayList = actLogsArrayList;
    }

    @NonNull
    @Override
    public MyListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.logs_item,parent, false);

        return new MyListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListAdapter.MyViewHolder holder, int position) {

        holder.name.setText(actLogsArrayList.get(position).getName());
        holder.amount.setText(actLogsArrayList.get(position).getAmount());
        holder.date.setText(actLogsArrayList.get(position).getDate());
        holder.category.setText(actLogsArrayList.get(position).getCategory());
    }

    @Override
    public int getItemCount() {

        return actLogsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, amount, category, date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.logsName);
            amount = itemView.findViewById(R.id.logsAmount);
            category = itemView.findViewById(R.id.logsCate);
            date = itemView.findViewById(R.id.LogsDate);

        }
    }
}
