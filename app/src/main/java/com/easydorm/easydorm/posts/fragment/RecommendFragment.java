package com.easydorm.easydorm.posts.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.entity.Post;
import com.easydorm.easydorm.entity.UserInfo;
import com.easydorm.easydorm.posts.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RecommendFragment extends Fragment {

    @BindView(R.id.post_recommend_recycler_view)
    RecyclerView postRecommendRecyclerView;

    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);

        postArrayList = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Post post = new Post();
            UserInfo userInfo = new UserInfo(0);
            userInfo.setNickName("用户"+i);
            post.setPosterInfo(userInfo);
            post.setAgreeNumber(12);
            post.setCommentNumber(0);
            post.setViewNumber(123);
            post.setPostText("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
            postArrayList.add(post);
        }

        postAdapter = new PostAdapter(R.layout.item_post_card, postArrayList);

        postRecommendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        postRecommendRecyclerView.setAdapter(postAdapter);

        return view;
    }


}
