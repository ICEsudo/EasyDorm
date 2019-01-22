package com.easydorm.easydorm.entity;

import java.util.List;

public class Comment {

    private ForumBackBean forumBack;
    private List<ForumSecondBackBean> forumSecondBack;
    private List<ForumMultiBackBean> forumMultiBack;




    public ForumBackBean getForumBack() {
        return forumBack;
    }

    public void setForumBack(ForumBackBean forumBack) {
        this.forumBack = forumBack;
    }

    public List<ForumSecondBackBean> getForumSecondBack() {
        return forumSecondBack;
    }

    public void setForumSecondBack(List<ForumSecondBackBean> forumSecondBack) {
        this.forumSecondBack = forumSecondBack;
    }

    public List<ForumMultiBackBean> getForumMultiBack() {
        return forumMultiBack;
    }

    public void setForumMultiBack(List<ForumMultiBackBean> forumMultiBack) {
        this.forumMultiBack = forumMultiBack;
    }

}
