package com.bolue.scan.mvp.entity.base;

/**
 * Created by cty on 2017/6/6.
 */

public class BaseLiveLessonEntity extends BaseLessonEntity{

    private int live_status;
    private long start_time;
    private long end_time;

    public int getLive_status() {
        return live_status;
    }

    public void setLive_status(int live_status) {
        this.live_status = live_status;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }
}
