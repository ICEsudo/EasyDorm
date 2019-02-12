package com.easydorm.easydorm.posts.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.entity.SimpleComment;
import com.easydorm.easydorm.posts.viewHolder.SimpleCommentViewHolder;

import java.util.List;

import androidx.annotation.Nullable;

public class SimpleCommentAdapter extends BaseQuickAdapter<SimpleComment, SimpleCommentViewHolder> {

    public SimpleCommentAdapter(int layoutResId, @Nullable List<SimpleComment> data) {
        super(layoutResId, data);
    }

    public SimpleCommentAdapter(@Nullable List<SimpleComment> data) {
        super(data);
    }

    public SimpleCommentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(SimpleCommentViewHolder helper, SimpleComment item) {
        helper.setText(R.id.item_multi_comment_user_name, item.getUserName())
                .setText(R.id.item_multi_comment_back_user_name, item.getbUserName())
                .setText(R.id.item_multi_comment_content, item.getContent());
    }

}
