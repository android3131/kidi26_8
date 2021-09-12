package com.example.kidi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstScreen extends AppCompatActivity {
    private TextView privacyT,termsT,forgotB;
    private Button loginB,firstB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
        termsT = findViewById(R.id.termsText2);
        termsT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog2();
            }
        });
        privacyT = findViewById(R.id.privacyText2);
       privacyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog3();
            }
        });

        loginB = findViewById(R.id.LoginButton);
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstScreen.this, LogInScreenActivity.class));
            }
        });
        firstB = findViewById(R.id.VisitButton);
        firstB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstScreen.this, FirstParentReg.class));
            }
        });
        forgotB = findViewById(R.id.forgotButton);
        forgotB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
       // SharedPreferences pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
       // SharedPreferences.Editor editor = pref.edit();
       // editor.putString("ParentID", "207931536"); // Storing integer
      //  editor.commit();//this should be in the log in screen


    }


    public void openDialog(){
        DialogAdapter dialog=new DialogAdapter(FirstScreen.this);
        dialog.show(getSupportFragmentManager(), "dialog");
    }
    public void openDialog2(){
        TermsOfService dialog=new TermsOfService(this);
        dialog.show(getSupportFragmentManager(), "TermsOfService");
    }
    public void openDialog3(){
        PrivacyPolicy dialog=new PrivacyPolicy(this);
        dialog.show(getSupportFragmentManager(), "PrivacyPolicy");
    }



}