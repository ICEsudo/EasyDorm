package com.easydorm.easydorm.posts.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.entity.Comment;
import com.easydorm.easydorm.posts.viewHolder.CommentViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

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
        Glide.with(mContext).load(Constants.Url.baseUrl + item.getForumBack().getPicture())
                .into((CircleImageView) helper.getView(R.id.comment_user_avatar));
        helper.setText(R.id.comment_user_nick_name, item.getForumBack().getNickName())
                .setText(R.id.comment_info, item.getForumBack().getBCreatetime())
                .setText(R.id.comment_text, item.getForumBack().getBContent());
    }

}
