package com.bolue.scan.greendaohelper;

import android.util.Log;

import com.bolue.scan.application.App;
import com.bolue.scan.greendao.entity.OffLineLessons;
import com.bolue.scan.greendao.entity.Participant;
import com.bolue.scan.greendao.gen.OffLineLessonsDao;
import com.bolue.scan.greendao.gen.ParticipantDao;
import com.bolue.scan.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cty on 2017/7/7.
 */

public class ParticipantHelper {

    private static ParticipantHelper mInstance;
    private ParticipantDao dao;

    public static ParticipantHelper getInstance(){

        if(mInstance == null){

            mInstance = new ParticipantHelper();

        }
        return mInstance;
    }

    private ParticipantHelper(){
        dao = App.getInstances().getDaoSession().getParticipantDao();
    }

    //更新所有
    public void insertParticipantlist(ArrayList<Participant> parts,int id){
        String userName = PreferenceUtils.getPrefString(App.getAppContext(),"userName","");
       //先删除
        deleteAll(id);

        for(int i = 0;i<parts.size();i++){
            parts.get(i).setAccount(userName);
        }

        //后重新添加
        dao.insertOrReplaceInTx(parts);

    }

    //删除当前账号下的所有课
    public void deleteAll(int id){
        dao.deleteInTx(getParticipantList(id));
    }



    //获取当前账号下缓存的所有课
    public List<Participant> getParticipantList(int id){

        String userName = PreferenceUtils.getPrefString(App.getAppContext(),"userName","");

        return dao.queryBuilder()
                .where(ParticipantDao.Properties.Account.eq(userName))
                .where(ParticipantDao.Properties.LessonId.eq(id))
                .build().list();

    }

}
