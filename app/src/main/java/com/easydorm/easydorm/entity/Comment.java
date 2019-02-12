package com.easydorm.easydorm.entity;

import java.util.ArrayList;

public class Comment {

    private ForumBackBean forumBack;

    private ArrayList<SimpleComment> simpleCommentList;


    public ForumBackBean getForumBack() {
        return forumBack;
    }

    public void setForumBack(ForumBackBean forumBack) {
        this.forumBack = forumBack;
    }

    public void addSimpleComment(int uId, String userName, int bUId, String bUserName, String content, boolean isSecond) {
        if(simpleCommentList == null) simpleCommentList = new ArrayList<>();
        simpleCommentList.add(new SimpleComment(uId, userName, bUId, bUserName, content, isSecond));
    }

    public ArrayList<SimpleComment> getSimpleCommentList() {
        return simpleCommentList;
    }
}
