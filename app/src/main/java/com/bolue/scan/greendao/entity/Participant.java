package com.bolue.scan.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cty on 2017/6/22.
 */
@Entity
public class Participant {
    @Id
    private Long key;

    private String name;
    private int lessonId;
    private int userId;
    private String checkCode;
    private String account;
    private int status;
    @Generated(hash = 2064887704)
    public Participant(Long key, String name, int lessonId, int userId,
            String checkCode, String account, int status) {
        this.key = key;
        this.name = name;
        this.lessonId = lessonId;
        this.userId = userId;
        this.checkCode = checkCode;
        this.account = account;
        this.status = status;
    }
    @Generated(hash = 1200154759)
    public Participant() {
    }
    public Long getKey() {
        return this.key;
    }
    public void setKey(Long key) {
        this.key = key;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLessonId() {
        return this.lessonId;
    }
    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getCheckCode() {
        return this.checkCode;
    }
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }


}
