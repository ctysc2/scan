package com.bolue.scan.mvp.entity.base;

/**
 * Created by cty on 2017/6/6.
 */

public class BaseQuestionEntity {
    private int id;
    private String title;
    private int anonymous;
    private String uuid;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    class User{
        private String nick_name;
        private String photo;

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getNick_name() {
            return nick_name;
        }

        public String getPhoto() {
            return photo;
        }
    }
}