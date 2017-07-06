package com.bolue.scan.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cty on 2017/7/6.
 */
@Entity
public class OffLineLessons {
    @Id
    private Long id;
    private String title;
    private String type;
    private String brief_image;
    @Generated(hash = 1713570991)
    public OffLineLessons(Long id, String title, String type, String brief_image) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.brief_image = brief_image;
    }
    @Generated(hash = 2035530795)
    public OffLineLessons() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getBrief_image() {
        return this.brief_image;
    }
    public void setBrief_image(String brief_image) {
        this.brief_image = brief_image;
    }

}
