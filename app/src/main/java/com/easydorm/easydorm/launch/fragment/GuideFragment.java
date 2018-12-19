package com.easydorm.easydorm.launch.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.easydorm.easydorm.R;

import androidx.fragment.app.Fragment;

public class GuideFragment extends Fragment {

    private int resId;

    public GuideFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        ImageView imageView = view.findViewById(R.id.guide_image);
        imageView.setImageResource(resId);

        return view;
    }


    public void setImageView(int resId) {
        this.resId = resId;
    }

}
