package com.example.kidi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;

public class LogInScreenActivity extends AppCompatActivity {
    Button Login;
    EditText usernameText;
    EditText passwordText;
    TextView forgotPassword;
    String leaderId;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    //Retrofit calls
    Retrofit retrofit;

    RetrofitAPILoginScreen retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
        retrofitAPI = retrofit.create(RetrofitAPILoginScreen.class);
        usernameText = (EditText) findViewById(R.id.username_field);
        passwordText = (EditText) findViewById(R.id.password_field);
        Login = findViewById(R.id.signin_button);
        forgotPassword = findViewById(R.id.forgot_password);

        pref = getSharedPreferences("MyKIDIPref", MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();

        View forgotB = findViewById(R.id.forgot_password);
        forgotB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


    }

    private void checkData(String username, String password) {
        ////////////////////// delete after test ///////////////
        if (username.equals("Parent")) {
            startActivity(new Intent(LogInScreenActivity.this, HomeLogin.class));
            return;
        } else if (username.equals("Admin")) {
            startActivity(new Intent(LogInScreenActivity.this, AdminMainActivity.class));
            return;
        }


        ////////////////////////////////////////
        Log.d("Mohammad", "success" + username + " " + password);
        User user = new User(username, password);
        editor.putString("username", username);
        editor.putString("password", password);
//        LogInInfo logInInfo=new LogInInfo(pref.getString("username",null),
//                pref.getString("password",null));
//        AuthorizationsCall authorizationsCall=new AuthorizationsCall(getString(R.string.BASE_URL),logInInfo);
        Call<HashMap<String, String>> call = retrofitAPI.createCheckUserPass(user);
        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Mohammad", "success");
                    System.out.println(response.body().get("flag") + "flag");
                    if (response.body().get("flag").equals("0")) {
                        System.out.println("flag 0");
                        usernameText.getText().clear();
                        passwordText.setText("");
                        Toast.makeText(LogInScreenActivity.this, "Username or password are worng", Toast.LENGTH_SHORT).show();

                    }
                    // ADMIN username and password
                    else if ((response.body().get("flag")).equals("1")) {
                        System.out.println("flag 1");
                        editor.putString("adminID", response.body().get("ID"));
                        editor.commit();
                        Toast.makeText(LogInScreenActivity.this, "---ADMIN LOGIN---", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInScreenActivity.this, AdminMainActivity.class));

//                        startActivity(new Intent(LogInScreenActivity.this, StatisticsActivity.class));
                    }
                    //LEADER  username and password -> first access change password
                    else if ((response.body().get("flag")).equals("2")) {
                        System.out.println("flag 2");

                        editor.putString("leaderID", response.body().get("ID"));
                        editor.commit();
                        Toast.makeText(LogInScreenActivity.this, "---LEADER LOGIN FIRST TIME---", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInScreenActivity.this, LeaderFirstLoginActivity.class));
                    }
                    //LEADER  username and password -> not a first access change password
                    else if ((response.body().get("flag")).equals("3")) {
                        System.out.println("flag 3");
                        editor.putString("userType", "leader");
                        editor.putString("leaderID", response.body().get("ID"));
                        editor.commit();
                        Toast.makeText(LogInScreenActivity.this, "---LEADER LOGIN ---", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(LogInScreenActivity.this, HomeLogin.class));
                    }
                    //PARENTS username and password
                    else if ((response.body().get("flag")).equals("4")) {
                        editor.putString("userType", "parent");
                        String id = response.body().get("ID");
                        editor.putString("parentID", id);
                        editor.commit();
                        Toast.makeText(LogInScreenActivity.this, "---PARENT LOGIN---", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInScreenActivity.this, HomeLogin.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Log.d("Mohammad", "failed");
                usernameText.setText("");
                passwordText.setText("");
                Toast.makeText(LogInScreenActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SendData(View view) {
        if (usernameText.getText().toString().isEmpty() ||
                passwordText.getText().toString().isEmpty()) {
            Toast.makeText(LogInScreenActivity.this, "One or more field are empty", Toast.LENGTH_SHORT).show();
            return;
        }
//                startActivity(new Intent(LogInScreenActivity.this, HomeLogin.class));
        // calling a method to check username and password
        checkData(usernameText.getText().toString(),
                passwordText.getText().toString()

        );
        System.out.println(pref.getString("parentID", "") + "wa7wa7");
    }

    public void openDialog() {
        DialogAdapter dialog = new DialogAdapter(LogInScreenActivity.this);
        dialog.show(getSupportFragmentManager(), "dialog");
    }
}
