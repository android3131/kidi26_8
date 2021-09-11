package com.example.kidi2;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterPerLeader  extends RecyclerView.Adapter<MyAdapterPerLeader.MyViewHolder4> {

    //String data1[], data2[], data3[],data4[],data5[],data6[],data7[];
    Context context;
    private int selectedPosition = -1;
    private ArrayList<Leader> lis;



    public MyAdapterPerLeader(Context ct,/* String s1[], String s2[], String s3[],
                     String s4[],String s5[],String s6[],String s7[],*/ArrayList<Leader> lis){
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
    public MyAdapterPerLeader.MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.my_row4, parent, false);
        View view = inflater.inflate(R.layout.my_row4, parent, false);
        return new MyAdapterPerLeader.MyViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterPerLeader.MyViewHolder4 holder, int position) {
        holder.myText1.setText(lis.get(position).getFullName());
        holder.myText2.setText(lis.get(position).getEmail());
        holder.myText3.setText(lis.get(position).getPhoneNumber());

        holder.mainLayout.setBackgroundColor(lis.get(position).getClr());

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

    public class MyViewHolder4 extends RecyclerView.ViewHolder {

        TextView myText1;
        TextView myText2;
        TextView myText3;
        LinearLayout mainLayout;

        public MyViewHolder4(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.fisrt_col);
            myText2 = itemView.findViewById(R.id.second_col);
            myText3 = itemView.findViewById(R.id.third_col);

            mainLayout = itemView.findViewById(R.id.mainLayout);

        }

    }
}
