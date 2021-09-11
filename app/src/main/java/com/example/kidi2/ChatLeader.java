package com.example.kidi2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatLeader extends AppCompatActivity {
    private ImageButton sendBtn;
    private EditText sendTxt;
    private ImageButton returnBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_leader);
        sendBtn = findViewById(R.id.sendBtn);
        sendTxt = findViewById(R.id.sendTxt);
        returnBtn=findViewById(R.id.returnBtnChat);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ArrayList<ChatItem> chatItemArrayList=new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycleChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChatAdapter chatAdapter = new ChatAdapter( chatItemArrayList);
        recyclerView.setAdapter(chatAdapter);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=sendTxt.getText().toString();
                if(!msg.equals("")) {
                    ChatItem chatItem = new ChatItem(msg);
                    chatItemArrayList.add(chatItem);
                    sendTxt.setText("");
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }
    private void sendMessage(Leader leader, Parent parent, String message){
        HashMap<String,Object> chatmap=new HashMap<>();
        chatmap.put("sender",parent);
        chatmap.put("reciever",leader);
        chatmap.put("message",message);
    }
}