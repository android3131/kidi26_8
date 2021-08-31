package com.example.kidi2;

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
    @GET("getkidslistbyparent/{id}")
    Call<List<Kid>> getAllKidsOfParent(@Path("id") String id);

    @GET("getkidsfinishedcoursessortedlist/{id}")
    Call<List<MeetingParticipant>> getkidsfinishedcoursessortedlist(@Path("id") String id);

    @GET("getkidsactivecoursessortedlist/{id}")
    Call<List<MeetingParticipant>> getAllKidsActiveCoursesSorted(@Path("id") String id);

    //KidName
    @GET("getkidbyid/{kidid}")
    Call<Kid> getKid(@Path("kidid") String kidid);

    @GET("getnumberactivecourses/{kidid}")
    Call<Integer> getNumberActiveCourses(@Path("kidid") String kidid);

    @GET("getnumbercompletedcourses/{kidid}")
    Call<Integer> getNumberCompletedCourses(@Path("kidid") String kidid);


}