package com.bolue.scan.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bolue.scan.R;
import com.bolue.scan.application.App;
import com.bolue.scan.greendao.entity.Sign;
import com.bolue.scan.greendaohelper.SignHelper;
import com.bolue.scan.listener.AlertDialogListener;
import com.bolue.scan.mvp.entity.ParticipantEntity;
import com.bolue.scan.mvp.entity.SignRequestEntity;
import com.bolue.scan.mvp.entity.base.BaseEntity;
import com.bolue.scan.mvp.presenter.impl.ParticipantPresenterImpl;
import com.bolue.scan.mvp.presenter.impl.SignPresenterImpl;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.mvp.view.DoSignView;
import com.bolue.scan.mvp.view.ParticipantDetailView;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.SystemTool;
import com.bolue.scan.utils.TransformUtils;
import com.bolue.scan.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class ParticipantDetailActivity extends BaseActivity implements ParticipantDetailView,DoSignView{

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
    SignPresenterImpl mSignPresenterImpl;

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

                //dummy
                //checkCode = "123456";
                if(TextUtils.isEmpty(checkCode)){
                    Toast.makeText(this,"参会人员信息异常",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isOnlineMode){

                    if(mAlertDialog != null && mAlertDialog.isShowing())
                        mAlertDialog.dismiss();

                    mAlertDialog = DialogUtils.create(this);
                    mAlertDialog.show(new AlertDialogListener() {
                        @Override
                        public void onConFirm() {

                            mAlertDialog.dismiss();

                            SignRequestEntity entity = new SignRequestEntity();
                            ArrayList<SignRequestEntity.Check> checkList = new ArrayList<>();
                            checkList.add(new SignRequestEntity.Check(checkCode));
                            entity.setSign_list(checkList);
                            mSignPresenterImpl.beforeRequest();
                            mSignPresenterImpl.doSign(entity);
                        }

                        @Override
                        public void onCancel() {

                            mAlertDialog.dismiss();

                        }
                    },"签到确认","点击确定签到后,不可以取消签到。");



                }else{
                    if(SignHelper.getInstance().getSign(resource_id,checkCode) != null){
                        Toast.makeText(this,"该信息已在离线队列中",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(mAlertDialog != null && mAlertDialog.isShowing())
                        mAlertDialog.dismiss();

                    mAlertDialog = DialogUtils.create(this);
                    mAlertDialog.show(new AlertDialogListener() {
                        @Override
                        public void onConFirm() {

                            mAlertDialog.dismiss();

                            //离线模式加入数据库缓存
                            Sign sign = new Sign();
                            sign.setId(resource_id);
                            sign.setCheckCode(checkCode);
                            SignHelper.getInstance().insertSign(sign);
                            Toast.makeText(ParticipantDetailActivity.this,"签到信息添加成功",Toast.LENGTH_SHORT).show();

                            //离线模式也显示已签到
                            mBtScan.setVisibility(View.GONE);
                            mBtScaned.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancel() {

                            mAlertDialog.dismiss();

                        }
                    },"签到确认","点击确定将添加至离线队列。");

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
        Log.i("detail","id:"+id);
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


        if(status == 5 || SignHelper.getInstance().getSign(resource_id,checkCode) != null ){
            mBtScan.setVisibility(View.GONE);
            mBtScaned.setVisibility(View.VISIBLE);
        }else{
            mBtScan.setVisibility(View.VISIBLE);
            mBtScaned.setVisibility(View.GONE);
        }
        mParticipantPresenterImpl.attachView(this);
        mParticipantPresenterImpl.getParticipantDetail(id,isInvited,resource_id);


        mSignPresenterImpl.attachView(this);

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
            mLoadDialog.show("正在上传签到信息");
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

    @Override
    public void doSignCompleted(BaseEntity entity) {
        if(entity != null && entity.getErr() == null){
            Toast.makeText(this,"签到成功!",Toast.LENGTH_SHORT).show();
            mBtScan.setVisibility(View.GONE);
            mBtScaned.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onNetChange(int networkType) {
        if(networkType == SystemTool.NETWORK_NONE){

            if(isOnlineMode == false)
                return;

            if(App.isKicked == true)
                return;

            if(mAlertDialog != null && mAlertDialog.isShowing())
                mAlertDialog.dismiss();

            Observable.timer(500, TimeUnit.MILLISECONDS).compose(TransformUtils.<Object>defaultSchedulers())
                    .subscribe(new Observer<Object>() {
                        @Override
                        public void onCompleted() {
                            mAlertDialog = DialogUtils.create(ParticipantDetailActivity.this);
                            mAlertDialog.show(new AlertDialogListener() {
                                @Override
                                public void onConFirm() {
                                    mAlertDialog.dismiss();
                                    Intent intent = new Intent(ParticipantDetailActivity.this,MainActivity.class);
                                    intent.putExtra("isToOffline",true);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancel() {
                                    mAlertDialog.dismiss();
                                }
                            },"网络异常","没有检测到网络连接,是否切换至离线模式?","取消","看离线");
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Object data) {

                        }

                    });




        }
    }


}
