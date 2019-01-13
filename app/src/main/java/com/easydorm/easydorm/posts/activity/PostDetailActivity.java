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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.entity.Comment;
import com.easydorm.easydorm.entity.ForumTopicBean;
import com.easydorm.easydorm.posts.adapter.CommentAdapter;

import java.util.ArrayList;

public class PostDetailActivity extends BaseActivity {

    Toolbar toolbar;
    TextView textView;
    ImageView toolbarIcon;

    @BindView(R.id.post_detail_user_avatar) CircleImageView userAvatar;
    @BindView(R.id.post_detail_user_nick_name) TextView nickNameText;
    @BindView(R.id.post_detail_info) TextView postInfoText;
    @BindView(R.id.post_detail_title) TextView postTitleText;
    @BindView(R.id.post_detail_text) TextView postText;
    @BindView(R.id.post_detail_comment_recycler_view)
    RecyclerView commentRecyclerView;

    private ArrayList<Comment> commentArrayList;
    private CommentAdapter commentAdapter;

    private ForumTopicBean post;


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
        Glide.with(this).load(post.getPicture()).into(userAvatar);
        nickNameText.setText(post.getNickName());
        postInfoText.setText(post.getTCreatetime());
        postTitleText.setText(post.getTTitle());
        postText.setText(post.getTContent());


        //init comments
        commentArrayList = new ArrayList<>();
        commentAdapter = new CommentAdapter(R.layout.item_comment, commentArrayList);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        commentRecyclerView.setAdapter(commentAdapter);
        commentAdapter.bindToRecyclerView(commentRecyclerView);
        commentAdapter.setEmptyView(R.layout.empty_view_comment);

    }



    private void initView() {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
