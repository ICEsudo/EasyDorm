package com.easydorm.easydorm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class BaseResponse {

    /**
     * code : 1
     * message : 处理成功!
     * extend : {}
     */

    private int code;
    private String message;


    @SerializedName("extend")
    private ExtendBean extend;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExtendBean getExtend() {
        return extend;
    }

    public void setExtend(ExtendBean extend) {
        this.extend = extend;
    }

    public static class ExtendBean {
        private List<ForumTopicBean> forumTopic;    //getTopics
        private UserInfoBean userInfo;              //getUserInfo
        private int uId;                            //login

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

        public List<ForumTopicBean> getForumTopic() {
            return forumTopic;
        }

        public void setForumTopic(List<ForumTopicBean> forumTopic) {
            this.forumTopic = forumTopic;
        }


    }
}
