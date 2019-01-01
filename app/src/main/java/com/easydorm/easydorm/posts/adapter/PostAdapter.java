package com.easydorm.easydorm.posts.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.entity.ForumTopicBean;
import com.easydorm.easydorm.posts.viewHolder.PostViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends BaseQuickAdapter<ForumTopicBean, PostViewHolder> {

    public PostAdapter(int layoutResId, @Nullable List<ForumTopicBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(PostViewHolder helper, ForumTopicBean item) {

        helper.setText(R.id.post_user_nick_name, item.getNickName())
                .setText(R.id.post_info, item.getTUpdatetime())
                .setText(R.id.post_title, item.getTTitle())
                .setText(R.id.post_text, item.getTContent())
                .setText(R.id.post_agree_text, String.valueOf(item.getTGoodcount()));

        Glide.with(mContext).load(item.getPicture()).into((CircleImageView) helper.getView(R.id.post_user_avatar));

    }


}
