package com.example.kidi2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvAdapter_grp6 extends RecyclerView.Adapter<RvAdapter_grp6.MyViewHolder>{

    ArrayList<AlgorithmItem_grp6> lis;
    Context context;



    public RvAdapter_grp6(Context ct, ArrayList<AlgorithmItem_grp6> lis){
        context = ct;
        this.lis=lis;

    }
    public void setItems(ArrayList<AlgorithmItem_grp6> lis) {
        this.lis = lis;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_add_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(position<lis.size()) {
            holder.myText1.setText(lis.get(position).getAlgorithmName());
            holder.myText2.setText(lis.get(position).getElse_information());
            holder.check.setChecked(lis.get(position).getCheck());
        }
    }

    void helloFromRvAdapter(){
        System.out.println("RvAdapter");

    }

    @Override
    public int getItemCount() {
        return lis.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2;
        CheckedTextView check;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.txt1);
            myText2 = itemView.findViewById(R.id.txt2);
            check = itemView.findViewById(R.id.checkBox);

        }

    }
}
