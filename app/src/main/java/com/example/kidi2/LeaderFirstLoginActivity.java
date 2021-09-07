package com.example.kidi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LeaderFirstLoginActivity extends AppCompatActivity {
    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    EditText usernameText;
    EditText passwordText;
    //Retrofit calls
    Retrofit retrofit ;

    RetrofitAPILoginScreen retrofitAPI ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_first_login);
         retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL)
                )
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
         retrofitAPI = retrofit.create(RetrofitAPILoginScreen.class);
        pref = getSharedPreferences("MyKIDIPref", MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();
        usernameText = (EditText) findViewById(R.id.username_field);
        passwordText = (EditText) findViewById(R.id.password_field);
    }

    public void confirmData(View view) {
        if (usernameText.getText().toString().isEmpty() ||
                passwordText.getText().toString().isEmpty()) {
            Toast.makeText(LeaderFirstLoginActivity.this, "One or more field are empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!usernameText.getText().toString().equals(passwordText.getText().toString())){
            Toast.makeText(LeaderFirstLoginActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }
//                startActivity(new Intent(LogInScreenActivity.this, HomeLogin.class));
        // calling a method to check username and password
        updateData(passwordText.getText().toString());
    }

    private void updateData( String password) {
        User user = new User();
        String leaderId = pref.getString("leaderID", null);
        System.out.println(leaderId);
        user.setId(leaderId);
        user.setPassword(password);
        Call<Void> call = retrofitAPI.createUpdatePassword(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(LeaderFirstLoginActivity.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Mohammad", "failed");
                usernameText.setText("");
                passwordText.setText("");
                Toast.makeText(LeaderFirstLoginActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });
    }
}