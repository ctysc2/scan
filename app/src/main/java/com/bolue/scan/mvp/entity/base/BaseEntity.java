package com.bolue.scan.mvp.entity.base;

/**
 * Created by cty on 2017/6/6.
 */

public class BaseEntity {
    private Error err;
    private User user;

    public void setErr(Error err) {
        this.err = err;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Error getErr() {
        return err;
    }

    public User getUser() {
        return user;
    }

    class Error{

    }
    
    class User{
        private boolean isLogined;

        public void setLogined(boolean logined) {
            isLogined = logined;
        }

        public boolean isLogined() {
            return isLogined;
        }
    }
}
