package com.easydorm.easydorm.entity;

public class ForumMultiBackBean {
    /**
     * mId : 1
     * tId : 77
     * pId : 1
     * uId : 5
     * mCreatetime : 2019-01-21 14:35:35
     * mHasback : false
     * mContent : hahaha
     * nickName : lqfhahaha1
     */

    private int mId;
    private int tId;
    private int pId;
    private int uId;
    private String mCreatetime;
    private boolean mHasback;
    private String mContent;
    private String nickName;

    public int getMId() {
        return mId;
    }

    public void setMId(int mId) {
        this.mId = mId;
    }

    public int getTId() {
        return tId;
    }

    public void setTId(int tId) {
        this.tId = tId;
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

    public String getMCreatetime() {
        return mCreatetime;
    }

    public void setMCreatetime(String mCreatetime) {
        this.mCreatetime = mCreatetime;
    }

    public boolean isMHasback() {
        return mHasback;
    }

    public void setMHasback(boolean mHasback) {
        this.mHasback = mHasback;
    }

    public String getMContent() {
        return mContent;
    }

    public void setMContent(String mContent) {
        this.mContent = mContent;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
