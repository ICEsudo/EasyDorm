package com.easydorm.easydorm.entity;

import java.util.List;

public class ExtendBean {

    //getTopics
    private List<ForumTopicBean> forumTopics;
    private Integer pages;

    //getUserInfo
    private UserInfoBean userInfo;

    //login
    private int uId;

    //getTopic
    private ForumTopicBean forumTopic;


//    private List<ForumBackBean> forumBack;
//    private List<ForumSecondBackBean> forumSecondBack;
//    private List<ForumMultiBackBean> forumMultiBack;
//
//
//










    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<ForumTopicBean> getForumTopics() {
        return forumTopics;
    }

    public void setForumTopics(List<ForumTopicBean> forumTopics) {
        this.forumTopics = forumTopics;
    }

    public ForumTopicBean getForumTopic() {
        return forumTopic;
    }

    public void setForumTopic(ForumTopicBean forumTopic) {
        this.forumTopic = forumTopic;
    }

//    public List<ForumSecondBackBean> getForumSecondBack() {
//        return forumSecondBack;
//    }
//
//    public void setForumSecondBack(List<ForumSecondBackBean> forumSecondBack) {
//        this.forumSecondBack = forumSecondBack;
//    }
//
//    public List<ForumMultiBackBean> getForumMultiBack() {
//        return forumMultiBack;
//    }
//
//    public void setForumMultiBack(List<ForumMultiBackBean> forumMultiBack) {
//        this.forumMultiBack = forumMultiBack;
//    }
//
//    public List<ForumBackBean> getForumBack() {
//        return forumBack;
//    }
//
//    public void setForumBack(List<ForumBackBean> forumBack) {
//        this.forumBack = forumBack;
//    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }
    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

}
