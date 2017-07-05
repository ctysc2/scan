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
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.mvp.presenter.impl.OffLineDetailPresenterImpl;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.mvp.view.OffLineDetailView;
import com.bolue.scan.utils.DateTransformUtil;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.DimenUtil;
import com.bolue.scan.zxing.activity.CaptureActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class OfflineDetailActivity extends BaseActivity implements OffLineDetailView{

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

    private View headerView;

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
        rxPermissions = new RxPermissions(this);
        mOffLineDetailPresenterImpl.attachView(this);
        if(id != -1){
            mOffLineDetailPresenterImpl.beforeRequest();
            mOffLineDetailPresenterImpl.getOffLineDetail(id);
        }


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
                mOffLineDetailPresenterImpl.getOffLineDetail(id);
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


    @OnClick({R.id.rl_back,R.id.ll_scan,R.id.ll_download,R.id.ll_upload})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_scan:
                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean grant) {
                                if(grant){
                                    startActivityForResult(new Intent(OfflineDetailActivity.this,CaptureActivity.class),100);
                                }else{

                                }
                            }
                        });
                break;
            case R.id.ll_download:
                break;
            case R.id.ll_upload:
                break;

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void doclick(View v){


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data != null){
            Toast.makeText(OfflineDetailActivity.this,"扫描结果:"+data.getStringExtra("qr_code"),Toast.LENGTH_SHORT).show();
            return;
        }
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
            }else{
                xRefreshView.stopRefresh(false);
            }
    }
}
