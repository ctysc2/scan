package com.bolue.scan.mvp.ui.activity.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.bolue.scan.R;
import com.bolue.scan.application.App;
import com.bolue.scan.di.component.ActivityComponent;
import com.bolue.scan.di.component.DaggerActivityComponent;
import com.bolue.scan.di.module.ActivityModule;
import com.bolue.scan.mvp.presenter.base.BasePresenter;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.DimenUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import rx.Subscription;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter;

    protected DialogUtils mAlertDialog;

    protected DialogUtils mLoadDialog;

    protected boolean mIsStatusTranslucent = true;

    protected ActivityComponent mActivityComponent;

    public abstract int getLayoutId();

    public abstract void initInjector();

    public abstract void initViews();

    protected Subscription mSubscription;

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
        int layout = getLayoutId();

        setContentView(layout);
        initInjector();
        ButterKnife.bind(this);
        if(mIsStatusTranslucent == true){
            setStatusBarTranslucent();
        }else{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            if (toolbar != null) {
                toolbar.setPadding(0, 0, 0, 0);
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                params.height = (int) DimenUtil.dp2px(60);
                toolbar.setLayoutParams(params);

            }
        }


    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null)
            mPresenter.onDestroy();

    }
    public void setStatusBarTranslucent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
            // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);// SDK21

        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        else{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            if (toolbar != null) {
                toolbar.setPadding(0, 0, 0, 0);
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                params.height = (int) DimenUtil.dp2px(60);
                toolbar.setLayoutParams(params);

            }
        }


    }
    public void setStatusBarDarkMode(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
