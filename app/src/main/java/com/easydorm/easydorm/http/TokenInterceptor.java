package com.easydorm.easydorm.http;

import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.entity.UserToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();

        if(responseBody != null) {
            try {
                JSONObject jsonObject = new JSONObject(new String(responseBody.bytes()));
                int code = jsonObject.optInt("code", 2);
                if(code == 3) {
                    UserToken userToken = EasyDormApp.getUser().getUserToken();
                    userToken.refreshToken();
                    request.newBuilder().addHeader("access_token", userToken.getAccessToken());
                    response = chain.proceed(request);
                } else {
                    response = response.newBuilder()
                            .body(ResponseBody.create(null, responseBody.bytes()))
                            .build();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
