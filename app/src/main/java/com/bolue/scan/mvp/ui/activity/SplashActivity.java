package com.bolue.scan.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.bolue.scan.R;
import com.bolue.scan.listener.AlertDialogListener;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.PhoneUtils;
import com.bolue.scan.utils.PreferenceUtils;
import com.bolue.scan.utils.TransformUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class SplashActivity extends BaseActivity implements AlertDialogListener{

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
        rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean grant) {
                        if(grant){
                            getDeviceInfo();
                            startTransTimer();
                        }else{
                            mAlertDialog = DialogUtils.create(SplashActivity.this);
                            mAlertDialog.show(SplashActivity.this,"权限设置","APP需要获取手机号码确保后台操作记录,请打开权限后重新进入应用","退出","去设置");
                        }

                    }
                });
    }

    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void getDeviceInfo(){
        String imei = PhoneUtils.getIMEI(this);
        String phone = PhoneUtils.getPhoneNumber(this);

        PreferenceUtils.setPrefString(this,"phone",phone);
        PreferenceUtils.setPrefString(this,"imei",imei);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startTransTimer(){

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
    public void onConFirm() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri packageURI = Uri.parse("package:" + getPackageName());
        intent.setData(packageURI);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCancel() {
        finish();
    }
}
