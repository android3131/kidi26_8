package com.example.kidi2;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    ArrayList<NotificationItem> notificationItemArrayList;








    public NotificationsAdapter(ArrayList<NotificationItem> notificationItemArrayList) {
        this.notificationItemArrayList = notificationItemArrayList;

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_layout,parent,false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NotificationItem notificationItem = notificationItemArrayList.get(position);

        holder.imageView.setImageResource(notificationItem.imageID);
        holder.noti.setText(notificationItem.notificationText);
      holder.seen=notificationItem.seen;
        if(holder.seen==true){
        holder.noti_linearlayout.setBackgroundColor(Color.parseColor("#5377ee"));

    }


    }

    @Override
    public int getItemCount() {
        return notificationItemArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView noti;
Boolean seen;
        LinearLayout noti_linearlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.notiimage);
            noti = itemView.findViewById(R.id.notiText);
            noti_linearlayout=(LinearLayout)itemView.findViewById(R.id.notilayoutitem);

        }
    }

}
