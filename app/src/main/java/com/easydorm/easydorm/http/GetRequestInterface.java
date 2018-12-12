package com.easydorm.easydorm.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRequestInterface {


    @GET("login")
    Call<ResponseBody> login(@Query("username") String username, @Query("password") String password);


}
