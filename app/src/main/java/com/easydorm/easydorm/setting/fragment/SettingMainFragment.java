package com.easydorm.easydorm.setting.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import androidx.preference.Preference;

import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.launch.activity.LoginActivity;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.setting.SettingActivity;


public class SettingMainFragment extends PreferenceFragmentCompat {

    Preference logout;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName("baseSetting");
        addPreferencesFromResource(R.xml.preferences);

        init();
        setListener();
    }


    private void init() {
        logout = findPreference("logout");
    }


    private void setListener() {
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ActivityCollector.finishToLoginActivity();
                return true;
            }
        });
    }






}
