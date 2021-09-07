package com.example.kidi2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPIAdminSetLeader {



        @GET("/getAllLeaders")
        Call<List<Leaders>> retrieveAllLeaders();

        @GET("/findByID/{id}")
        Call<Leaders> findLeaderByID(@Path("id") String id);


        @PUT("/updatestatus/{leaderID}")
        Call<List<Leaders>> updateLeadersStatus(@Path("id") String id);

    }





