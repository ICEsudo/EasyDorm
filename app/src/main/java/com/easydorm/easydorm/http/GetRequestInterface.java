package com.easydorm.easydorm.http;

import com.easydorm.easydorm.entity.BaseResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetRequestInterface {


    @GET("login")
    Call<BaseResponse> login(
            @Query("username") String username,
            @Query("password") String password,
            @Query("level") int level
    );


    @GET("refresh")
    Call<BaseResponse> refreshToken(
            @Header("refresh_token") String refreshToken
    );


    @GET("getTopics")
    Call<BaseResponse> getTopics(
            @Header("access_token") String accessToken,
            @Query("page") int page
    );

    @GET("getTopic")
    Call<BaseResponse> getTopic(
            @Header("access_token") String accessToken,
            @Query("tId") int tId
    );


    @GET("getUserInfo")
    Call<BaseResponse> getUserInfo(
            @Header("access_token") String accessToken,
            @Query("sId") int userId
    );

}
