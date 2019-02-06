package com.easydorm.easydorm.Utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easydorm.easydorm.EasyDormApp;

public class ToastUtil {

    public static void toast(String str) {
        toastTime(str, Toast.LENGTH_SHORT);
    }

    public static void toastLong(String str) {
        toastTime(str, Toast.LENGTH_LONG);
    }

    public static void toastTime(String str, int time) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Toast.makeText(EasyDormApp.getContext(), str, time).show();
            } else {
                ActivityCollector.getTopActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EasyDormApp.getContext(), str, time).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
