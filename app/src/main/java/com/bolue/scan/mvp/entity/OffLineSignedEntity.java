package com.bolue.scan.mvp.entity;

import com.codbking.calendar.entity.CalendarData;

/**
 * Created by cty on 2017/7/4.
 */

public class OffLineSignedEntity extends CalendarData{

    private boolean isUploaded;

    private boolean isSelected;

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
