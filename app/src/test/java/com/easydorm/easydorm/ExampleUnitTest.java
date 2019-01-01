package com.easydorm.easydorm;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.HttpUtil;
import com.easydorm.easydorm.Utils.MD5Util;
import com.easydorm.easydorm.Utils.ToastUtil;
import com.easydorm.easydorm.entity.BaseResponse;
import com.easydorm.easydorm.entity.User;
import com.easydorm.easydorm.entity.UserInfo;
import com.easydorm.easydorm.entity.UserToken;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.http.PostRequestInterface;
import com.easydorm.easydorm.http.TokenInterceptor;
import com.easydorm.easydorm.launch.activity.LoginActivity;
import com.easydorm.easydorm.main.MainActivity;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private String userId = "123";
    private String pw = "123";
    private int level = 0;

    private String accessToken, refreshToken;


    @Test
    public void md5Test() {
        System.out.println(MD5Util.encodeToHex(pw));
    }

    @Test
    public void loginTest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);

        Call<BaseResponse> call = getRequestInterface.login(userId, MD5Util.encodeToHex(pw), level);

        try {
            Response<BaseResponse> response =  call.execute();
            if(response.body() == null) {
                System.out.println("login : response body is null");
            } else {
                System.out.println("login : "+response.body().getMessage());
                if(response.body().getCode() == 1) {
                    refreshToken = response.headers().get("refresh_token");
                    accessToken = response.headers().get("access_token");
                    System.out.println("accessToken : "+accessToken);
                    System.out.println("refreshToken : "+refreshToken.substring(130));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateAvatarTest() {

//        loginTest();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestInterface postRequestInterface = retrofit.create(PostRequestInterface.class);

        File file = new File("src/main/ic_launcher-web.png");
        System.out.println("updateAvatarTest : " + file.getAbsolutePath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part avatar = MultipartBody.Part.createFormData("file", "psb.png", requestBody);

        Call<ResponseBody> call = postRequestInterface.uploadFile(accessToken, avatar);

        try {
            Response<ResponseBody> response =  call.execute();
            if(response.body() == null) {
                System.out.println("updateAvatarTest : response body is null");
                System.out.println("updateAvatarTest : "+response.code());
            } else {
                System.out.println("updateAvatarTest : "+response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tokenRefreshTest() {

        loginTest();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);

        Call<BaseResponse> call = getRequestInterface.refreshToken(refreshToken);

        System.out.println("tokenRefreshTest : try refresh token");

        try {
            Response<BaseResponse> response = call.execute();
            if(response.body() != null) {
                String access = response.headers().get("access_token");
                String ref = response.headers().get("refresh_token");
                if (ref != null) System.out.println("tokenRefreshTest : refresh: "+ ref.substring(130));
                if (access != null) {
                    System.out.println("tokenRefreshTest : access: " + access.substring(130));
                    System.out.println("tokenRefreshTest : refresh success");
                }
                if (response.body().getCode() == 2) {
                    System.out.println("tokenRefreshTest : refresh failed");
                }
            } else {
                System.out.println("tokenRefreshTest : body is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void createPostTest() {

        loginTest();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestInterface postRequestInterface = retrofit.create(PostRequestInterface.class);
        Call<BaseResponse> call = postRequestInterface.createTopic(accessToken, "111", "222", 1);
        try {
            BaseResponse response = call.execute().body();
            if(response != null) {
                System.out.println(response.getMessage());
            } else {
                System.out.println("body null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getPostTest() {
        loginTest();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);
        Call<BaseResponse> call = getRequestInterface.getTopic(accessToken);
        try {
            BaseResponse response = call.execute().body();
            if(response != null) {
                System.out.println(response.getMessage());
            } else {
                System.out.println("body null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}