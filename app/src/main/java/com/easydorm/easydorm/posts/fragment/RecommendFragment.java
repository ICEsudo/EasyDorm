package com.easydorm.easydorm.posts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.HttpUtil;
import com.easydorm.easydorm.Utils.ToastUtil;
import com.easydorm.easydorm.entity.BaseResponse;
import com.easydorm.easydorm.entity.ForumTopicBean;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.posts.activity.PostDetailActivity;
import com.easydorm.easydorm.posts.adapter.PostAdapter;
import com.easydorm.easydorm.userinfo.UserInfoActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecommendFragment extends Fragment {

    @BindView(R.id.post_recommend_recycler_view)
    RecyclerView postRecommendRecyclerView;
    @BindView(R.id.fragment_recommend_framelayout)
    FrameLayout frameLayout;

    private PostAdapter postAdapter;
    private ArrayList<ForumTopicBean> postArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
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
        postAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //TODO
                    case R.id.post_user_avatar:
                    case R.id.post_user_nick_name:
                        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                        intent.putExtra("uId", postArrayList.get(position).getUId());
                        startActivity(intent);
                        break;
                    case R.id.post_agree_icon:
                        ImageView imageView = view.findViewById(R.id.post_agree_icon);
                        ToastUtil.toast("点赞");
                        break;
                    case R.id.post_comment_icon:
                        ToastUtil.toast("评论没做");
                        break;
                    case R.id.post_more:
                        ToastUtil.toast("更多");
                        break;
                    case R.id.post_share:
                        ToastUtil.toast("分享不了");
                        break;
                }
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        loadPost();
    }


    private void loadPost() {

        GetRequestInterface getRequestInterface = HttpUtil.getGetRequestInterface();
        Call<BaseResponse> call = getRequestInterface.getTopics(EasyDormApp.getUser().getUserToken().getAccessToken());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                if(baseResponse != null) {
                    if(baseResponse.getCode() == 1) {
                        List<ForumTopicBean> newList = baseResponse.getExtend().getForumTopic();
                        postAdapter.replaceData(newList);
                    }
                } else {
                    ToastUtil.toast("服务器异常");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
//                ToastUtil.toast("请求失败");
            }
        });
    }


    private void agree(int pos) {

    }


}
