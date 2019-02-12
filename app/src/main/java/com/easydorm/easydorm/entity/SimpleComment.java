package com.easydorm.easydorm.entity;

public class SimpleComment {
    private int uId;
    private int bUId;
    private String userName, bUserName;
    private String content;
    private boolean isSecond;

    public SimpleComment() {}

    public SimpleComment(int uId, String userName, int bUId, String bUserName, String content, boolean isSecond) {
        this.uId = uId;
        this.userName = userName;
        this.bUId = bUId;
        this.bUserName = bUserName;
        this.content = content;
        this.isSecond = isSecond;
    }

    public int getuId() {
        return uId;
    }

    public int getbUId() {
        return bUId;
    }

    public String getUserName() {
        return userName;
    }

    public String getbUserName() {
        return bUserName;
    }

    public String getContent() {
        return content;
    }

    public boolean isSecond() {
        return isSecond;
    }
}

