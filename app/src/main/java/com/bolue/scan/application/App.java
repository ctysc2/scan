package com.bolue.scan.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.bolue.scan.di.component.ApplicationComponent;
import com.bolue.scan.di.component.DaggerApplicationComponent;
import com.bolue.scan.di.module.ApplicationModule;
import com.bolue.scan.greendao.gen.DaoMaster;
import com.bolue.scan.greendao.gen.DaoSession;
import com.bolue.scan.greendaohelper.MyOpenHelper;
import com.bolue.scan.utils.CrashHandler;
import com.bolue.scan.utils.PhoneUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by cty on 2017/6/4.
 */

public class App extends MultiDexApplication {
    private static App sAppContext;
    private ApplicationComponent mApplicationComponent;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private MyOpenHelper mHelper;
    private static Activity sActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

            //初始化崩溃日志
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(this);
            initApplicationComponent();

            //初始化Fresco
            ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, new OkHttpClient())
                    .setDownsampleEnabled(true)
                    .build();
            Fresco.initialize(this, config);
            setDataBase();
            registerActivitys();

        }
    }

    private void registerActivitys() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d("YWK",activity+"onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d("YWK",activity+"onActivityStarted");
                sActivity=activity;

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
    public static Activity getActivity(){
        return sActivity;
    }
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
    public static Context getAppContext() {
        return sAppContext;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    private void setDataBase() {
        mHelper = new MyOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    private static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


}
