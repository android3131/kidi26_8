package com.example.kidi2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder>{

    ArrayList<AlgorithmItem> lis;
    Context context;



    public RvAdapter(Context ct,  ArrayList<AlgorithmItem> lis){
        context = ct;
        this.lis=lis;

    }
    public void setItems(ArrayList<AlgorithmItem> lis) {
        this.lis = lis;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(position<lis.size()) {
            holder.myText1.setText(lis.get(position).getAlgorithmName());
            holder.myText2.setText(lis.get(position).getElse_information());
            holder.imgsrc.setImageResource(lis.get(position).getIm());
        }
    }

    @Override
    public int getItemCount() {
        return lis.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2;
        ImageView imgsrc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.txt1);
            myText2 = itemView.findViewById(R.id.txt2);
            imgsrc = itemView.findViewById(R.id.img);

        }

    }
}
