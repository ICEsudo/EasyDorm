package com.easydorm.easydorm.entity;


import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.TimeUtil;

import java.io.Serializable;

public class ForumTopicBean implements Serializable {
    /**
     * tId : 2
     * uId : 1
     * tTitle : haha
     * tType : 1
     * tGoodcount : 0
     * tCreatetime : 2018-12-31T09:30:48.000+0000
     * tUpdatetime : 2018-12-31T09:30:48.000+0000
     * tContent : hello~ world!
     */

    private Integer tId;
    private Integer uId;

    private String tTitle;
    private int tType;
    private Integer tGoodcount;
    private String tCreatetime;
    private String tUpdatetime;
    private String tContent;

    private String summary;

    private UserInfoBean userInfo;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getTId() {
        return tId;
    }

    public void setTId(Integer tId) {
        this.tId = tId;
    }

    public Integer getUId() {
        return uId;
    }

    public void setUId(Integer uId) {
        this.uId = uId;
    }

    public String getTTitle() {
        return tTitle;
    }

    public void setTTitle(String tTitle) {
        this.tTitle = tTitle;
    }

    public Integer getTType() {
        return tType;
    }

    public void setTType(int tType) {
        this.tType = tType;
    }

    public Integer getTGoodcount() {
        return tGoodcount;
    }

    public void setTGoodcount(Integer tGoodcount) {
        this.tGoodcount = tGoodcount;
    }

    public String getTCreatetime() {
        return TimeUtil.stringToEasyString(tCreatetime);
    }

    public void setTCreatetime(String tCreatetime) {
        this.tCreatetime = tCreatetime;
    }

    public String getTUpdatetime() {
        return TimeUtil.stringToEasyString(tUpdatetime);
    }

    public void setTUpdatetime(String tUpdatetime) {
        this.tUpdatetime = tUpdatetime;
    }

    public String getTContent() {
        return tContent;
    }

    public void setTContent(String tContent) {
        this.tContent = tContent;
    }

    public String getSummary() {
        if(summary == null) summary = "";
        if(tContent != null) summary = tContent;
        if(summary.length() > 100) summary = summary.substring(0, 99) + "...";
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}