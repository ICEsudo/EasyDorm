package com.easydorm.easydorm.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface PostRequestInterface {

    @POST("register")
    @FormUrlEncoded
    Call<ResponseBody> register(@Field("username") String username, @Field("password") String password);

}
