package com.example.kidi2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VPAdapter2 extends RecyclerView.Adapter<VPAdapter2.ViewHolder> {

    ArrayList<ViewPagerItem> viewPagerItemArrayList;
    Context ctx;
    String[] kidsID;
    int positiontemp;

    public Boolean getSharedp() {
        return sharedp;
    }

    public void setSharedp(Boolean sharedp) {
        this.sharedp = sharedp;
    }

    Boolean sharedp=false;//add shared prefrence or not

    public String[] getKidsID() {
        return kidsID;
    }

    public void setKidsID(String[] kidsID,int posiotion) {
        this.kidsID = kidsID;
        this.positiontemp=posiotion;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public VPAdapter2(ArrayList<ViewPagerItem> viewPagerItemArrayList) {
        this.viewPagerItemArrayList = viewPagerItemArrayList;
    }
    public void setVPAdapter(ArrayList<ViewPagerItem> viewPagerItemArrayList){
        this.viewPagerItemArrayList=viewPagerItemArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewpager_item2,parent,false);
        TextView tvprofile =(TextView) view.findViewById(R.id.profileText2);
        tvprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSharedp()==false) {
                    SharedPreferences pref = getCtx().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("kidSeeProfile", kidsID[positiontemp]);
                    editor.commit();
                }
                setSharedp(false);
                Intent openThree = new Intent(getCtx(),KidName.class);
                getCtx().startActivity(openThree);
            }});
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ViewPagerItem viewPagerItem = viewPagerItemArrayList.get(position);

        holder.imageView.setImageResource(viewPagerItem.imageID);
        holder.name.setText(viewPagerItem.name);
        holder.description.setText(viewPagerItem.description);

        holder.date.setText(viewPagerItem.date);
        holder.profile.setText(viewPagerItem.profile);

    }

    @Override
    public int getItemCount() {
        return viewPagerItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, date,profile,description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.kidImage2);
            name = itemView.findViewById(R.id.kidNameText2);
            date = itemView.findViewById(R.id.dateText2);

            profile = itemView.findViewById(R.id.profileText2);
            description = itemView.findViewById(R.id.descrebtionText2);

        }
    }

}