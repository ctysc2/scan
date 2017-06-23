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
    private Long id;
    private String name;
    private String userId;
    @Generated(hash = 814071873)
    public Participant(Long id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
    @Generated(hash = 1200154759)
    public Participant() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
