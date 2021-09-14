package com.example.kidi2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ParentProfileActivity extends AppCompatActivity {
    boolean saveInfo = false;
    Retrofit retrofit ;
    private RecyclerView my_recycler;
    private MyAdapterParentProfile m_adapter;
    static ArrayList<KidCheckBox> Kid_list = new ArrayList<KidCheckBox>();
    RetrofitAPIParentProfile retrofitAPI ;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String parentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);
        EditText name = findViewById(R.id.name_text);
        EditText phone = findViewById(R.id.phone_text);
        EditText email = findViewById(R.id.email_text);
        pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
        editor = pref.edit();
        parentId = pref.getString("parentID", null);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
        retrofitAPI = retrofit.create(RetrofitAPIParentProfile.class);

        getKidsData();

        Call<Parent> call = retrofitAPI.getParent(parentId);
        call.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(Call<Parent> call, Response<Parent> response) {
                // this method is called when we get response from our api.
                Parent p = response.body();

                if(p!=null) {
                    name.setText(p.getFullName());
                    email.setText(p.getEmail());
                    phone.setText(p.getPhoneNumber());
                }

            }
            @Override
            public void onFailure(Call<Parent> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Toast.makeText(ParentProfileActivity.this, "Server connection failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getKidsData() {
       Kid_list.clear();
        System.out.println(parentId);
        Call<List<Kid>> getAllParentsChildren = retrofitAPI.getParentsChildren(parentId);
        getAllParentsChildren.enqueue(new Callback<List<Kid>>() {
            @Override
            public void onResponse(Call<List<Kid>> getAllParentsChildren, Response<List<Kid>> response) {
                System.out.println("111111");

                List<Kid>  kids = response.body();
                for(int i=0;i<kids.size();i++){
                    if (kids.get(i) != null) {
                        Kid_list.add(new KidCheckBox(kids.get(i), false));
                    }
                }
                my_recycler = findViewById(R.id.RecyclerView);
                my_recycler.setHasFixedSize(true);
                m_adapter = new MyAdapterParentProfile(ParentProfileActivity.this,Kid_list);
                my_recycler.setAdapter(m_adapter);
            }

            @Override
            public void onFailure(Call<List<Kid>> getAllParentsChildren, Throwable t) {


            }
        });

    }

    public void editeInfo(View view) {
        EditText name = findViewById(R.id.name_text);
        EditText phone = findViewById(R.id.phone_text);
        EditText email = findViewById(R.id.email_text);
        Button info_button = findViewById(R.id.edit_info_button);
        if (!saveInfo) {
            info_button.setText("Save Info");
            name.setEnabled(true);
            phone.setEnabled(true);
            email.setEnabled(true);
            saveInfo = true;
        }
        else if(saveInfo){
            info_button.setText("Edit Info");
            name.setEnabled(false);
            phone.setEnabled(false);
            email.setEnabled(false);
            saveInfo = false;
            Parent parent = new Parent(name.getText().toString(),
                    phone.getText().toString(),
                    email.getText().toString())   ;
            sendData(parent);
        }


    }

    private void sendData(Parent parent) {
        parent.setId(pref.getString("parentID",null));
        Call<Void> call = retrofitAPI.createUpdateInfo(parent);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // this method is called when we get response from our api.
                Toast.makeText(ParentProfileActivity.this, "Information has changed", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Toast.makeText(ParentProfileActivity.this, "Server connection failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addKidParent(View view) {
        startActivity(new Intent(ParentProfileActivity.this, AddKidActivity.class));
    }
}