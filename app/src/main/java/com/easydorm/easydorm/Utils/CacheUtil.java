package com.easydorm.easydorm.Utils;

import android.content.Context;
import android.os.Looper;

import com.bumptech.glide.Glide;

public class CacheUtil {

    public static void clearGlideAllCache(Context context) {
        clearGlideDiskCache(context);
        clearGlideMemoryCache(context);
    }


    public static void clearGlideMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearGlideDiskCache(Context context) {
        try {
            if(Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
