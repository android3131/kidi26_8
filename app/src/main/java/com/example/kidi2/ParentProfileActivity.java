package com.example.kidi2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ParentProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);
        ImageView backBtn = findViewById(R.id.back_arrow_parent);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentProfileActivity.this, HomeLogin.class));
            }
        });
    }

    public void editeInfo(View view) {
        EditText name = findViewById(R.id.name_text);
        EditText country = findViewById(R.id.country_text);
        EditText address = findViewById(R.id.address_text);
        EditText city = findViewById(R.id.city_text);
        EditText phone = findViewById(R.id.phone_text);
        EditText email = findViewById(R.id.email_text);
        name.clearFocus();
        country.requestFocus();
        city.requestFocus();
        phone.requestFocus();
        email.requestFocus();
        address.requestFocus();

    }
}