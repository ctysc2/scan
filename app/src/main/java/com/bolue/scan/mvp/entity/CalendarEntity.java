package com.bolue.scan.mvp.entity;

import com.bolue.scan.mvp.entity.base.BaseEntity;
import com.codbking.calendar.entity.CalendarData;

import java.util.ArrayList;

/**
 * Created by cty on 2017/6/14.
 */

public class CalendarEntity extends BaseEntity{

    private ArrayList<DataEntity> result;

    public void setResult(ArrayList<DataEntity> result) {
        this.result = result;
    }

    public ArrayList<DataEntity> getResult() {
        return result;
    }

    public static class DataEntity{
        private ArrayList<CalendarData> lessons;

        private String date;

        public String getDate() {
            return date;
        }

        public void setData(String date) {
            this.date = date;
        }

        public void setLessons(ArrayList<CalendarData> lessons) {
            this.lessons = lessons;
        }

        public ArrayList<CalendarData> getLessons() {
            return lessons;
        }


    }
}
