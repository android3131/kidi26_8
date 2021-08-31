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
import androidx.viewpager2.widget.ViewPager2;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VPAdapter extends RecyclerView.Adapter<VPAdapter.ViewHolder> {

    ArrayList<ViewPagerItem> viewPagerItemArrayList;
Context ctx;
String[] kidsID;
int positontemp;
Boolean sharedp=false;//add shared prefrence or not

    public Boolean getSharedp() {
        return sharedp;
    }

    public void setSharedp(Boolean sharedp) {
        this.sharedp = sharedp;
    }

    public String[] getKidsID() {
        return kidsID;
    }

    public void setKidsID(String[] kidsID,int position) {
        this.kidsID = kidsID;
        this.positontemp=position;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public VPAdapter(ArrayList<ViewPagerItem> viewPagerItemArrayList) {
        this.viewPagerItemArrayList = viewPagerItemArrayList;

    }


    public Context getCtx() {
        return this.ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewpager_item,parent,false);
        TextView tvprofile =(TextView) view.findViewById(R.id.profileText);
        tvprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSharedp()==false) {
                    SharedPreferences pref = getCtx().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("kidSeeProfile", kidsID[positontemp]);
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

            imageView = itemView.findViewById(R.id.kidImage);
            name = itemView.findViewById(R.id.kidNameText);
            date = itemView.findViewById(R.id.dateText);

            profile = itemView.findViewById(R.id.profileText);
            description = itemView.findViewById(R.id.descrebtionText);

        }
    }

}
