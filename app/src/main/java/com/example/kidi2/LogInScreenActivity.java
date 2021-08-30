package com.example.kidi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInScreenActivity extends AppCompatActivity {
    Button Login;
    EditText usernameText;
    EditText passwordText;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        usernameText = (EditText)findViewById(R.id.password_field);
        passwordText = (EditText)findViewById(R.id.password_field);
        Login = findViewById(R.id.signin_button);
        forgotPassword = findViewById(R.id.forgot_password);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameText.getText().toString().isEmpty() ||
                        passwordText.getText().toString().isEmpty()) {
                    Toast.makeText(LogInScreenActivity.this, "One or more field are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                /// TODO: temporary when Log in is pressed move to the next page
                startActivity(new Intent(LogInScreenActivity.this, HomeLogin.class));

                // calling a method to check username and password
                checkData(usernameText.getText().toString(),
                        passwordText.getText().toString()
                        
                );
            }
        });
    }

    private void checkData(String username, String password) {


    }
}