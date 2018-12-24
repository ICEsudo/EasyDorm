package com.easydorm.easydorm.entity;

import java.util.Date;

public class Post {

    private int id;

    private int agreeNumber;
    private int commentNumber;
    private int viewNumber;

    private String postText;

    private UserInfo posterInfo;

    private Date postTime;




    public Post() {

    }

    public UserInfo getPosterInfo() {
        return posterInfo;
    }

    public void setPosterInfo(UserInfo posterInfo) {
        this.posterInfo = posterInfo;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public int getAgreeNumber() {
        return agreeNumber;
    }

    public void setAgreeNumber(int agreeNumber) {
        this.agreeNumber = agreeNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }




}
