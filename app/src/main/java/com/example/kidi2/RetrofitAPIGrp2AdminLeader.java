package com.example.kidi2;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitAPIGrp2AdminLeader {


    /**
     * posts a new leader
     * @param leader , the leader to be added
     * @return a list of parents.
     */
    @POST("createLeader")
    //creating a method to post our data.
    Call<Leader> addLeader(@Body Leader leader);


    @POST("updateLeader")
        //creating a method to post our data.
    Call<Leader> updateLeaderByID(@Body String id, Leader leader);


    @Multipart
    @POST("/spring-rest/fileserver/singlefileupload/")
    Call<Object> uploadImage(
            @Part MultipartBody.Part file, @Part("file") RequestBody name

    );


}



