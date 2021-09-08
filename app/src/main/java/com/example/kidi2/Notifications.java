package com.example.kidi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
private Button readall;
private ImageButton returnBtn;
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.notifications);
        readall=findViewById(R.id.readAllBtn);
        returnBtn=findViewById(R.id.returnNotiBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        navigationView = findViewById(R.id.btvnotification);
        navigationView.setSelectedItemId(R.id.bottomNavigationNotificatonsMenuId);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationHomeMenuId:
                        startActivity(new Intent(Notifications.this, HomeLogin.class));
                        return true;
                    case R.id.bottomNavigationUserMenuId:
                        startActivity(new Intent(Notifications.this, KidName.class));
                        return true;
                    case R.id.bottomNavigationActivityMenuId:
                        startActivity(new Intent(Notifications.this, FirstScreen.class));
                        return true;
                    case R.id.bottomNavigationNotificatonsMenuId:
                        startActivity(new Intent(Notifications.this, Notifications.class));
                        return true;
                    case R.id.bottomNavigationMoreMenuId:

                        PopupMenu popup = new PopupMenu(Notifications.this, findViewById(R.id.bottomNavigationMoreMenuId));
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.mymenu, popup.getMenu());
                        popup.show();
                        return true;
                }
                return false;
            }
        });
        int[] images = {R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl
        ,R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl};
       NotificationsAdapter notificationsAdapter;
        ArrayList<NotificationItem> notificationItemArrayList=new ArrayList<NotificationItem>();
       for(int i=0;i<10;i++) {
           NotificationItem notificationItem = new NotificationItem(images[i], "notification");
           notificationItemArrayList.add(notificationItem);

       }


        RecyclerView recyclerView = findViewById(R.id.recylenotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationsAdapter = new NotificationsAdapter( notificationItemArrayList);
        recyclerView.setAdapter(notificationsAdapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(notificationItemArrayList.get(position).getSeen()==false) {
                            notificationItemArrayList.get(position).setSeen(true);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        readall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
for(int i=0;i<notificationItemArrayList.size();i++){
    if(notificationItemArrayList.get(i).getSeen()==false) {
        notificationItemArrayList.get(i).setSeen(true);
    }
}
                recyclerView.getAdapter().notifyDataSetChanged();

            }
        });

    }
}