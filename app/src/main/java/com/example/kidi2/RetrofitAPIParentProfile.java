package com.example.kidi2;



import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPIParentProfile {

    @PUT("/updateParentInfo")
    Call<Void> createUpdateInfo(@Body Parent parent);

    @GET("/getParentById/{parentid}")
    Call<Parent> getParent(@Path("parentid") String parentid);

    @POST("/authenticate")
    Call<JsonObject> getJWT(@Body LogInInfo logInInfo);

    @GET("/getAllParentsChildren/{parentId}")
    Call<List<Kid>> getParentsChildren(@Path("parentId") String parentId);
}