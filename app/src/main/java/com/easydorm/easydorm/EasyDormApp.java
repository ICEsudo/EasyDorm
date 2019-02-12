package com.easydorm.easydorm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.entity.User;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;


public class EasyDormApp extends Application {

    private static WeakReference<Context> context;
    private static User user;

    private static WeakReference<Activity> currentActivity;

    private static String curUserId;


    @Override
    public void onCreate() {
        super.onCreate();


        //for OOM check
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        context = new WeakReference<>(getApplicationContext());

        //for logger
        Logger.addLogAdapter(new AndroidLogAdapter());

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                    layout.setPrimaryColorsId(R.color.colorPrimary, R.color.colorPrimary);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                currentActivity = new WeakReference<>(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });


    }


    public static Activity getCurrentActivity() {
        return currentActivity.get();
    }

    public static Context getContext() {
        return context.get();
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
