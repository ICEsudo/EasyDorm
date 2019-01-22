package com.easydorm.easydorm.entity;

public class ForumSecondBackBean {
    /**
     * sId : 1
     * tId : 77
     * pId : 1
     * uId : 5
     * sCreatetime : 2019-01-21 14:47:37
     * sHasback : false
     * sContent : hahaha1
     * nickName : lqfhahaha1
     */

    private int sId;
    private int tId;
    private int pId;
    private int uId;
    private String sCreatetime;
    private boolean sHasback;
    private String sContent;
    private String nickName;

    public int getSId() {
        return sId;
    }

    public void setSId(int sId) {
        this.sId = sId;
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

    public String getSCreatetime() {
        return sCreatetime;
    }

    public void setSCreatetime(String sCreatetime) {
        this.sCreatetime = sCreatetime;
    }

    public boolean isSHasback() {
        return sHasback;
    }

    public void setSHasback(boolean sHasback) {
        this.sHasback = sHasback;
    }

    public String getSContent() {
        return sContent;
    }

    public void setSContent(String sContent) {
        this.sContent = sContent;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
