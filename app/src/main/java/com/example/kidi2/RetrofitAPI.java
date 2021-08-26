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
    //public Parent getSpecificParent (String email, String password)
    @GET("/getallkids/{id}")
     Call<List<DataModelKid>> GetAllKidsOfParent(@Path("id") String id);
    //public List<Kid> GetAllKidsOfParent (String id)
    @GET("/getspecifickid/{id}")
    Call<DataModelParent> getSpecificKid(@Path("id") String id);
    //public Kid getSpecificKid (String parentId, Strind kidId)

    //public Kid addProfilePicture (String parentId, String kidId, String picture)
    //public List<Course> getKidActiveCourses (String parentId, String kidId)
    //public List<String> getKidCompletedCourses (String parentId, String kidId)
}
