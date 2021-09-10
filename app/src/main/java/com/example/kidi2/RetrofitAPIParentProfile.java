package com.example.kidi2;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;

public interface RetrofitAPIParentProfile {

    @PUT("/updateParentInfo")
    Call<Void> createUpdateInfo(@Body Parent parent);
}
