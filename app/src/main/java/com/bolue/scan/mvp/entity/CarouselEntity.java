package com.bolue.scan.mvp.entity;


import com.bolue.scan.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/6/7.
 */

public class CarouselEntity extends BaseEntity {

    private ArrayList<DataEntity> result;

    public ArrayList<DataEntity> getResult() {
        return result;
    }

    public void setResult(ArrayList<DataEntity> result) {
        this.result = result;
    }

    public static class DataEntity{
        String content;
        int resource_type;
        int resource_id;
        String image;
        String url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getResource_type() {
            return resource_type;
        }

        public void setResource_type(int resource_type) {
            this.resource_type = resource_type;
        }

        public int getResource_id() {
            return resource_id;
        }

        public void setResource_id(int resource_id) {
            this.resource_id = resource_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
