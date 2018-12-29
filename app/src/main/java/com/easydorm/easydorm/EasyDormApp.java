package com.easydorm.easydorm;

import android.app.Application;
import android.content.Context;

import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.entity.User;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;


public class EasyDormApp extends Application {

    private static Context context;
    private static User user;


    private static String curUserId;

    @Override
    public void onCreate() {
        super.onCreate();


        //for OOM check
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        context = getApplicationContext();

        //for logger
        Logger.addLogAdapter(new AndroidLogAdapter());

    }

    public static Context getContext() {
        return context;
    }

    public static User getUser() {
        return user;
    }

    public static User setUser(User user) {
        EasyDormApp.user = user;
        return user;
    }

    public static String getCurUserId() {
        curUserId = SPUtil.getAppConstants().getString("curUserId", "");
        return curUserId;
    }

    public static void setCurUserId(String curUserId) {
        EasyDormApp.curUserId = curUserId;
        SPUtil.getAppConstants().edit().putString("curUserId", curUserId).apply();
    }


}
