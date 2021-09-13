package com.example.kidi2;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface RetrofitAPIAdminSetUser {

    @GET("/getAllLeaders")
    Call<List<Leaders>> retrieveAllLeaders();

    @GET("/getallparents")
    Call<List<Parent>> retrieveAllParents();

    @GET("/getAllAdmins")
    Call<List<Admin>> retrieveAllAdmins();

    @GET("/findByID/{id}")
    Call<Leaders> findLeaderByID(@Path("id") String id);


    @PUT("/updatestatus/{leaderID}")
    Call<List<Leaders>> updateLeadersStatus(@Path("id") String id);

}



