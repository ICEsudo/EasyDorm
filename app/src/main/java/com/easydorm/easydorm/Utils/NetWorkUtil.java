package com.easydorm.easydorm.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.easydorm.easydorm.EasyDormApp;

public class NetWorkUtil {


    public static void checkNetWork() {
        if(!isNetWorkAvailable()) {
            ToastUtil.toast("网络不可用");
        }
    }

    public static boolean isNetWorkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) EasyDormApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }



}
