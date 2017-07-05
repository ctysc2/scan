package com.bolue.scan.mvp.entity;

import com.bolue.scan.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2017/7/5.
 */

public class LoginEntity extends BaseEntity{

    private DataEntity result;

    public void setResult(DataEntity result) {
        this.result = result;
    }

    public DataEntity getResult() {
        return result;
    }

    public static class DataEntity{
        String openid;
        String code;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
