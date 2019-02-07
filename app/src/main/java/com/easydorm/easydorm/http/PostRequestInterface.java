package com.easydorm.easydorm.http;

import com.easydorm.easydorm.entity.BaseResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public interface PostRequestInterface {

    @POST("check_token")
    Call<BaseResponse> checkToken(
            @Header("access_token") String accessToken
    );

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadFile(
            @Header("access_token") String token,
            @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("createTopic")
    Call<BaseResponse> createTopic(
            @Header("access_token") String token,
            @Field("tTitle") String tTitle,
            @Field("tContent") String tContent,
            @Field("tType") int tType
    );

    @Multipart
    @POST("updateUserInfo")
    Call<BaseResponse> updateUserInfo(
            @Header("access_token") String token,
            @Part MultipartBody.Part picture
    );

    @Multipart
    @POST("updateUserInfo")
    Call<BaseResponse> updateUserInfo(
            @Header("access_token") String token,
            @PartMap Map<String, RequestBody> data
    );

    @FormUrlEncoded
    @POST("createBack")
    Call<BaseResponse> createBack(
            @Header("access_token") String token,
            @Field("pId") int pId,
            @Field("content") String content,
            @Field("type") int type
    );

    @FormUrlEncoded
    @POST("createBack")
    Call<BaseResponse> createBack(
            @Header("access_token") String token,
            @Field("tId") int tId,
            @Field("pId") int pId,
            @Field("content") String content,
            @Field("type") int type
    );

    @FormUrlEncoded
    @POST("createBack")
    Call<BaseResponse> createBack(
            @Header("access_token") String token,
            @Field("tId") int tId,
            @Field("pId") int pId,
            @Field("content") String content,
            @Field("type") int type,
            @Field("pType") int pType
    );

}
