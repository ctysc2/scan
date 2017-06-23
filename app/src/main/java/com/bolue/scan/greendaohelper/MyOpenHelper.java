package com.bolue.scan.greendaohelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bolue.scan.greendao.entity.Participant;
import com.bolue.scan.greendao.gen.DaoMaster;
import com.bolue.scan.greendao.gen.ParticipantDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;


/**
 * Created by cty on 2017/4/16.
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {


    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, ParticipantDao.class);//数据版本变更才会执行
    }

}
