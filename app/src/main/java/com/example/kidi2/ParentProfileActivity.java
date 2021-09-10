package com.example.kidi2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ParentProfileActivity extends AppCompatActivity {
    boolean saveInfo = false;
    Retrofit retrofit ;
    RetrofitAPIParentProfile retrofitAPI ;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);
         pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
         editor = pref.edit();
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
        retrofitAPI = retrofit.create(RetrofitAPIParentProfile.class);
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
}