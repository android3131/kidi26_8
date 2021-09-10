package com.example.kidi2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RetrofitAPI4 {
    @GET("getallCourses")
    Call<List<Course2>> getAllCourses();


    @GET("getSpacesCourses")
    Call<List<Course2>> getSpacesCourses();

    @GET("getAnimalsCourses")
    Call <List<Course2>> getAnimalsCourses();
    @GET("getArtsCourses")
    Call <List<Course2>> getArtsCourses();
    @DELETE("deleteCourse/{name}")
    Call <List<Course2>> deleteCourse(@Path("name") String name);




    @GET("getallCourses1")
    Call<List<Course1>> getAllCourses1();
    @GET("getallKids")
    Call <List<Kid>> getAllKids();
    @GET("getallParents")
    Call <List<Parent>> getAllParents();
    @GET("getallCategories")
    Call<List<Category>> getAllCategories();
    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorization);
}
