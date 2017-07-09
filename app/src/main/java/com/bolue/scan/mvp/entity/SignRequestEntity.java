package com.bolue.scan.mvp.entity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/7/7.
 */

public class SignRequestEntity {
    private ArrayList<Check> sign_list;


    public void setSign_list(ArrayList<Check> sign_list) {
        this.sign_list = sign_list;
    }

    public ArrayList<Check> getSign_list() {
        return sign_list;
    }

    public static class Check{

        public Check(String checkcode) {
            this.checkcode = checkcode;
        }

        private String checkcode;

        public void setCheckcode(String checkcode) {
            this.checkcode = checkcode;
        }

        public String getCheckcode() {
            return checkcode;
        }
    }
}
