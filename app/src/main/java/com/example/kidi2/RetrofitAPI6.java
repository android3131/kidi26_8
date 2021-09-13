package com.example.kidi2;


import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.Call;

public interface RetrofitAPI6 {

    @GET("/getCategoriesForNewActivity/{parentId}/{kidId}")
    Call<List<Course>> getCategoriesForNewAct(@Path("parentId") String parentId, @Path("kidId") String kidId);

    @GET("/getCoursesForNewActivity/{parentId}/{kidId}/{catId}")
    Call<List<Course>> getCourseByCatNewAct(@Path("parentId") String parentId, @Path("kidId") String kidId, @Path("catId") String catId);

    @GET("/getAllParentsChildren/{parentId}")
    Call<List<Kid>> getParentsChildren(@Path("parentId") String parentId);

    @POST("/addCourseToChild/{parentId}/{kidId}/{courseId}")
    Call<Boolean> updateKidsCourses(@Path("parentId") String parentId, @Path("kidId") String kidId, @Path("courseId") String courseId);



}
