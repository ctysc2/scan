package com.bolue.scan.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bolue.scan.R;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.zxing.activity.CaptureActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class OfflineDetailActivity extends BaseActivity {

    RxPermissions rxPermissions;

    @Override
    public int getLayoutId() {
        return R.layout.activity_offline_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        rxPermissions = new RxPermissions(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void doclick(View v){
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean grant) {
                        if(grant){
                            startActivityForResult(new Intent(OfflineDetailActivity.this,CaptureActivity.class),100);
                        }else{
                            Toast.makeText(OfflineDetailActivity.this,"已拒绝Camera权限",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data != null){
            Toast.makeText(OfflineDetailActivity.this,"扫描结果:"+data.getStringExtra("qr_code"),Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
