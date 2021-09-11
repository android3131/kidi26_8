package com.example.kidi2;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetroFitAPI2 {



    //get courses from server
    @GET("getallCourses")
    Call<List<DataModel>> getString();

    /* for first parent reg */
    @GET("getSpacesCourses")
    Call<List<DataModel>> getSpacesCourses();

    @GET("getAnimalsCourses")
    Call <List<DataModel>> getAnimalsCourses();
    @GET("getArtsCourses")
    Call <List<DataModel>> getArtsCourses();
    @GET("/getAllCategories")
    Call <List<Category>> getallctg();

    @GET("/getCoursesOfCategory/{categoryid}")
    Call <List<Course4>> getcatcourses(@Path("categoryid") String categoryid );
    @GET()
    @Streaming
    Call<ResponseBody> downloadImage(@Url String fileUrl);
/////////////////////////////////////////////////////////////////////
    /* for admin */

    //get courses from server
    @GET("getAllCourses")
    Call<List<Course>> getAllCourses();
    @GET("/getCoursesOfCategory/{categoryid}")
    Call <List<Course>> getcoursesofcat(@Path("categoryid")String categoryid);

    @GET("/getAllCategories")
    Call<List<Category>> getallCat();

    @DELETE("deleteCourse/{name}")
    Call <List<Course2>> deleteCourse(@Path("name") String name);
    @PUT("/deleteCourse/{courseId}")
    Call <Boolean> deleteCourse1(@Path("courseId") String name);











}
