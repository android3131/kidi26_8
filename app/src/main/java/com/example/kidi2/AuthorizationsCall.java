//package com.example.kidi2;
//
//import android.os.StrictMode;
//import android.util.Log;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonPrimitive;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.List;
//import java.util.logging.LogManager;
//import java.util.logging.Logger;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class AuthorizationsCall {
//    String baseURL;
//    LogInInfo logInInfo;
//    String authintication;
//
//    public AuthorizationsCall(String baseURL, LogInInfo logInInfo) {
//        this.baseURL = baseURL;
//
//        this.logInInfo = logInInfo;
//
//    }
//
//
//    public String getJWTAndRunFunction() {
//         Logger logger ;
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseURL)
//                .addConverterFactory(GsonConverterFactory.create())
//                // and build our retrofit builder.
//                .build();
//
//        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
//        Call<JsonObject> call = retrofitAPI.getJWT(logInInfo);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                JsonPrimitive json=response.body().getAsJsonPrimitive("token");
//                authintication = json.getAsString();
//              //  System.out.println("auth "+authintication);
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Log.e("MYAPP", "exception", t);
//            }
//        });
//
////        try {
////
////            Response response = call.execute();
////           // JsonObject json=response.body().;
////            authintication = (String) response.body().toString();
////System.out.println("auth "+authintication);
////
////        } catch (Exception e) {
////            Log.e("MYAPP", "exception", e);
////        }
//        return authintication;
//    }
//}
