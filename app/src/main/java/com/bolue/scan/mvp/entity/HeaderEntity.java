package com.bolue.scan.mvp.entity;

/**
 * Created by cty on 2017/6/6.
 */

public class HeaderEntity {
    private String openid;
    private String code;

    public HeaderEntity(String openid, String code) {
        this.openid = openid;
        this.code = code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCode() {
        return code;
    }

    public String getOpenid() {
        return openid;
    }
}
