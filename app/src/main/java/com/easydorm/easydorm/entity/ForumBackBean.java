package com.easydorm.easydorm.entity;

import com.easydorm.easydorm.Utils.TimeUtil;

public class ForumBackBean {
    /**
     * bId : 7
     * pId : 77
     * uId : 5
     * bCreatetime : 2019-01-21 15:59:20
     * bHasback : false
     * bContent : hahaha
     * nickName : lqfhahaha1
     * picture : /static/psb.jpg
     */

    private int bId;
    private int pId;
    private int uId;
    private String bCreatetime;
    private boolean bHasback;
    private String bContent;
    private String nickName;
    private String picture;

    public int getBId() {
        return bId;
    }

    public void setBId(int bId) {
        this.bId = bId;
    }

    public int getPId() {
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getBCreatetime() {
        return TimeUtil.stringToEasyString(bCreatetime);
//        return bCreatetime;
    }

    public void setBCreatetime(String bCreatetime) {
        this.bCreatetime = bCreatetime;
    }

    public boolean isBHasback() {
        return bHasback;
    }

    public void setBHasback(boolean bHasback) {
        this.bHasback = bHasback;
    }

    public String getBContent() {
        return bContent;
    }

    public void setBContent(String bContent) {
        this.bContent = bContent;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
