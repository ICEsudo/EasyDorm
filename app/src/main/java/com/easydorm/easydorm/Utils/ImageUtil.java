package com.easydorm.easydorm.Utils;

import android.Manifest;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.easydorm.easydorm.EasyDormApp;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.concurrent.ExecutionException;


public class ImageUtil {


    public static void getAvatar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FutureTarget<File> target = Glide.with(EasyDormApp.getContext())
                            .asFile().load(EasyDormApp.getUser().getUserInfo().getAvatarUrl()).submit();
                    final File avatar = target.get();

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
