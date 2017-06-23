package com.bolue.scan.mvp.entity;

import com.bolue.scan.mvp.entity.base.BaseEntity;
import com.bolue.scan.mvp.entity.base.BaseLessonEntity;
import com.bolue.scan.mvp.entity.base.BaseLiveLessonEntity;
import com.bolue.scan.mvp.entity.base.BaseQuestionEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/6/6.
 */

public class IndexEntity extends BaseEntity {

    public DataEntity result;

    public void setResult(DataEntity result) {
        this.result = result;
    }

    public DataEntity getResult() {
        return result;
    }

    public static class DataEntity{
        private ArrayList<BaseLiveLessonEntity> alreadyLive;
        private ArrayList<BaseLessonEntity> first_online_lessons;
        private LessonCount firstLessonCount;
        private ArrayList<BaseLessonEntity> second_online_lessons;
        private LessonCount secondLessonCount;
        private ArrayList<BaseLessonEntity> third_online_lessons;
        private LessonCount thirdLessonCount;
        private ArrayList<BaseQuestionEntity> question;
        private ArrayList<BaseLiveLessonEntity> reservedLives;

        public ArrayList<BaseLiveLessonEntity> getAlreadyLive() {
            return alreadyLive;
        }

        public void setAlreadyLive(ArrayList<BaseLiveLessonEntity> alreadyLive) {
            this.alreadyLive = alreadyLive;
        }

        public ArrayList<BaseLessonEntity> getFirst_online_lessons() {
            return first_online_lessons;
        }

        public void setFirst_online_lessons(ArrayList<BaseLessonEntity> first_online_lessons) {
            this.first_online_lessons = first_online_lessons;
        }

        public LessonCount getFirstLessonCount() {
            return firstLessonCount;
        }

        public void setFirstLessonCount(LessonCount firstLessonCount) {
            this.firstLessonCount = firstLessonCount;
        }

        public ArrayList<BaseLessonEntity> getSecond_online_lessons() {
            return second_online_lessons;
        }

        public void setSecond_online_lessons(ArrayList<BaseLessonEntity> second_online_lessons) {
            this.second_online_lessons = second_online_lessons;
        }

        public LessonCount getSecondLessonCount() {
            return secondLessonCount;
        }

        public void setSecondLessonCount(LessonCount secondLessonCount) {
            this.secondLessonCount = secondLessonCount;
        }

        public ArrayList<BaseLessonEntity> getThird_online_lessons() {
            return third_online_lessons;
        }

        public void setThird_online_lessons(ArrayList<BaseLessonEntity> third_online_lessons) {
            this.third_online_lessons = third_online_lessons;
        }

        public LessonCount getThirdLessonCount() {
            return thirdLessonCount;
        }

        public void setThirdLessonCount(LessonCount thirdLessonCount) {
            this.thirdLessonCount = thirdLessonCount;
        }

        public ArrayList<BaseQuestionEntity> getQuestion() {
            return question;
        }

        public void setQuestion(ArrayList<BaseQuestionEntity> question) {
            this.question = question;
        }

        public ArrayList<BaseLiveLessonEntity> getReservedLives() {
            return reservedLives;
        }

        public void setReservedLives(ArrayList<BaseLiveLessonEntity> reservedLives) {
            this.reservedLives = reservedLives;
        }

        class LessonCount{
            private int lessonCount;
            private int catalogNum;

            public int getCatalogNum() {
                return catalogNum;
            }

            public int getLessonCount() {
                return lessonCount;
            }

            public void setCatalogNum(int catalogNum) {
                this.catalogNum = catalogNum;
            }

            public void setLessonCount(int lessonCount) {
                this.lessonCount = lessonCount;
            }
        }



    }
}
