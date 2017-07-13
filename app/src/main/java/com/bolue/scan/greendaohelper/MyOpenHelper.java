package com.bolue.scan.greendaohelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bolue.scan.greendao.entity.Participant;
import com.bolue.scan.greendao.gen.DaoMaster;
import com.bolue.scan.greendao.gen.OffLineLessonsDao;
import com.bolue.scan.greendao.gen.ParticipantDao;
import com.bolue.scan.greendao.gen.SignDao;
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
        Log.i("greenDAO","onUpgrade "+"oldVersion:"+oldVersion+" newVersion:"+newVersion);
        MigrationHelper.migrate(db, ParticipantDao.class, OffLineLessonsDao.class, SignDao.class);//数据版本变更才会执行

    }

}
