package com.example.kidi2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetroFitAPI3 {



    /* for admin */
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

    /* for first parent reg */
    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorization);

}