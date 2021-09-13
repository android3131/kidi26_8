package com.example.kidi2;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {
    //FirstScreen
    @GET("/finduser/{name}")
    Call<DataModelParent> retrieveUserByName(@Path("name") String name);

    @GET("/sendMail/{name}")
    Call<DataModelParent> sendMailToUser(@Path("name") String name);

    //HomeLogin


    @GET("funweplangetkidscoursessorted/{id}")
    Call<HashMap<List<Kid>,List<Meeting>>> getAllKidsNextCoursesSorted(@Path("id") String id
                                                                      );

   // @GET("funwehadgetfinishedkidscoursessorted/{id}")
   // Call<HashMap<String, List<?>>> getAllKidsFinishedCoursesSorted(@Path("id") String id
                                                                   //        );

    //KidName
    @GET("getkidbyid/{kidid}")
    Call<Kid> getKid(@Path("kidid") String kidid);

    @GET("getnumberactivecourses/{kidid}")
    Call<Integer> getNumberActiveCourses(@Path("kidid") String kidid);

    @GET("getnumbercompletedcourses/{kidid}")
    Call<Integer> getNumberCompletedCourses(@Path("kidid") String kidid);

    @GET("getlistofkidsactivecoursessorted/{id}")
    Call<List<Meeting>> getAllKidsActiveCoursesSortedKidProfile(@Path("id") String id
                                                                );

    @GET("getlistofkidscompletedcoursessortedkid/{id}")
    Call<List<Meeting>> getAllKidsCompletedCoursesSortedKidProfile(@Path("id") String id
                                                                  );

    @POST("/authenticate")
    Call<JsonObject> getJWT(@Body LogInInfo logInInfo);

    @GET("getmeetingbyid/{id}")
    Call<Meeting> getMeetingById(@Path("id") String id);

    @POST("/addNewParent")
     Call<Parent> createParent(@Body Parent parent);


    @GET("/getNewKids/{period}")
    Call<HashMap<String,Integer>>getNewKids(@Path("period") int period);

    @GET("/getNewParents/{period}")
    Call<HashMap<String,Integer>> getNewParents(@Path("period") int period);

    @GET("/getKidsCountByCategory/{period}")
    Call<HashMap<String,Integer>> getKidsCountByCategory(@Path("period") int period);

    @GET("/getActivityTime/{period}")
    Call<HashMap<String,Double>> getActivityTime(@Path("period") int period);




    //==================activity Pie================//
    @GET("/getactivitiesperweek")
    Call <HashMap<String, Integer>> createGetActivityPerWeek();

    @GET("/getactivitiespermonth")
    Call <HashMap<String, Integer>>createGetActivityPerMonth();

    @GET("/getactivitiesperyear")
    Call <HashMap<String, Integer>>createGetActivityPerYear();


    //=============kids pie=========
    @GET("/getlistofactivekidsperweek")
    Call <HashMap<String, Integer>> createGetActiveKidsPerWeek();

    @GET("/getlistofactivekidspermonth")
    Call <HashMap<String, Integer>> createGetActiveKidsPerMonth();

    @GET("/getlistofactivekidsperyear")
    Call <HashMap<String, Integer>> createGetActiveKidsPerYear();



    //============= parents pie ==========

    @GET("/getallactiveparentsbyweek")
    Call <HashMap<String, Integer>> createGetActiveParentsPerWeek();

    @GET("/getallactiveparentsbymonth")
    Call<HashMap<String, Integer>> activeParentsMonth();

    @GET("/getallactiveparentsbyyear")
    Call<HashMap<String, Integer>>activeParentsYear();


    //========= gets by category pie==========/

    @GET("/getlistofactivekidspercategoryperweek")
    Call<HashMap<String, Integer>> activeKidsCategWeek();

    @GET("/getlistofactivekidspercategorypermonth")
    Call<HashMap<String, Integer>> activeKidsCategMonth();

    @GET("/getlistofactivekidspercategoryperyear")
    Call<HashMap<String, Integer>> activeKidsCategYear();

}