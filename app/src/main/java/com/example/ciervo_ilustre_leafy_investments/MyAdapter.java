package com.example.ciervo_ilustre_leafy_investments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<DueDate> dueDateArrayList;

    public MyAdapter(Context context, ArrayList<DueDate> dueDateArrayList) {
        this.context = context;
        this.dueDateArrayList = dueDateArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        DueDate dueDate = dueDateArrayList.get(position);

        holder.name.setText(dueDate.name);
        holder.amount.setText(dueDate.amount);
        holder.category.setText(dueDate.category);
        holder.date.setText(dueDate.date);

    }

    @Override
    public int getItemCount() {
        return dueDateArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, amount, category, date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dueDate_Name);
            amount = itemView.findViewById(R.id.dueDate_Amount);
            category = itemView.findViewById(R.id.dueDate_Category);
            date = itemView.findViewById(R.id.dueDate_Date);
        }
    }
}
