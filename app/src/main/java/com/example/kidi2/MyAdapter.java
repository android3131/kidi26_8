package com.example.myapplication19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication19.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //String data1[], data2[], data3[],data4[],data5[],data6[],data7[];
    Context context;
    private int selectedPosition = -1;
    private ArrayList<Course> lis;



    public MyAdapter(Context ct,/* String s1[], String s2[], String s3[],
                     String s4[],String s5[],String s6[],String s7[],*/ArrayList<Course> lis){
        context = ct;
        /*data1 = s1;
        data2 = s2;
        data3 = s3;
        data4= s4;
        data5= s5;
        data6=s6;
        data7=s7;
         */
        this.lis=lis;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.my_row, parent, false);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(lis.get(position).getCategory().getName());
        holder.myText2.setText(lis.get(position).getName());
        holder.myText3.setText(lis.get(position).getStartDateTime());
        holder.myText4.setText(lis.get(position).getFinishDateTime());
        holder.myText5.setText(lis.get(position).getDay());
        holder.myText6.setText(lis.get(position).getStartOclock());
        holder.myText7.setText(lis.get(position).getEndOclock());
        holder.mainLayout.setBackgroundColor((lis.get(position).getClr()));

        /*
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                holder.myText1.setText("1234");
            }
        });


         */

    }

    @Override
    public int getItemCount() {
        return lis.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2, myText3,myText4,myText5,myText6,myText7;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.fisrt_col);
            myText2 = itemView.findViewById(R.id.secend_col);
            myText3 = itemView.findViewById(R.id.third_col);
            myText4 = itemView.findViewById(R.id.fourth_col);
            myText5 = itemView.findViewById(R.id.fifth_col);
            myText6 = itemView.findViewById(R.id.sixth_col);
            myText7 = itemView.findViewById(R.id.seventh_col);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }

    }
}
