package com.bolue.scan.mvp.entity;

/**
 * Created by cty on 2017/7/5.
 */

public class LoginRequestEntity {
    String userName;
    String pw;

    public LoginRequestEntity(String userName, String pw) {
        this.userName = userName;
        this.pw = pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPw() {
        return pw;
    }

    public String getUserName() {
        return userName;
    }
}
