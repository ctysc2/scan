package com.bolue.scan.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cty on 2017/7/7.
 */
@Entity
public class Sign {
    @Id
    private Long key;

    private int id;

    private String checkCode;

    private String userName;

    @Generated(hash = 1869989754)
    public Sign(Long key, int id, String checkCode, String userName) {
        this.key = key;
        this.id = id;
        this.checkCode = checkCode;
        this.userName = userName;
    }

    @Generated(hash = 2025164192)
    public Sign() {
    }

    public Long getKey() {
        return this.key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCheckCode() {
        return this.checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
