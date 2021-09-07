package com.example.kidi2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuantityAapter extends RecyclerView.Adapter<QuantityAapter.viewHolder> {
    View view;
    Context context;
    ArrayList<String> arrayList;
    QuantityListener quantityListener;

    ArrayList<String> arrayList_0=new ArrayList<>();

    public QuantityAapter(Context context,ArrayList<String> arrayList,QuantityListener quantityListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.quantityListener = quantityListener;
    }
    public View getView(){
        return view;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(arrayList !=null && arrayList.size()>0){
            holder.check_box.setText(arrayList.get(position));
            holder.check_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.check_box.isChecked()){
                        arrayList_0.add(arrayList.get(position));
                    }
                    else {
                        arrayList_0.remove(arrayList.get(position));
                    }
                    quantityListener.onQuantityChange(arrayList_0);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CheckBox check_box;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            check_box=itemView.findViewById(R.id.check_box);
        }
    }
}
