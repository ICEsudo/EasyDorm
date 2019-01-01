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
    /**
     * extend : {"forumTopic":[{"tId":2,"uId":1,"tTitle":"haha","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T09:30:48.000+0000","tUpdatetime":"2018-12-31T09:30:48.000+0000","tContent":"hello~ world!"},{"tId":3,"uId":3,"tTitle":"haha","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T09:31:49.000+0000","tUpdatetime":"2018-12-31T09:31:49.000+0000","tContent":"hello~ world!"},{"tId":4,"uId":12,"tTitle":"wakaka","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T10:28:42.000+0000","tUpdatetime":"2018-12-31T10:28:42.000+0000","tContent":"hello~ world!123456"},{"tId":5,"uId":10,"tTitle":"yohuohuo","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T10:30:45.000+0000","tUpdatetime":"2018-12-31T10:30:45.000+0000","tContent":"this is test page~"},{"tId":6,"uId":10,"tTitle":"yahaha","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T10:31:21.000+0000","tUpdatetime":"2018-12-31T10:31:21.000+0000","tContent":"this is test page~3"},{"tId":7,"uId":10,"tTitle":"yahaha1","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T10:31:26.000+0000","tUpdatetime":"2018-12-31T10:31:26.000+0000","tContent":"this is test page~4"},{"tId":8,"uId":10,"tTitle":"yahaha12","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T10:31:29.000+0000","tUpdatetime":"2018-12-31T10:31:29.000+0000","tContent":"this is test page~44"},{"tId":9,"uId":10,"tTitle":"yahaha5","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T10:31:31.000+0000","tUpdatetime":"2018-12-31T10:31:31.000+0000","tContent":"this is test page~44"},{"tId":10,"uId":10,"tTitle":"yahaha6","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T10:31:33.000+0000","tUpdatetime":"2018-12-31T10:31:33.000+0000","tContent":"this is test page~44"},{"tId":11,"uId":10,"tTitle":"yahaha7","tType":1,"tGoodcount":0,"tCreatetime":"2018-12-31T10:31:41.000+0000","tUpdatetime":"2018-12-31T10:31:41.000+0000","tContent":"this is test page~44"}]}
     */

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
        private List<ForumTopicBean> forumTopic;
        private HashMap<String, String> nickName;
        private HashMap<String, String> picture;


        public HashMap<String, String> getNickName() {
            return nickName;
        }

        public void setNickName(HashMap<String, String> nickName) {
            this.nickName = nickName;
        }

        public HashMap<String, String> getPicture() {
            return picture;
        }

        public void setPicture(HashMap<String, String> picture) {
            this.picture = picture;
        }

        public List<ForumTopicBean> getForumTopic() {
            return forumTopic;
        }

        public void setForumTopic(List<ForumTopicBean> forumTopic) {
            this.forumTopic = forumTopic;
        }

    }
}
