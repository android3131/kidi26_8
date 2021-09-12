package com.example.kidi2;


import java.util.HashMap;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPIAdminMain {


    @GET("/getactivitiesperweek")
    Call <HashMap<String, Integer>> createGetActivityPerWeek();

    @GET("/getactivitiespermonth")
    Call <HashMap<String, Integer>>createGetActivityPerMonth();

    @GET("/getactivitiesperyear")
    Call <HashMap<String, Integer>>createGetActivityPerYear();



    //=============bottom left pie=========
    @GET("/getNewKids/{period}")
    Call <HashMap<String, Integer>>createGetActiveKids(@Path("period") int period);

//    @GET("/getlistofactivekidspermonth")
//    Call <HashMap<String, Integer>>createGetActiveKidsPerMonth();
//
//    @GET("/getlistofactivekidsperyear")
//    Call <HashMap<String, Integer>>createGetActiveKidsPerYear();


    @GET("/getpercentactivekidsbyweek")
    Call <Double> createGetPercentActiveKidsPerWeek();

    @GET("/getpercentactivekidsbymonth")
    Call <Double> createGetPercentActiveKidsPerMonth();

    @GET("/getpercentactivekidsbyyear")
    Call <Double> createGetPercentActiveKidsPerYear();

    //============= top right pie ==========

    @GET("/getallactiveparentsbyweek")
    Call <HashMap<String, Integer>>createGetActiveParentsPerWeek();

    @GET("/getallactiveparentsbymonth")
    Call <HashMap<String, Integer>>createGetActiveParentsPerMonth();

    @GET("/getallactiveparentsbyyear")
    Call <HashMap<String, Integer>>createGetActiveParentsPerYear();


    @GET("/getpercentactiveparentsbyweek")
    Call <Double> createGetPercentActiveParentsByWeek();

    @GET("/getpercentactiveparentsbymonth")
    Call <Double> createGetPercentActiveParentsByMonth();

    @GET("/getpercentactiveparentsbyyear")
    Call <Double> createGetPercentActiveParentsByYear();


    //======== bottom right pie ========

    @GET("/getlistofactivekidspercategoryperweek")
    Call <HashMap<String, Integer>>createGetActiveKidsPerCategoryPerWeek();

    @GET("/getlistofactivekidspercategorypermonth")
    Call <HashMap<String, Integer>>createGetActiveKidsPerCategoryPerMonth();

    @GET("/getlistofactivekidspercategoryperyear")
    Call <HashMap<String, Integer>>createGetActiveKidsPerCategoryPerYear();

    //======== bar graph ========

    @GET("getkidsbycategorymonth/{category}")
    Call <TreeMap<Integer, Integer>>createGetKidsByCategoryMonth(@Path("category") String category);

    @GET("getkidsbycategoryweek/{category}")
    Call <TreeMap<Integer, Integer>>createGetKidsByCategoryYear(@Path("category") String category);

    @GET("getkidsbycategoryday/{category}")
    Call <TreeMap<Integer, Integer>>createGetKidsByCategoryDay(@Path("category") String category);


}