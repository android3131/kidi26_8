package com.example.kidi2;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetroFitAPI3 {

    //post new course
    @POST("/createCourse")
    Call<Course> postNewCourse(@Body Course newCourse);

    @GET("/getallCourses1")
    Call<List<Course1>> getAllCourses1();

    @PUT("/updateCourse")
    Call<Course> updateCourse(@Body Course updateCourse);
    @GET("getallCourses")
    Call<List<Course>> getAllCourses();
    @GET("/getCoursesOfCategory/{categoryid}")
    Call <List<Course>> getcoursesofcat(@Path("categoryid")String categoryid);

    @GET("/getAllCategories")
    Call<List<Category>> getallCat1();
    @PUT("/deleteCourse/{courseId}")
    Call <Boolean> deleteCourse2(@Path("courseId") String name);

}