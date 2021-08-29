package com.example.kidi2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPI4 {
    @GET("getallCourses")
    Call<List<Course>> getAllCourses();


    @GET("getSpacesCourses")
    Call<List<Course>> getSpacesCourses();

    @GET("getAnimalsCourses")
    Call <List<Course>> getAnimalsCourses();
    @GET("getArtsCourses")
    Call <List<Course>> getArtsCourses();
    @DELETE("deleteCourse/{name}")
    Call <List<Course>> deleteCourse(@Path("name") String name);




    @GET("getallCourses1")
    Call<List<Course1>> getAllCourses1();
    @GET("getallKids")
    Call <List<Kid>> getAllKids();
    @GET("getallParents")
    Call <List<Parent>> getAllParents();
    @GET("getallCategories")
    Call<List<Category>> getAllCategories();
}
