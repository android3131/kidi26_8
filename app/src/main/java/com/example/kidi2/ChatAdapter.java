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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<ChatItem> chatItemArrayList;








    public ChatAdapter(ArrayList<ChatItem> chatItemArrayList) {
        this.chatItemArrayList = chatItemArrayList;

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item,parent,false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChatItem chatItem = chatItemArrayList.get(position);

       holder.msg.setText(chatItem.message);


    }

    @Override
    public int getItemCount() {
        return chatItemArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView msg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.msgText);


        }
    }

}
