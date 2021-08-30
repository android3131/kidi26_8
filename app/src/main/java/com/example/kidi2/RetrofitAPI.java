package com.example.kidi2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @GET("/finduser/{name}")
    Call<DataModelParent> retrieveUserByName(@Path("name") String name);
    @GET("/sendMail/{name}")
    Call<DataModelParent> sendMailToUser(@Path("name") String name);


    @GET("getkidslistbyparent/{id}")
    Call<List<Kid>> getAllKidsOfParent(@Path("id") String id);
    @GET("getkidsfinishedcoursessortedlist/{id}")
    Call<List<Course>> getkidsfinishedcoursessortedlist(@Path("id") String id);
   @GET("getkidsactivecoursessortedlist/{id}")
    Call<List<Meeting>> getAllKidsActiveCoursesSorted(@Path("id") String id);

}
