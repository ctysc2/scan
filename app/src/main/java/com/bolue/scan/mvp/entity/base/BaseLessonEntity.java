package com.bolue.scan.mvp.entity.base;

/**
 * Created by cty on 2017/6/6.
 */

public class BaseLessonEntity {
    private int id;
    private boolean charge_type;
    private String brief_image;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCharge_type() {
        return charge_type;
    }

    public void setCharge_type(boolean charge_type) {
        this.charge_type = charge_type;
    }

    public String getBrief_image() {
        return brief_image;
    }

    public void setBrief_image(String brief_image) {
        this.brief_image = brief_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
