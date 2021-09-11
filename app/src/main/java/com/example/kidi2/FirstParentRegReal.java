package com.example.kidi2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstParentRegReal extends AppCompatActivity {

    /**
     * a Final string is created as the name of the application SharedPreferences name.
     * SharedPreferences is used to save and retain data from the application.
     */
    public static final String MYPREF = "MyKIDIPref";
    EditText password1, password2, fullName, email, phone;
    Button next, prev;
    Spinner phoneCode;
    SharedPreferences pref;
    public ArrayList<com.example.kidi2.ParentGrp2> parents=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_first_parent);
        pref = getSharedPreferences(MYPREF,0);
        SharedPreferences.Editor editor = pref.edit();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();

        // create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        /**
         * a Spinner is created from locally saved phone area codes.
         */
        phoneCode = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dialCodes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        phoneCode.setAdapter(adapter);
        phoneCode.setSelection(pref.getInt("code", 100));
        password1 = findViewById(R.id.editTextTextPassword);
       // password1.setText(pref.getString("password", ""));
        password2 = findViewById(R.id.editTextTextPassword2);
       // password2.setText(pref.getString("password", ""));
        fullName = findViewById(R.id.editTextTextPersonName);
        fullName.setText(pref.getString("fullName", ""));
        email = findViewById(R.id.editTextTextEmailAddress);
        email.setText(pref.getString("email", ""));
        phone = findViewById(R.id.editTextPhone2);
        phone.setText(pref.getString("phone", ""));
        next = findViewById(R.id.next);
        prev = findViewById(R.id.back);
        /**
         * onClick listener for next button
         */
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkName() && checkEmail() && checkPhone() && check_password()) {
                Parent p = new Parent(fullName.getText().toString(),phone.getText().toString(), email.getText().toString()
                        , password1.getText().toString());

                //parents.add(p);
                Log.d("Muhannad", p.toString());
                /**
                 * the new created parent, wrapped by retrofit in order to send post http request.
                 */
                Call<Parent> call = retrofitAPI.createParent(p);
                /**
                 * when onResponse is called we get response from our api and the view will change to ThirdRegScreen, otherwise onFailure will return error toast.
                 */
                call.enqueue(new Callback<Parent>() {
                    @Override
                    public void onResponse(Call<Parent> call, Response<Parent> response) {
                        // this method is called when we get response from our api.
                        editor.putString("fullName", fullName.getText().toString());
                        editor.putString("email", email.getText().toString());
                        editor.putString("phone", phone.getText().toString());
                        editor.putString("password", password1.getText().toString());
                        editor.putInt("code", phoneCode.getSelectedItemPosition());
                        editor.putString("parentIDReg",response.body().getId());
                      //  List<Parent> prtLst = response.body();
                       // editor.putString("Id", prtLst.get(prtLst.size()-1).getId());
                        editor.commit();
                        Toast.makeText(FirstParentRegReal.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        if (response.code() == 400) {
                            Log.v("Error code 400",response.errorBody().toString());
                        }
                        Parent responseFromAPI =response.body();
                        Log.d("Muhannad", "Response= "+response.body());
                        //Log.d("Muhannad", "responseFromAPI, " + responseFromAPI);
                        Toast.makeText(FirstParentRegReal.this, "The new Parent: " + p.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FirstParentRegReal.this, ThirdParentRegActivity.class);//elie changed this
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Parent> call, Throwable t) {
                        int x=5;
                        Log.d("Muhannad", "responseFromAPI " + t);
                        Toast.makeText(FirstParentRegReal.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
//                    Intent intent = new Intent(FirstParentRegReal.this, ThirdParentRegActivity.class);//elie changed this
//                    startActivity(intent);
                }


            }
        });

        /**
         * onClick listener for back button
         */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fullName.setText("");
//                phone.setText("");
//                email.setText("");
//                password1.setText("");
//                editor.clear();
//                editor.commit();
                Intent intent = new Intent(FirstParentRegReal.this, FirstParentReg.class);//elie changed this
                startActivity(intent);
            }
        });
    }


    /**
     * a boolean method to validate password and retype password, if some thing is wrong an error message will be shown.
     * @return false, if passwords are not the same or don't apply the constraints.
     * @return true, when the validation passes
     */
    public boolean check_password() {
        String pass1 = password1.getText().toString();
        String pass2 = password2.getText().toString();
        if (! pass1.equals(pass2)){
            password1.setError("Passwords are not the same");
            return false;
        }

        else if (! pass1.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            password1.setError("Password must be at least 8 characters, containing at least one number,one lower case and one upper case");
            return false;
        }
        return true;
    }

    /**
     * a boolean method to validate the full name, when validation fails an error message will be shown.
     * @return false, when the name is less than 6 or larger than 40 characters. or lacks a white space.
     * @return true, when the validation passes.
     */
    public boolean checkName(){
        if (!fullName.getText().toString().matches("(?=^.{6,40}$)^[a-zA-Z-]+\\s[a-zA-Z-]+$")) {
            fullName.setError("Full name, must include white space and letters only");
            return false;
        }
        return true;
    }

    /**
     * a boolean method to validate email address, when validation fails an error message will be shown.
     * @return false, when the email is not valid (no @domain.com for example).
     * @return true, when the validation passes.
     */
    public boolean checkEmail(){
        if (!email.getText().toString().matches(Patterns.EMAIL_ADDRESS.pattern())) {
            email.setError("Email must contain @domain. etc");
            return false;
        }
        return true;
    }

    /**
     * a boolean method to validate the phone number, when validation fails an error message will be shown.
     * @return false, when the phone number is invalid.
     * @return true, when the validation passes.
     */
    public boolean checkPhone(){
        if (!phone.getText().toString().matches(Patterns.PHONE.pattern())) {
            phone.setError("Phone number is not valid");
            return false;
        }
        return true;
    }
}
