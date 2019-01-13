package com.easydorm.easydorm.posts.fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.entity.ForumTopicBean;
import com.easydorm.easydorm.posts.activity.PostDetailActivity;
import com.easydorm.easydorm.posts.adapter.PostAdapter;

import java.util.ArrayList;

public class AttentionFragment extends Fragment {

    @BindView(R.id.post_attention_recycler_view) RecyclerView postRecommendRecyclerView;
    @BindView(R.id.fragment_attention_framelayout) FrameLayout frameLayout;

    private PostAdapter postAdapter;
    private ArrayList<ForumTopicBean> postArrayList;


    public AttentionFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attention, container, false);
        ButterKnife.bind(this, view);

        postArrayList = new ArrayList<>();
        postAdapter = new PostAdapter(R.layout.item_post_card, postArrayList);

        postRecommendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        postRecommendRecyclerView.setAdapter(postAdapter);

        postAdapter.bindToRecyclerView(postRecommendRecyclerView);
        postAdapter.setEmptyView(R.layout.empty_view_recommend);

        initListener();

        return view;
    }

    private void initListener() {
        postAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post", postArrayList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
