package com.example.kidi2;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface RetrofitAPILoginScreen {
    @POST("http://10.0.2.2:8090/checkuserpass")
        //creating a method to post our data.
    Call <HashMap<String,String>> createCheckUserPass(@Body User user);

    @PATCH("http://10.0.2.2:8090/updatepass")
    Call <Void> createUpdatePassword(@Body User user);

}
