package com.bolue.scan.greendaohelper;

import android.util.Log;

import com.bolue.scan.application.App;
import com.bolue.scan.greendao.entity.OffLineLessons;
import com.bolue.scan.greendao.gen.OffLineLessonsDao;
import com.bolue.scan.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cty on 2017/7/6.
 */

public class OffLineLessonsHelper {

    private static OffLineLessonsHelper mInstance;
    private OffLineLessonsDao dao;

    public static OffLineLessonsHelper getInstance(){

        if(mInstance == null){

            mInstance = new OffLineLessonsHelper();

        }
        return mInstance;
    }

    private OffLineLessonsHelper(){
        dao = App.getInstances().getDaoSession().getOffLineLessonsDao();
    }

    public void insertOffLine(OffLineLessons lesson,long id){
        //同意添加userName
        String userName = PreferenceUtils.getPrefString(App.getAppContext(),"userName","");

        OffLineLessons origin = getLessonById(id);


        lesson.setUserName(userName);

        if(origin != null){
            //存在则更新
            lesson.setKey(origin.getKey());
            dao.update(lesson);
        }else{
            //不存在则插入

            dao.insert(lesson);

        }




//        lesson.setUserName(userName);
//        dao.insertOrReplaceInTx(lesson);
    }

    //删除当前账号下的所有课
    public void deleteAll(){
        dao.deleteInTx(getAll());
    }

    public void deleteLesson(long id){

        dao.delete(getLessonById(id));
    }

    //根据ID查询当前账号下缓存的一门课
    public  OffLineLessons getLessonById(long id){
        Log.i("GreenDao","要查询的id:"+id);
        String userName = PreferenceUtils.getPrefString(App.getAppContext(),"userName","");
        OffLineLessons lesson = dao.queryBuilder()
                .where(OffLineLessonsDao.Properties.Id.eq(id))
                .where(OffLineLessonsDao.Properties.UserName.eq(userName))
                .build()
                .unique();

        if(lesson == null){
            Log.i("GreenDao","没有查询到课程");

        }else {
            Log.i("GreenDao","查询到课程 id为"+id);
        }

        return lesson;
    }

    //获取当前账号下缓存的所有课
    public List<OffLineLessons> getAll(){

        String userName = PreferenceUtils.getPrefString(App.getAppContext(),"userName","");

        return dao.queryBuilder()
                .where(OffLineLessonsDao.Properties.UserName.eq(userName))
                .build().list();

    }


}
