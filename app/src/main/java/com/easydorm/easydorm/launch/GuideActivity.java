package com.easydorm.easydorm.launch;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.easydorm.easydorm.R;
import com.easydorm.easydorm.launch.fragment.GuideFragment;
import com.easydorm.easydorm.main.MainActivity;
import com.github.paolorotolo.appintro.AppIntro;

public class GuideActivity extends AppIntro {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        sp = getSharedPreferences("AppConstants", MODE_PRIVATE);

        GuideFragment guideFragment1 = new GuideFragment();
        guideFragment1.setImageView(R.mipmap.photo_1);
        addSlide(guideFragment1);

        GuideFragment guideFragment2 = new GuideFragment();
        guideFragment2.setImageView(R.mipmap.photo_2);
        addSlide(guideFragment2);

        GuideFragment guideFragment3 = new GuideFragment();
        guideFragment3.setImageView(R.mipmap.photo_4);
        addSlide(guideFragment3);

        GuideFragment guideFragment4 = new GuideFragment();
        guideFragment4.setImageView(R.mipmap.photo_6);
        addSlide(guideFragment4);

        GuideFragment guideFragment5 = new GuideFragment();
        guideFragment5.setImageView(R.mipmap.photo_7);
        addSlide(guideFragment5);

        setSkipText("跳过");
        setDoneText("完成");

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        sp.edit().putBoolean("isFirstLaunch", true).apply();
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        sp.edit().putBoolean("isFirstLaunch", true).apply();
        super.onDonePressed(currentFragment);
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
    }
}
