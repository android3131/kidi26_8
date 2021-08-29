package com.example.kidi2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroFitAPI2 {



    //get courses from server
    @GET("getallCourses")
    Call<List<DataModel>> getString();

    @GET("getSpacesCourses")
    Call<List<DataModel>> getSpacesCourses();

    @GET("getAnimalsCourses")
    Call <List<DataModel>> getAnimalsCourses();
    @GET("getArtsCourses")
    Call <List<DataModel>> getArtsCourses();




}
