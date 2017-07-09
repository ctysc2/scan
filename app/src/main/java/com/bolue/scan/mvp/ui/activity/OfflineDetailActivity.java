package com.bolue.scan.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.Text;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.andview.refreshview.XRefreshViewHeader;
import com.bolue.scan.R;
import com.bolue.scan.adapter.OffLineDetailAdapter;
import com.bolue.scan.application.App;
import com.bolue.scan.greendao.entity.OffLineLessons;
import com.bolue.scan.greendao.entity.Participant;
import com.bolue.scan.greendao.entity.Sign;
import com.bolue.scan.greendaohelper.OffLineLessonsHelper;
import com.bolue.scan.greendaohelper.ParticipantHelper;
import com.bolue.scan.greendaohelper.SignHelper;
import com.bolue.scan.listener.AlertDialogListener;
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.mvp.entity.SignRequestEntity;
import com.bolue.scan.mvp.entity.base.BaseEntity;
import com.bolue.scan.mvp.presenter.impl.OffLineDetailPresenterImpl;
import com.bolue.scan.mvp.presenter.impl.SignPresenterImpl;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.mvp.view.DoSignView;
import com.bolue.scan.mvp.view.OffLineDetailView;
import com.bolue.scan.utils.DateTransformUtil;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.DimenUtil;
import com.bolue.scan.utils.SystemTool;
import com.bolue.scan.zxing.activity.CaptureActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class OfflineDetailActivity extends BaseActivity implements OffLineDetailView,DoSignView {

    @BindView(R.id.rl_back)
    RelativeLayout mBack;

    @BindView(R.id.xrefreshview)
    XRefreshView xRefreshView;

    @BindView(R.id.xrecycleView)
    RecyclerView recyclerView;

    @BindView(R.id.ll_panel)
    LinearLayout mPanel;

    @BindView(R.id.tv_panel_participant_num)
    TextView mPanelNum;

    @BindView(R.id.ll_download)
    LinearLayout mLLDownload;

    @BindView(R.id.ll_upload)
    LinearLayout mLLUpLoad;

    @BindView(R.id.ll_download_online)
    LinearLayout getmLLDownloadOnline;

    @BindView(R.id.tv_toolbar_title)
    TextView mToolTitle;


    private TextView mTitle;

    private TextView mLocation;

    private TextView mDate;

    private TextView mParticipantNum;

    private LinearLayoutManager layoutManager;

    private double longitude;

    private double latitude;

    private String name;

    private RelativeLayout mRLLocation;
    @Inject
    OffLineDetailPresenterImpl mOffLineDetailPresenterImpl;

    private RxPermissions rxPermissions;

    private OffLineDetailAdapter adapter;

    private ArrayList<OffLineLessonEntity.DataEntity.Member> dataSource = new ArrayList<>();
    //线下课id
    private int id;

    private boolean isOnlineMode = true;

    private int status;

    private String join_num;

    private View headerView;

    private boolean isWillUpdated = false;

    private OffLineLessons lesson;

    private List<Participant> parts;

    @Inject
    SignPresenterImpl mSignPresenterImpl;

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
        id = getIntent().getIntExtra("id",-1);
        isOnlineMode = getIntent().getBooleanExtra("isOnlineMode",true);

        if(!isOnlineMode){
            mToolTitle.setText("签到详情(离线)");
        }else{
            mToolTitle.setText("签到详情");
        }
        status = getIntent().getIntExtra("status",0);
        join_num = getIntent().getStringExtra("join_num");


        rxPermissions = new RxPermissions(this);

        mSignPresenterImpl.attachView(this);
        mOffLineDetailPresenterImpl.attachView(this);


        xRefreshView.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);

        adapter = new OffLineDetailAdapter(dataSource, this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OffLineLessonEntity.DataEntity.Member member = dataSource.get(position);
                Intent intent = new Intent(OfflineDetailActivity.this,ParticipantDetailActivity.class);
                intent.putExtra("user_id",member.getUser_id());
                intent.putExtra("is_invited",member.is_invited());
                intent.putExtra("resource_id",id);
                intent.putExtra("status",member.getStatus());
                intent.putExtra("isOnlineMode",isOnlineMode);
                intent.putExtra("checkCode",member.getCheckcode());
                startActivity(intent);

            }
        });
        // 设置静默加载模式
//		xRefreshView1.setSilenceLoadMore();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        headerView = adapter.setHeaderView(R.layout.detail_header, recyclerView);
        mRLLocation = (RelativeLayout)headerView.findViewById(R.id.rl_location);
        mRLLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfflineDetailActivity.this,AMapViewActivity.class);
                intent.putExtra("longitude",longitude);
                intent.putExtra("latitude",latitude);
                intent.putExtra("name",name);

                startActivity(intent);
            }
        });
        mTitle = (TextView) headerView.findViewById(R.id.tv_title);
        mLocation = (TextView) headerView.findViewById(R.id.tv_location);
        mDate = (TextView)headerView.findViewById(R.id.tv_date);
        mParticipantNum = (TextView)headerView.findViewById(R.id.tv_participant_num);

        xRefreshView.setCustomHeaderView(new XRefreshViewHeader(this));
        recyclerView.setAdapter(adapter);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                setOriginDatas();
            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }
        });
        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy-=dy;
                if(Math.abs(totalDy) >= DimenUtil.dp2px(235)){
                    mPanel.setVisibility(View.VISIBLE);
                }else{
                    mPanel.setVisibility(View.GONE);
                }
            }
        });
    }


    @OnClick({R.id.rl_back,R.id.ll_scan,R.id.ll_download,R.id.ll_upload,R.id.ll_download_online})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_back:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.ll_scan:
                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean grant) {
                                if(grant){

                                    if(dataSource == null || dataSource.size() == 0){
                                        Toast.makeText(OfflineDetailActivity.this,"参会人员数据异常",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    ArrayList<String> checkcodes = new ArrayList<String>();

                                    for(int i = 0;i<dataSource.size();i++){
                                        checkcodes.add(dataSource.get(i).getCheckcode());
                                    }

                                    if(checkcodes.size() == 0){
                                        Toast.makeText(OfflineDetailActivity.this,"参会人员数据异常",Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    Intent intent = new Intent(OfflineDetailActivity.this,CaptureActivity.class);

                                    intent.putExtra("isOnlineMode",isOnlineMode);
                                    intent.putExtra("id",id);
                                    intent.putStringArrayListExtra("checkcodes",checkcodes);

                                    startActivity(intent);
                                }else{

                                }
                            }
                        });
                break;
            case R.id.ll_download:
                mAlertDialog =  DialogUtils.create(this);
                mAlertDialog.show(new AlertDialogListener() {
                    @Override
                    public void onConFirm() {
                        isWillUpdated = true;
                        mOffLineDetailPresenterImpl.beforeRequest();
                        mOffLineDetailPresenterImpl.getOffLineDetail(id);
                        mAlertDialog.dismiss();
                    }

                    @Override
                    public void onCancel() {
                        mAlertDialog.dismiss();
                    }
                },"更新参会数据","该线下课的参会数据有更新,按确定更新。");
                break;
            case R.id.ll_upload:

                final List<Sign> list = SignHelper.getInstance().getSignList(id);

                if(list == null || list.size() == 0){
                    Toast.makeText(this,"没有需要上传的数据",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAlertDialog =  DialogUtils.create(this);
                mAlertDialog.show(new AlertDialogListener() {
                    @Override
                    public void onConFirm() {

                        mAlertDialog.dismiss();

                        SignRequestEntity entity = new SignRequestEntity();
                        ArrayList<SignRequestEntity.Check> checkList = new ArrayList<>();
                        for(int i = 0;i<list.size();i++){

                            Sign sign = list.get(i);
                            checkList.add(new SignRequestEntity.Check(sign.getCheckCode()));

                        }

                        entity.setSign_list(checkList);

                        mSignPresenterImpl.setReqType(1);
                        mSignPresenterImpl.beforeRequest();
                        mSignPresenterImpl.doSign(entity);
                    }

                    @Override
                    public void onCancel() {
                        mAlertDialog.dismiss();

                    }
                },"上传签到数据","已离线缓存"+list.size()+"个签到信息,是否现在上传？","取消","上传");



                break;
            case R.id.ll_download_online:
                mAlertDialog =  DialogUtils.create(this);
                String title;
                String content;
                if(OffLineLessonsHelper.getInstance().getLessonById(id)  == null){
                    title = "下载参会数据";
                    content = "如果您使用离线模式，请按确定下载该线下课的参会数据。";
                }else{
                    title = "更新参会数据";
                    content = "该线下课的参会数据有更新,按确定更新。";
                }

                mAlertDialog.show(new AlertDialogListener() {
                    @Override
                    public void onConFirm() {
                        isWillUpdated = true;
                        mOffLineDetailPresenterImpl.beforeRequest();
                        mOffLineDetailPresenterImpl.getOffLineDetail(id);
                        mAlertDialog.dismiss();
                    }

                    @Override
                    public void onCancel() {
                        mAlertDialog.dismiss();
                    }
                },title,content);
                break;

        }

    }

    private void setOriginDatas(){
        if(isOnlineMode){
            mLLDownload.setVisibility(View.GONE);
            mLLUpLoad.setVisibility(View.GONE);
            //在线模式下从服务器更新数据
            if(id != -1){
                //mOffLineDetailPresenterImpl.beforeRequest();
                mOffLineDetailPresenterImpl.getOffLineDetail(id);
            }
        }else{
            getmLLDownloadOnline.setVisibility(View.GONE);
            //离线模式下从数据库获取数据
            lesson = OffLineLessonsHelper.getInstance().getLessonById(id);

            //离线模式直接更新
            if(lesson != null){
                mTitle.setText(lesson.getTitle());
                mLocation.setText(lesson.getSite());
                mDate.setText(DateTransformUtil.transFormDate(lesson.getStart_time(),lesson.getEnd_time()));
                mParticipantNum.setText("("+lesson.getEnroll_count()+")");
                longitude = lesson.getLongitude();
                latitude = lesson.getLatitude();
                name = lesson.getSite();
                mPanelNum.setText("("+lesson.getEnroll_count()+")");

                status = lesson.getStatus();
                join_num = lesson.getJoin_num();
            }

            parts = ParticipantHelper.getInstance().getParticipantList(id);

            if(parts != null){

                dataSource = new ArrayList<>();

                for(int i = 0;i<parts.size();i++){

                    Participant part = parts.get(i);

                    OffLineLessonEntity.DataEntity.Member member = new OffLineLessonEntity.DataEntity.Member();
                    member.setName(part.getName());
                    member.setCheckcode(part.getCheckCode());
                    member.setStatus(part.getStatus());
                    member.setUser_id(part.getUserId());
                    member.setIs_invited(part.getIs_invited());
                    dataSource.add(member);
                }

                adapter.setData(dataSource);
                adapter.notifyDataSetChanged();
            }

            xRefreshView.stopRefresh();

        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        setOriginDatas();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void doclick(View v){


    }



    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this);
            if(reqType == 1)
                mLoadDialog.show("正在上传签到信息");
            else
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
        xRefreshView.stopRefresh(false);
    }

    @Override
    public void getOffLineDetailCompleted(OffLineLessonEntity entity) {
            if(entity != null && entity.getErr() == null){


                longitude = entity.getResult().getAddress().getMap_x();
                latitude = entity.getResult().getAddress().getMap_y();
                name = entity.getResult().getAddress().getSite();

                mTitle.setText(entity.getResult().getTitle());
                mLocation.setText(entity.getResult().getAddress().getSite());
                mDate.setText(DateTransformUtil.transFormDate(entity.getResult().getStart_time(),entity.getResult().getEnd_time()));
                mParticipantNum.setText("("+entity.getResult().getEnroll_count()+")");
                mPanelNum.setText("("+entity.getResult().getEnroll_count()+")");

                dataSource = entity.getResult().getEnroll_list();
                adapter.setData(dataSource);
                adapter.notifyDataSetChanged();
                xRefreshView.stopRefresh();

                //手动下载或是数据库中已有该课程信息
                if(isWillUpdated || OffLineLessonsHelper.getInstance().getLessonById(id) != null){

                    //step1: 保存课程详情
                    OffLineLessons lesson =
                            new OffLineLessons();
                    lesson.setId((long)id);
                    lesson.setBrief_image(entity.getResult().getBrief_image());
                    lesson.setEnd_time(entity.getResult().getEnd_time());
                    lesson.setStart_time(entity.getResult().getStart_time());
                    lesson.setEnroll_count(entity.getResult().getEnroll_count());
                    lesson.setJoin_num(join_num);
                    lesson.setLongitude(entity.getResult().getAddress().getMap_x());
                    lesson.setLatitude(entity.getResult().getAddress().getMap_y());
                    lesson.setSite(entity.getResult().getAddress().getSite());
                    lesson.setTitle(entity.getResult().getTitle());
                    lesson.setStatus(status);
                    OffLineLessonsHelper.getInstance().insertOffLine(lesson,id);


                    //step:2 保存参会人信息
                    ArrayList<Participant> parts = new ArrayList<>();
                    for(int i = 0;i<dataSource.size();i++){
                        Participant part = new Participant();
                        OffLineLessonEntity.DataEntity.Member member = dataSource.get(i);
                        part.setCheckCode(member.getCheckcode());
                        part.setLessonId(id);
                        part.setName(member.getName());
                        part.setUserId(member.getUser_id());
                        part.setStatus(member.getStatus());
                        part.setIs_invited(member.is_invited());
                        parts.add(part);
                    }

                    ParticipantHelper.getInstance().insertParticipantlist(parts,id);

                    if(isWillUpdated ){
                        if(isOnlineMode)
                            Toast.makeText(this,"下载成功,可切换至离线模式下查看~",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this,"离线数据已更新~",Toast.LENGTH_SHORT).show();
                    }

                    isWillUpdated = false;
                }

            }else{
                xRefreshView.stopRefresh(false);
            }
    }

    @Override
    public void doSignCompleted(BaseEntity entity) {

        if(entity != null && entity.getErr() == null){

            SignHelper.getInstance().deleteAll(id);
            Toast.makeText(this,"签到数据上传成功!",Toast.LENGTH_SHORT).show();

            //重新从后台获取数据并更新数据库
            mOffLineDetailPresenterImpl.getOffLineDetail(id);
        }

    }

    @Override
    public void onBackPressed() {
        if(mAlertDialog != null && mAlertDialog.isShowing()){
            mAlertDialog.dismiss();
            return;
        }

        setResult(RESULT_OK);
        finish();
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

            mAlertDialog = DialogUtils.create(this);
            mAlertDialog.show(new AlertDialogListener() {
                @Override
                public void onConFirm() {
                    mAlertDialog.dismiss();
                    Intent intent = new Intent(OfflineDetailActivity.this,MainActivity.class);
                    intent.putExtra("isToOffline",true);
                    startActivity(intent);
                }

                @Override
                public void onCancel() {
                    mAlertDialog.dismiss();
                }
                },"网络异常","没有检测到网络连接,是否切换至离线模式?","取消","看离线");




        }
    }
}
