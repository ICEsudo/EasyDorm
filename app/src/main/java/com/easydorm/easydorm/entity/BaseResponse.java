package com.easydorm.easydorm.entity;

public class BaseResponse {

    /**
     * code : 1
     * message : 处理成功!
     * extend : {}
     */

    private int code;
    private String message;
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

    }
}
