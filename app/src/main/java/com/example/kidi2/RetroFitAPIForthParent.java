package com.example.kidi2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetroFitAPIForthParent {



    @GET("/{courseID}")
    Call<Course> getByID(@Path("courseID") String courseID);


}