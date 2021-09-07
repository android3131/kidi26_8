package com.example.kidi2;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPI {
    //FirstScreen
    @GET("/finduser/{name}")
    Call<DataModelParent> retrieveUserByName(@Path("name") String name);

    @GET("/sendMail/{name}")
    Call<DataModelParent> sendMailToUser(@Path("name") String name);

    //HomeLogin

    @GET("funweplangetkidscoursessorted/{id}")
    Call<HashMap<List<Kid>,List<Meeting>>> getAllKidsNextCoursesSorted(@Path("id") String id);
    @GET("funwehadgetfinishedkidscoursessorted/{id}")
    Call<HashMap<List<Kid>,List<Meeting>>> getAllKidsFinishedCoursesSorted(@Path("id") String id);
    //KidName
    @GET("getkidbyid/{kidid}")
    Call<Kid> getKid(@Path("kidid") String kidid);

    @GET("getnumberactivecourses/{kidid}")
    Call<Integer> getNumberActiveCourses(@Path("kidid") String kidid);

    @GET("getnumbercompletedcourses/{kidid}")
    Call<Integer> getNumberCompletedCourses(@Path("kidid") String kidid);
    @GET("getlistofkidsactivecoursessorted/{id}")
    Call<List<Meeting>> getAllKidsActiveCoursesSortedKidProfile(@Path("id") String id);
    @GET("getlistofkidscompletedcoursessortedkid/{id}")
    Call<List<Meeting>> getAllKidsCompletedCoursesSortedKidProfile(@Path("id") String id);


}