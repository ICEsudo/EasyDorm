package com.easydorm.easydorm.http;


import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.NetWorkUtil;
import com.easydorm.easydorm.Utils.ToastUtil;
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
        NetWorkUtil.checkNetWork();
        Request request = chain.request();

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();

        if(responseBody != null) {
            byte[] bytes = responseBody.bytes();
            try {
                JSONObject jsonObject = new JSONObject(new String(bytes));
                int code = jsonObject.optInt("code");
                if(code == 3) { //access_token expired
                    UserToken userToken = EasyDormApp.getUser().getUserToken();
                    userToken.refreshToken();
                    request = request.newBuilder()
                            .removeHeader("access_token")
                            .addHeader("access_token", userToken.getAccessToken())
                            .build();
                    response = chain.proceed(request);
                    responseBody = response.body();
                    if(responseBody != null) {
                        bytes = responseBody.bytes();
                        jsonObject = new JSONObject(new String(bytes));
                        code = jsonObject.optInt("code");
                        if(code == 3) { //refresh_token expired
                            ActivityCollector.getTopActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.toast("登录信息已过期,请重新登录");
                                }
                            });
                            ActivityCollector.finishToLoginActivity();
                        }
                        response = response.newBuilder()
                                .body(ResponseBody.create(null, bytes))
                                .build();
                    }

                } else {
                    response = response.newBuilder()
                            .body(ResponseBody.create(null, bytes))
                            .build();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
