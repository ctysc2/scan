package com.bolue.scan.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolue.scan.R;
import com.bolue.scan.mvp.entity.ParticipantEntity;
import com.bolue.scan.mvp.presenter.impl.ParticipantPresenterImpl;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.mvp.view.ParticipantDetailView;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.zxing.activity.CaptureActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class ParticipantDetailActivity extends BaseActivity implements ParticipantDetailView{

    @BindView(R.id.tv_toolbar_title)
    TextView mTitle;

    @BindView(R.id.tv_name)
    TextView mName;

    @BindView(R.id.tv_job)
    TextView mJob;

    @BindView(R.id.tv_company)
    TextView mCompany;

    @BindView(R.id.tv_phone)
    TextView mPhone;

    @BindView(R.id.tv_email)
    TextView mEmail;

    @BindView(R.id.bt_sign)
    Button mBtScan;

    @BindView(R.id.bt_signed)
    Button mBtScaned;


    @Inject
    ParticipantPresenterImpl mParticipantPresenterImpl;

    private int id = -1;

    private boolean isInvited = false;

    private int resource_id = -1;

    private int status = 5;

    private boolean isOnlineMode = false;

    private String checkCode = "";

    @OnClick({R.id.rl_back,R.id.bt_sign})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_sign:

                if(isOnlineMode){

                }else{

                }

                break;

        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_participant_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();

        id = intent.getIntExtra("user_id",-1);
        isInvited = intent.getBooleanExtra("is_invited",false);
        resource_id = intent.getIntExtra("resource_id",-1);
        status = intent.getIntExtra("status",5);
        isOnlineMode = intent.getBooleanExtra("isOnlineMode",true);
        checkCode = intent.getStringExtra("checkCode");

        if(!isOnlineMode){
            mTitle.setText("参会人员信息(离线)");
        }else{
            mTitle.setText("参会人员信息");
        }


        if(status == 5){
            mBtScan.setVisibility(View.GONE);
            mBtScaned.setVisibility(View.VISIBLE);
        }else{
            mBtScan.setVisibility(View.VISIBLE);
            mBtScaned.setVisibility(View.GONE);
        }
        mParticipantPresenterImpl.attachView(this);
        mParticipantPresenterImpl.beforeRequest();
        mParticipantPresenterImpl.getParticipantDetail(id,isInvited,resource_id);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this);
            mLoadDialog.show("正在获取数据");
        }
    }

    @Override
    public void hideProgress(int reqType) {
        if(mLoadDialog != null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {

    }

    @Override
    public void getParticipantDetailCompleted(ParticipantEntity entity) {

        if(entity != null && entity.getErr() == null){
            mName.setText(entity.getResult().getName());
            mJob.setText(entity.getResult().getPosition());
            mCompany.setText(entity.getResult().getCompany());
            mPhone.setText("手机号  "+entity.getResult().getPhone());
            mEmail.setText("邮箱  "+entity.getResult().getEmail());
        }
    }
}
