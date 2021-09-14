package com.example.kidi2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    private ArrayList<KidCheckBox> mExamplelist;
    //private int row_index;
    //private int item_selected;
    private int mCheckedPostion = -1;// no selection by default
    Context ctx;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.kid_checkbox_grp6,parent,false);
        MyViewHolder evh = new MyViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        KidCheckBox curr=mExamplelist.get(position);

        if(mExamplelist.get(position).getKid().getGender()==Gender.Boy){
            holder.imageView.setImageResource(R.drawable.boyavatargrp6);
        }
        else {
            holder.imageView.setImageResource(R.drawable.girlavatargrp6);
        }
        //holder.imageView.setImageResource(Integer.parseInt( curr.getKid().getImage()));
        holder.checkedTextView.setText(curr.getKid().getFullName());

        //check checkbox and uncheck previous selected button
        holder.checkedTextView.setChecked(position == mCheckedPostion);
        holder.checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //              boolean currentCheckedStatus = curr.isIschecked();
                mExamplelist.get(position).setIschecked(true);
                if (position == mCheckedPostion) {
                    //curr.setIschecked(!currentCheckedStatus);

                    holder.checkedTextView.setChecked(false);
                    mCheckedPostion = -1;
                } else {
                    mCheckedPostion = position;
                    //System.out.println(position);
                    // curr.setIschecked(false);
                    notifyDataSetChanged();
                }
                ((Addactivity) ctx).getPosition(position);
                ((Addactivity) ctx).ClickedKid();
            }

        });


    }

    @Override
    public int getItemCount() {
        return mExamplelist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public CheckedTextView checkedTextView;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView3);
            checkedTextView=itemView.findViewById(R.id.checkBox);
        }

    }
    public MyAdapter2(Context ctx,ArrayList<KidCheckBox> exampleList){
        this.mExamplelist=exampleList;
        this.ctx=ctx;

    }



    //public int getItem_selected() {
    //return item_selected;
    //}
}

