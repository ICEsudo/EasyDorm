package com.easydorm.easydorm;

import android.content.Intent;
import android.widget.Toast;

import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.MD5Util;
import com.easydorm.easydorm.entity.BaseResponse;
import com.easydorm.easydorm.entity.User;
import com.easydorm.easydorm.entity.UserInfo;
import com.easydorm.easydorm.entity.UserToken;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.http.PostRequestInterface;
import com.easydorm.easydorm.launch.activity.LoginActivity;
import com.easydorm.easydorm.main.MainActivity;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
                    System.out.println("accessToken : "+accessToken.substring(130));
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
                .baseUrl(Constants.Url.baseImage)
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



}