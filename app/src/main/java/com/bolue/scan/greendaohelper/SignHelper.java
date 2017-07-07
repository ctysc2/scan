package com.bolue.scan.greendaohelper;

import com.bolue.scan.application.App;
import com.bolue.scan.greendao.entity.Sign;
import com.bolue.scan.greendao.gen.ParticipantDao;
import com.bolue.scan.greendao.gen.SignDao;
import com.bolue.scan.utils.PreferenceUtils;

import java.util.List;

/**
 * Created by cty on 2017/7/7.
 */

public class SignHelper {
    private static SignHelper mInstance;
    private SignDao dao;

    public static SignHelper getInstance(){

        if(mInstance == null){

            mInstance = new SignHelper();

        }
        return mInstance;
    }

    private SignHelper(){
        dao = App.getInstances().getDaoSession().getSignDao();
    }

    //插入单个数据
    public void insertSign(Sign sign, int id){
        String userName = PreferenceUtils.getPrefString(App.getAppContext(),"userName","");
        Sign origin = getSign(id,sign.getCheckCode());

        sign.setUserName(userName);
        if(origin == null){
            //不存在则插入
            dao.insert(sign);
        }else{
            //存在则更新
            sign.setKey(origin.getKey());
            dao.update(sign);

        }

    }

    //删除课下单个缓存信息
    public void deleteOne(int id,String checkCode){
        dao.delete(getSign(id,checkCode));
    }

    //删除课下所有缓存信息
    public void deleteAll(int id){
        dao.deleteInTx(getSignList(id));
    }

    //获取单个
    public Sign getSign(int id,String checkCode){

        String userName = PreferenceUtils.getPrefString(App.getAppContext(),"userName","");

        return dao.queryBuilder()
                .where(SignDao.Properties.UserName.eq(userName))
                .where(SignDao.Properties.Id.eq(id))
                .where(SignDao.Properties.CheckCode.eq(checkCode))
                .build().unique();

    }


    //获取列表
    public List<Sign> getSignList(int id){

        String userName = PreferenceUtils.getPrefString(App.getAppContext(),"userName","");

        return dao.queryBuilder()
                .where(SignDao.Properties.UserName.eq(userName))
                .where(SignDao.Properties.Id.eq(id))
                .build().list();

    }

}
