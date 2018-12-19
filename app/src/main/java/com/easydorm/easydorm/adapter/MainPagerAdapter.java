package com.easydorm.easydorm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
