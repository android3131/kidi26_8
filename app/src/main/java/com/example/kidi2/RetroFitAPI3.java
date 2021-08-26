package com.example.myapplication3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroFitAPI3 {

    //post new course
    @POST("/createCourse1")
    Call<Course1> postNewCourse(@Body Course1 newCourse);

    @GET("/getallCourses1")
    Call<List<Course1>> getAllCourses1();
}