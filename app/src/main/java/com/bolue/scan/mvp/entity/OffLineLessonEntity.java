package com.bolue.scan.mvp.entity;

/**
 * Created by cty on 2017/6/13.
 */

public class OffLineLessonEntity {

    private String name;
    private String position;
    private String date;

    public OffLineLessonEntity(String name, String position, String date) {
        this.name = name;
        this.position = position;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
