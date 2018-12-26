package com.easydorm.easydorm.Utils;

import android.content.Context;
import android.widget.Toast;

import com.easydorm.easydorm.EasyDormApp;

public class ToastUtil {

    public static void toast(String str) {
        Toast.makeText(EasyDormApp.getContext(), str, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(String str) {
        Toast.makeText(EasyDormApp.getContext(), str, Toast.LENGTH_LONG).show();
    }



}
