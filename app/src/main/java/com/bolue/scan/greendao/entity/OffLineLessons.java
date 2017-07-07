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
    private Long key;
    private Long id;
    private String title;
    private String brief_image;
    private long start_time;
    private long end_time;
    private int status;
    private String join_num;
    private String site;
    private double longitude;
    private double latitude;
    private int enroll_count;
    private String userName;
    @Generated(hash = 2130318846)
    public OffLineLessons(Long key, Long id, String title, String brief_image,
            long start_time, long end_time, int status, String join_num,
            String site, double longitude, double latitude, int enroll_count,
            String userName) {
        this.key = key;
        this.id = id;
        this.title = title;
        this.brief_image = brief_image;
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
        this.join_num = join_num;
        this.site = site;
        this.longitude = longitude;
        this.latitude = latitude;
        this.enroll_count = enroll_count;
        this.userName = userName;
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
    public String getBrief_image() {
        return this.brief_image;
    }
    public void setBrief_image(String brief_image) {
        this.brief_image = brief_image;
    }
    public long getStart_time() {
        return this.start_time;
    }
    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }
    public long getEnd_time() {
        return this.end_time;
    }
    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getJoin_num() {
        return this.join_num;
    }
    public void setJoin_num(String join_num) {
        this.join_num = join_num;
    }
    public String getSite() {
        return this.site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public int getEnroll_count() {
        return this.enroll_count;
    }
    public void setEnroll_count(int enroll_count) {
        this.enroll_count = enroll_count;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getKey() {
        return this.key;
    }
    public void setKey(Long key) {
        this.key = key;
    }



}
