package com.bolue.scan.mvp.entity.base;

/**
 * Created by cty on 2017/6/6.
 */

public class BaseEntity {
    private String err;
    private User user;

    public void setErr(String err) {
        this.err = err;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getErr() {
        return err;
    }

    public User getUser() {
        return user;
    }


    
    public  static class User{
        private boolean is_logined;

        public void setLogined(boolean logined) {
            is_logined = logined;
        }

        public boolean isLogined() {
            return is_logined;
        }
    }
}
