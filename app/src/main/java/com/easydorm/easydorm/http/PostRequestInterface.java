package com.easydorm.easydorm.http;

import com.easydorm.easydorm.entity.BaseResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface PostRequestInterface {

    @POST("check_token")
    Call<BaseResponse> checkToken(
            @Header("access_token") String accessToken
    );

}
