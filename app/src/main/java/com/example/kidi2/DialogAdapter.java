package com.example.kidi2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DialogAdapter extends AppCompatDialogFragment {

    //private DialogListener listener;
    private EditText editTextEmail;
private Context ctx;
  DialogAdapter(Context ctx){
      this.ctx=ctx;
  }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.layout_dialog,null);

        builder.setView(view)

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String email=editTextEmail.getText().toString();
                        checkMail(email);

                        //listener.applyTexts(Email);
                    }
                });
        editTextEmail=view.findViewById(R.id.editTextEmail1);
        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void checkMail(String email){

       if(!isEmailValid(email)){
           Toast.makeText(ctx,"invalid email, please try again"  ,Toast.LENGTH_LONG).show();
       }
        else {
           Retrofit retrofit = new Retrofit.Builder()
                   .baseUrl(getString(R.string.BASE_URL)
                   )
                   // when sending data in json format we have to add Gson converter factory
                   .addConverterFactory(GsonConverterFactory.create())
                   // and build our retrofit builder.
                   .build();
           // create an instance for our retrofit api class.
           RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
           //DataModel user = new DataModel();

           Call<DataModelParent> call = retrofitAPI.retrieveUserByName(email);
           call.enqueue(new Callback<DataModelParent>() {



               @Override

               public void onResponse(Call<DataModelParent> call, Response<DataModelParent> response) {
                   // this method is called when we get response from our api.


                   //setting edit text to empty
                   editTextEmail.setText("");




                  // Call<DataModelParent> call2 = retrofitAPI.sendMailToUser(email);


                   // getting response from our body
                   // and passing it to our model class.
                   DataModelParent responseFromAPI = response.body();

                   // getting our data from model class and adding it to our string.
                   String responseString = "Response Code : " + response.code() +
                           "\nManufacturer : " + responseFromAPI.getEmail();

                   //Toast.makeText(getActivity(), "The email is " + responseString, Toast.LENGTH_SHORT).show();

                   //String responseString = "Response Code : " + response.code() + "\n" + response.body();
//System.out.println("email has been sent");
                   // setting responseString string to our text view.
                   //responseTV.setText(responseString);
                   Toast.makeText(ctx, "email has been sent", Toast.LENGTH_LONG).show();


               }

               @Override
               public void onFailure(Call<DataModelParent> call, Throwable t) {
                   int x = 5;
                   // setting text to our text view when

                   // we get error response from API.
                   // responseTV.setText("Error found is : " + t.getMessage());
                   Toast.makeText(ctx, "User does not exist", Toast.LENGTH_LONG).show();
               }
           });

       }

    }



}

