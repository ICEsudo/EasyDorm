package com.easydorm.easydorm.posts.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.easydorm.easydorm.entity.Comment;
import com.easydorm.easydorm.posts.viewHolder.CommentViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends BaseQuickAdapter<Comment, CommentViewHolder> {


    public CommentAdapter(int layoutResId, @Nullable List<Comment> data) {
        super(layoutResId, data);
    }

    public CommentAdapter(@Nullable List<Comment> data) {
        super(data);
    }

    public CommentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(CommentViewHolder helper, Comment item) {

    }

}
