package com.example.kidi2;


import java.util.HashMap;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPIAdminMain {


    @GET("http://10.0.2.2:8090/getactivitiesperweek")
    Call <HashMap<String, Integer>> createGetActivityPerWeek();

    @GET("http://10.0.2.2:8090/getactivitiespermonth")
    Call <HashMap<String, Integer>>createGetActivityPerMonth();

    @GET("http://10.0.2.2:8090/getactivitiesperyear")
    Call <HashMap<String, Integer>>createGetActivityPerYear();

    @GET("http://10.0.2.2:8090/getpercentactivitiesperweek")
    Call <Double> createGetPercentActivitiesPerWeek();

    @GET("http://10.0.2.2:8090/getpercentactivitiespermonth")
    Call <Double> createGetPercentActivitiesPerMonth();

    @GET("http://10.0.2.2:8090/getpercentactivitiesperyear")
    Call <Double> createGetPercentActivitiesPerYear();


    //=============bottom left pie=========
    @GET("http://10.0.2.2:8090/getlistofactivekidsperweek")
    Call <HashMap<String, Integer>>createGetActiveKidsPerWeek();

    @GET("http://10.0.2.2:8090/getlistofactivekidspermonth")
    Call <HashMap<String, Integer>>createGetActiveKidsPerMonth();

    @GET("http://10.0.2.2:8090/getlistofactivekidsperyear")
    Call <HashMap<String, Integer>>createGetActiveKidsPerYear();


    @GET("http://10.0.2.2:8090/getpercentactivekidsbyweek")
    Call <Double> createGetPercentActiveKidsPerWeek();

    @GET("/getpercentactivekidsbymonth")
    Call <Double> createGetPercentActiveKidsPerMonth();

    @GET("http://10.0.2.2:8090/getpercentactivekidsbyyear")
    Call <Double> createGetPercentActiveKidsPerYear();

    //============= top right pie ==========

    @GET("http://10.0.2.2:8090/getallactiveparentsbyweek")
    Call <HashMap<String, Integer>>createGetActiveParentsPerWeek();

    @GET("http://10.0.2.2:8090/getallactiveparentsbymonth")
    Call <HashMap<String, Integer>>createGetActiveParentsPerMonth();

    @GET("http://10.0.2.2:8090/getallactiveparentsbyyear")
    Call <HashMap<String, Integer>>createGetActiveParentsPerYear();


    @GET("http://10.0.2.2:8090/getpercentactiveparentsbyweek")
    Call <Double> createGetPercentActiveParentsByWeek();

    @GET("http://10.0.2.2:8090/getpercentactiveparentsbymonth")
    Call <Double> createGetPercentActiveParentsByMonth();

    @GET("http://10.0.2.2:8090/getpercentactiveparentsbyyear")
    Call <Double> createGetPercentActiveParentsByYear();


    //======== bottom right pie ========

    @GET("http://10.0.2.2:8090/getlistofactivekidspercategoryperweek")
    Call <HashMap<String, Integer>>createGetActiveKidsPerCategoryPerWeek();

    @GET("http://10.0.2.2:8090/getlistofactivekidspercategorypermonth")
    Call <HashMap<String, Integer>>createGetActiveKidsPerCategoryPerMonth();

    @GET("http://10.0.2.2:8090/getlistofactivekidspercategoryperyear")
    Call <HashMap<String, Integer>>createGetActiveKidsPerCategoryPerYear();

    //======== bar graph ========

    @GET("getkidsbycategorymonth/{category}")
    Call <TreeMap<Integer, Integer>>createGetKidsByCategoryMonth(@Path("category") String category);

    @GET("getkidsbycategoryweek/{category}")
    Call <TreeMap<Integer, Integer>>createGetKidsByCategoryYear(@Path("category") String category);

    @GET("getkidsbycategoryday/{category}")
    Call <TreeMap<Integer, Integer>>createGetKidsByCategoryDay(@Path("category") String category);


}