package com.example.kidi2;


import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.Call;

public interface RetrofitAPI6 {

    @GET("/getCoursesOfCategory/{categoryid}")
    Call<List<Course_Addactivity>> getCourseOfCategory(@Path("categoryid") int categoryid);

    @GET("/getCoursesOfChild/{childId}")
    Call<List<Course_Addactivity>> getCoursesOfChild(@Path("childId") int childId);

    @PUT("/UpdateChildsCourses/{childId}")
        //creating a method to post our data.
    Call<List<Course_Addactivity>> updateChilsCcurses(@Path("childId") int childId, @Body List<Course_Addactivity> courses);

}
