package com.bolue.scan.mvp.entity;

import com.bolue.scan.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/6/22.
 */

public class LabelEntity extends BaseEntity {

    private DataEntity result;

    public void setResult(DataEntity result) {
        this.result = result;
    }

    public DataEntity getResult() {
        return result;
    }

    public static class DataEntity{

        private ArrayList<City> city;

        public void setCity(ArrayList<City> city) {
            this.city = city;
        }

        public ArrayList<City> getCity() {
            return city;
        }

        public static class City{
            private int city_id;
            private String city_name;
            private boolean isSelected =false;

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public int getCity_id() {
                return city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }
        }
    }
}
