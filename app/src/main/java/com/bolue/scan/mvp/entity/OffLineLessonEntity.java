package com.bolue.scan.mvp.entity;

import com.bolue.scan.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/6/13.
 */

public class OffLineLessonEntity extends BaseEntity{

    public DataEntity result;

    public void setResult(DataEntity result) {
        this.result = result;
    }

    public DataEntity getResult() {
        return result;
    }

    public static class DataEntity{
        int id;
        String title;
        int type;
        String offline_brief;
        String brief_image;
        long start_time;
        long end_time;
        int address_id;
        private Address address;
        ArrayList<Member> enroll_list;
        int enroll_count;

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getOffline_brief() {
            return offline_brief;
        }

        public void setOffline_brief(String offline_brief) {
            this.offline_brief = offline_brief;
        }

        public String getBrief_image() {
            return brief_image;
        }

        public void setBrief_image(String brief_image) {
            this.brief_image = brief_image;
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

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public ArrayList<Member> getEnroll_list() {
            return enroll_list;
        }

        public void setEnroll_list(ArrayList<Member> enroll_list) {
            this.enroll_list = enroll_list;
        }

        public int getEnroll_count() {
            return enroll_count;
        }

        public void setEnroll_count(int enroll_count) {
            this.enroll_count = enroll_count;
        }

        public static class Member{
            String name;
            int status;
            int account_id;
            boolean is_invited;
            int invitee_id;
            int user_id;
            String checkcode;

            public void setCheckcode(String checkcode) {
                this.checkcode = checkcode;
            }

            public String getCheckcode() {
                return checkcode;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getAccount_id() {
                return account_id;
            }

            public void setAccount_id(int account_id) {
                this.account_id = account_id;
            }

            public boolean is_invited() {
                return is_invited;
            }

            public void setIs_invited(boolean is_invited) {
                this.is_invited = is_invited;
            }

            public int getInvitee_id() {
                return invitee_id;
            }

            public void setInvitee_id(int invitee_id) {
                this.invitee_id = invitee_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }
        }
        public static class Address{
            String cityname;
            String address;
            double map_x;
            double map_y;
            String site;

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public double getMap_x() {
                return map_x;
            }

            public void setMap_x(double map_x) {
                this.map_x = map_x;
            }

            public double getMap_y() {
                return map_y;
            }

            public void setMap_y(double map_y) {
                this.map_y = map_y;
            }

            public String getSite() {
                return site;
            }

            public void setSite(String site) {
                this.site = site;
            }
        }
    }

}
