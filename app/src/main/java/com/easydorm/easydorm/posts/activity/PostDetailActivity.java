package com.easydorm.easydorm.posts.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.entity.Comment;
import com.easydorm.easydorm.entity.ForumTopicBean;
import com.easydorm.easydorm.posts.adapter.CommentAdapter;
import com.easydorm.easydorm.userinfo.UserInfoActivity;

import java.util.ArrayList;

public class PostDetailActivity extends BaseActivity {

    Toolbar toolbar;
    TextView textView;
    ImageView toolbarIcon;

    CircleImageView userAvatar;
    TextView nickNameText, postInfoText, postTitleText, postText, agreeCountText;
    @BindView(R.id.post_detail_comment_recycler_view)
    RecyclerView commentRecyclerView;

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

        initView();
        initListener();
        initData();


    }

    private void initData() {

        //init post
        Glide.with(this).load(Constants.Url.baseUrl + post.getUserInfo().getPicture()).into(userAvatar);
        nickNameText.setText(post.getUserInfo().getNickname());
        postInfoText.setText(post.getTCreatetime());
        postTitleText.setText(post.getTTitle());
        postText.setText(post.getTContent());
        agreeCountText.setText(String.valueOf(post.getTGoodcount()));


        //init comments
        commentArrayList = new ArrayList<>();
        commentAdapter = new CommentAdapter(R.layout.item_comment, commentArrayList);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        commentRecyclerView.setAdapter(commentAdapter);
        commentAdapter.bindToRecyclerView(commentRecyclerView);
//        commentAdapter.setEmptyView(R.layout.empty_view_comment);
        commentAdapter.addHeaderView(headerView);
        if(commentArrayList.size() == 0) {
            View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view_comment, null);
            commentAdapter.addHeaderView(emptyView);
        }

    }



    private void initView() {
        headerView = LayoutInflater.from(this).inflate(R.layout.post_detail_header, null);

        userAvatar = headerView.findViewById(R.id.post_detail_user_avatar);
        nickNameText = headerView.findViewById(R.id.post_detail_user_nick_name);
        postInfoText = headerView.findViewById(R.id.post_detail_info);
        postTitleText = headerView.findViewById(R.id.post_detail_title);
        postText = headerView.findViewById(R.id.post_detail_text);
        agreeCountText = headerView.findViewById(R.id.post_agree_text);


        toolbar = findViewById(R.id.toolbar_post_detail).findViewById(R.id.toolbar_back);
        textView = toolbar.findViewById(R.id.toolbar_back_text_title);
        textView.setText("帖子详情");
        textView = toolbar.findViewById(R.id.toolbar_back_text_left);
        toolbarIcon = toolbar.findViewById(R.id.toolbar_back_icon);
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
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
