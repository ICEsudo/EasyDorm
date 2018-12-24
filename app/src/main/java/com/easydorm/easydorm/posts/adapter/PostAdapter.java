package com.easydorm.easydorm.posts.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.entity.Post;
import com.easydorm.easydorm.posts.viewHolder.PostViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends BaseQuickAdapter<Post, PostViewHolder> {

    public PostAdapter(int layoutResId, @Nullable List<Post> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(PostViewHolder helper, Post item) {
        helper.setText(R.id.post_user_nick_name, item.getPosterInfo().getNickName())
                .setText(R.id.post_text, item.getPostText());
    }


}
