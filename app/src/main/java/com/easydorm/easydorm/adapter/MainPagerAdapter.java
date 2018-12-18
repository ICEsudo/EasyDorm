package com.easydorm.easydorm.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> listViews;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setListViews(List<Fragment> listViews) {
        this.listViews = listViews;
    }


    @Override
    public Fragment getItem(int i) {
        return listViews.get(i);
    }

    @Override
    public int getCount() {
        return listViews.size();
    }



}
