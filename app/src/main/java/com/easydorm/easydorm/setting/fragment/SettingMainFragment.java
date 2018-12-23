package com.easydorm.easydorm.setting.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easydorm.easydorm.LoginActivity;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.setting.SettingActivity;


public class SettingMainFragment extends PreferenceFragment {

    Preference logout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        init();
        setListener();

    }


    public void init() {
        logout = findPreference("logout");
    }


    public void setListener() {
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                ((SettingActivity) getActivity()).finishAllActivity();
                SPUtil.getUserInfo().edit().putBoolean("isLogined", false).apply();
                return true;
            }
        });
    }






}
