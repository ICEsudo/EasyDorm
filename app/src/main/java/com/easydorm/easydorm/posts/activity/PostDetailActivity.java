package com.easydorm.easydorm.posts.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.HttpUtil;
import com.easydorm.easydorm.entity.BaseResponse;
import com.easydorm.easydorm.entity.Comment;
import com.easydorm.easydorm.entity.ExtendBean;
import com.easydorm.easydorm.entity.ForumBackBean;
import com.easydorm.easydorm.entity.ForumMultiBackBean;
import com.easydorm.easydorm.entity.ForumSecondBackBean;
import com.easydorm.easydorm.entity.ForumTopicBean;
import com.easydorm.easydorm.entity.SimpleComment;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.posts.adapter.CommentAdapter;
import com.easydorm.easydorm.userinfo.UserInfoActivity;
import com.easydorm.easydorm.view.InputDialogFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostDetailActivity extends FragmentActivity {

    Toolbar toolbar;
    TextView textView;
    ImageView toolbarIcon;

    CircleImageView userAvatar;
    TextView nickNameText, postInfoText, postTitleText, postText, agreeCountText, commentCountText;
    @BindView(R.id.post_detail_comment_recycler_view) RecyclerView commentRecyclerView;
    InputDialogFragment inputDialogFragment;

    private ArrayList<Comment> commentArrayList;
    private CommentAdapter commentAdapter;


    private ForumTopicBean post;

    private View headerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);

        Intent intent = this.getIntent();
        post = (ForumTopicBean) intent.getSerializableExtra("post");

        //init comments
        commentArrayList = new ArrayList<>();
        commentAdapter = new CommentAdapter(R.layout.item_comment, commentArrayList);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        commentRecyclerView.setAdapter(commentAdapter);
        commentAdapter.bindToRecyclerView(commentRecyclerView);
//        commentAdapter.setEmptyView(R.layout.empty_view_comment);

        initView();
        initListener();
        initData();


    }

    private void initData() {

        load();
        //init post
        Glide.with(this).load(Constants.Url.baseUrl + post.getPicture()).into(userAvatar);
        nickNameText.setText(post.getNickName());
        postInfoText.setText(post.getTCreatetime());
        postTitleText.setText(post.getTTitle());
        postText.setText(post.getTContent());
        agreeCountText.setText(String.valueOf(post.getTGoodcount()));



    }



    private void initView() {
        headerView = LayoutInflater.from(this).inflate(R.layout.post_detail_header, null);

        userAvatar = headerView.findViewById(R.id.post_detail_user_avatar);
        nickNameText = headerView.findViewById(R.id.post_detail_user_nick_name);
        postInfoText = headerView.findViewById(R.id.post_detail_info);
        postTitleText = headerView.findViewById(R.id.post_detail_title);
        postText = headerView.findViewById(R.id.post_detail_text);
        agreeCountText = headerView.findViewById(R.id.post_agree_text);
        commentCountText = headerView.findViewById(R.id.post_comment_text);
        commentAdapter.addHeaderView(headerView);


        toolbar = findViewById(R.id.toolbar_post_detail).findViewById(R.id.toolbar_back);
        textView = toolbar.findViewById(R.id.toolbar_back_text_title);
        textView.setText("帖子详情");
        textView = toolbar.findViewById(R.id.toolbar_back_text_left);
        toolbarIcon = toolbar.findViewById(R.id.toolbar_back_icon);

        inputDialogFragment = new InputDialogFragment();
        inputDialogFragment.settId(post.getTId());
        getSupportFragmentManager().beginTransaction().replace(R.id.post_detail_dialog_input, inputDialogFragment).commit();


    }

    private void initListener() {
        View.OnClickListener finishListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        textView.setOnClickListener(finishListener);
        toolbarIcon.setOnClickListener(finishListener);
        View.OnClickListener userInfoOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetailActivity.this, UserInfoActivity.class);
                intent.putExtra("uId", post.getUId());
                startActivity(intent);
            }
        };
        userAvatar.setOnClickListener(userInfoOnClickListener);
        nickNameText.setOnClickListener(userInfoOnClickListener);
        findViewById(R.id.post_detail_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialogFragment.clearFocus();
            }
        });
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialogFragment.clearFocus();
            }
        });
        commentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(inputDialogFragment.hasFocus()) {
                    inputDialogFragment.clearFocus();
                } else {
                    Comment comment = commentArrayList.get(position);
                    inputDialogFragment.getFocus(comment);
                }
            }
        });
        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.comment_user_avatar:
                    case R.id.comment_user_nick_name:
                        Intent intent = new Intent(PostDetailActivity.this, UserInfoActivity.class);
                        intent.putExtra("uId", commentArrayList.get(position).getForumBack().getUId());
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    public void load() {
        GetRequestInterface getRequestInterface = HttpUtil.getGetRequestInterface();
        Call<BaseResponse> call = getRequestInterface.getTopic(EasyDormApp.getUser().getUserToken().getAccessToken(), post.getTId());

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                if(baseResponse != null && baseResponse.getExtend() != null) {
                    ExtendBean extendBean = baseResponse.getExtend();
                    if(extendBean.getForumTopic() != null) {
                        post = extendBean.getForumTopic();
                        Glide.with(PostDetailActivity.this).load(Constants.Url.baseUrl + post.getPicture()).into(userAvatar);
                        nickNameText.setText(post.getNickName());
                        postInfoText.setText(post.getTCreatetime());
                        postTitleText.setText(post.getTTitle());
                        postText.setText(post.getTContent());
                        agreeCountText.setText(String.valueOf(post.getTGoodcount()));
                        commentCountText.setText(String.valueOf(post.getCommentCount()));
                       // formatCommentList(extendBean.getForumBack(), extendBean.getForumSecondBack(), extendBean.getForumMultiBack());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }


    private void formatCommentList(List<ForumBackBean> forumBack, List<ForumSecondBackBean> forumSecondBack,
                                   List<ForumMultiBackBean> forumMultiBack) {
        if(commentArrayList == null) commentArrayList = new ArrayList<>(forumBack.size());
        commentArrayList.clear();
        for(ForumBackBean forumBackBean: forumBack) {
            Comment comment = new Comment();
            comment.setForumBack(forumBackBean);
            if(forumBackBean.isBHasback()) {
                for(ForumSecondBackBean forumSecondBackBean: forumSecondBack) {
                    if(forumSecondBackBean.getPId() == forumBackBean.getBId()) {
                        comment.addSimpleComment(forumBackBean.getUId(),
                                forumBackBean.getNickName(),
                                forumSecondBackBean.getUId(),
                                forumSecondBackBean.getNickName(),
                                forumSecondBackBean.getSContent(),
                                true);
                        if(forumSecondBackBean.isSHasback()) {
                            for(ForumMultiBackBean forumMultiBackBean: forumMultiBack) {
                                if(forumMultiBackBean.getPId() == forumSecondBackBean.getSId() && forumMultiBackBean.getpType() == -1) {
                                    comment.addSimpleComment(forumSecondBackBean.getUId(),
                                            forumSecondBackBean.getNickName(),
                                            forumMultiBackBean.getUId(),
                                            forumMultiBackBean.getNickName(),
                                            forumMultiBackBean.getMContent(),
                                            false);
                                }
                            }
                        }
                    }
                }
            }
            commentArrayList.add(comment);
        }
        if(commentArrayList.size() == 0) {
            View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view_comment, null);
            commentAdapter.addHeaderView(emptyView);
        } else {
            Collections.reverse(commentArrayList);
            commentAdapter.notifyDataSetChanged();
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
