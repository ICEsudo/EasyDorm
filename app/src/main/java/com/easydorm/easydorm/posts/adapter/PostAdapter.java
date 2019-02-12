package com.easydorm.easydorm.posts.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.entity.ForumTopicBean;
import com.easydorm.easydorm.posts.viewHolder.PostViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends BaseQuickAdapter<ForumTopicBean, PostViewHolder> {

    public PostAdapter(int layoutResId, @Nullable List<ForumTopicBean> data) {
        super(layoutResId, data);
    }

    public PostAdapter(@Nullable List<ForumTopicBean> data) {
        super(data);
    }

    public PostAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(PostViewHolder helper, ForumTopicBean item) {

        helper.setText(R.id.post_user_nick_name, item.getNickName())
                .setText(R.id.post_info, item.getTUpdatetime())
                .setText(R.id.post_title, item.getTTitle())
                .setText(R.id.post_text, item.getSummary())
                .setText(R.id.post_agree_text, String.valueOf(item.getTGoodcount()))
                .setText(R.id.post_comment_text, String.valueOf(item.getCommentCount()))
                .addOnClickListener(R.id.post_user_avatar)
                .addOnClickListener(R.id.post_user_nick_name)
                .addOnClickListener(R.id.post_agree_icon)
                .addOnClickListener(R.id.post_comment_icon)
                .addOnClickListener(R.id.post_share)
                .addOnClickListener(R.id.post_more);

        Glide.with(mContext).load(Constants.Url.baseUrl + item.getPicture()).into((CircleImageView) helper.getView(R.id.post_user_avatar));

    }


}
