package com.easydorm.easydorm;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();


    }

    private void initData() {
        views = new ArrayList<>();
        LayoutInflater layoutInflater = getLayoutInflater().from(this);
        views.add(layoutInflater.inflate(R.layout.guide_views, null));

    }


    private void initView() {
        viewPager = findViewById(R.id.view_pager_guide);
    }





}
