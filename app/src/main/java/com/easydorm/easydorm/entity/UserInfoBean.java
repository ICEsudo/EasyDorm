package com.easydorm.easydorm.entity;

import java.io.Serializable;

public class UserInfoBean implements Serializable {
    /**
     * userid : 5
     * nickname : lqf456
     * picture : /static/psb.jpg
     * phonenumber : 123456
     * email : 123@qq.com
     * dormaddress : 行3
     * introduction : alouha
     * follow : null
     * fans : null
     * phonevisiable : false
     * emailvisiable : false
     * dormaddressvisiable : false
     */

    private int userid;
    private String nickname;
    private String picture;
    private String phonenumber;
    private String email;
    private String dormaddress;
    private String introduction;
    private Object follow;
    private Object fans;
    private boolean phonevisiable;
    private boolean emailvisiable;
    private boolean dormaddressvisiable;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDormaddress() {
        return dormaddress;
    }

    public void setDormaddress(String dormaddress) {
        this.dormaddress = dormaddress;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Object getFollow() {
        return follow;
    }

    public void setFollow(Object follow) {
        this.follow = follow;
    }

    public Object getFans() {
        return fans;
    }

    public void setFans(Object fans) {
        this.fans = fans;
    }

    public boolean isPhonevisiable() {
        return phonevisiable;
    }

    public void setPhonevisiable(boolean phonevisiable) {
        this.phonevisiable = phonevisiable;
    }

    public boolean isEmailvisiable() {
        return emailvisiable;
    }

    public void setEmailvisiable(boolean emailvisiable) {
        this.emailvisiable = emailvisiable;
    }

    public boolean isDormaddressvisiable() {
        return dormaddressvisiable;
    }

    public void setDormaddressvisiable(boolean dormaddressvisiable) {
        this.dormaddressvisiable = dormaddressvisiable;
    }
}
