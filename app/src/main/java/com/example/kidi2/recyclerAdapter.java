package com.example.kidi2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<Category> categories;

    public recyclerAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryName;

        public MyViewHolder(final View view){

            super(view);
            categoryName = view.findViewById(R.id.categoryName);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String name = categories.get(position).getName();
        holder.categoryName.setText(name);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
