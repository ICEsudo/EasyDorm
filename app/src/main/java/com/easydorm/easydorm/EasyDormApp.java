package com.easydorm.easydorm;

import android.app.Application;
import android.content.Context;

import com.easydorm.easydorm.entity.User;


public class EasyDormApp extends Application {

    private static Context context;
    private static User user;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        EasyDormApp.user = user;
    }
}
