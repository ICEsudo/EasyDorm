package com.easydorm.easydorm.http;

import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.entity.User;
import com.easydorm.easydorm.entity.UserToken;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        UserToken userToken = EasyDormApp.getUser().getUserToken();

        userToken.refreshToken();

        return response.request().newBuilder()
                .addHeader("access_token", userToken.getAccessToken())
                .build();
    }
}
