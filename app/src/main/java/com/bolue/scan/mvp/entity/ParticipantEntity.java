package com.bolue.scan.mvp.entity;


import com.bolue.scan.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2017/6/26.
 */

public class ParticipantEntity extends BaseEntity {

    private DataEntity result;

    public void setResult(DataEntity result) {
        this.result = result;
    }

    public DataEntity getResult() {
        return result;
    }

    public static class DataEntity{
        private String name;
        private String company;
        private String position;
        private String tel;
        private String phone;
        private String email;
        private int status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
