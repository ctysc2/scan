package com.bolue.scan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;


import com.bolue.scan.R;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.utils.PreferenceUtils;
import com.bolue.scan.utils.TransformUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

public class SplashActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        //过3秒执行下一步
        Observable.timer(1, TimeUnit.SECONDS).compose(TransformUtils.<Object>defaultSchedulers())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {

                        String userName = PreferenceUtils.getPrefString(SplashActivity.this,"userName","");
                        String passWord = PreferenceUtils.getPrefString(SplashActivity.this,"passWord","");

                        if(userName.equals("") || passWord.equals("")){
                            //未登录场合跳转登录界面
                            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                            finish();
                        }else{
                            //doLogin(userName,passWord);
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));
                            finish();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object data) {

                    }

                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
