package com.bolue.scan.mvp.ui.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewHeader;
import com.bolue.scan.R;
import com.bolue.scan.adapter.OfflineSignListAdapter;
import com.bolue.scan.greendao.entity.OffLineLessons;
import com.bolue.scan.greendao.entity.Participant;
import com.bolue.scan.greendaohelper.OffLineLessonsHelper;
import com.bolue.scan.greendaohelper.ParticipantHelper;
import com.bolue.scan.greendaohelper.SignHelper;
import com.bolue.scan.listener.AlertDialogListener;
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.mvp.entity.OffLineSignedEntity;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.DimenUtil;
import com.bolue.scan.zxing.activity.CaptureActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class OffLineSignListActivity extends BaseActivity {


    @BindView(R.id.ll_bottom)
    LinearLayout mEditBar;

    @BindView(R.id.tv_edit)
    TextView mTvEdit;

    @BindView(R.id.xrefreshview)
    XRefreshView xRefreshView;

    @BindView(R.id.xrecycleView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_selectAll)
    TextView mSelectAll;

    @BindView(R.id.tv_delete)
    TextView mDelete;

    private LinearLayoutManager layoutManager;

    private OfflineSignListAdapter mAdapter;

    private ArrayList<OffLineSignedEntity> dataSource = new ArrayList<>();

    private boolean isSelectedAll = false;

    @OnClick({R.id.rl_back,R.id.tv_edit,R.id.tv_selectAll,R.id.tv_delete})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_edit:
                mTvEdit.setSelected(!mTvEdit.isSelected());
                if(mTvEdit.isSelected()){
                    mTvEdit.setText("取消编辑");
                    mSelectAll.setText("全选");
                    startEditAnimation();

                    for(int i = 0;i<dataSource.size();i++){
                        dataSource.get(i).setSelected(false);
                    }
                    mAdapter.setData(dataSource,true);

                }else{

                    mTvEdit.setText("编辑");
                    endEditAnimation();
                    mAdapter.setData(dataSource,false);
                    isSelectedAll = false;

                }

                updateSelectNum();
                break;
            case R.id.tv_selectAll:
                if(!isSelectedAll){
                    for(int i = 0;i<dataSource.size();i++){
                        dataSource.get(i).setSelected(true);
                    }
                    mSelectAll.setText("取消全选");
                    isSelectedAll = true;
                }else{
                    for(int i = 0;i<dataSource.size();i++){
                        dataSource.get(i).setSelected(false);
                    }
                    mSelectAll.setText("全选");
                    isSelectedAll = false;
                }
                updateSelectNum();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_delete:

                boolean hasUnUploaded = false;

                for(int i = dataSource.size()-1;i>=0;i--){
                    if(dataSource.get(i).isSelected() == true){

                        if(dataSource.get(i).isUploaded()){
                            OffLineLessonsHelper.getInstance().deleteLesson(dataSource.get(i).getId());
                            ParticipantHelper.getInstance().deleteAll(dataSource.get(i).getId());
                            dataSource.remove(i);

                        }else{
                            hasUnUploaded = true;
                        }

                    }
                }
                updateSelectNum();
                checkAndExitEdit();
                mAdapter.notifyDataSetChanged();

                if(hasUnUploaded){
                    mAlertDialog = DialogUtils.create(this);
                    mAlertDialog.show(new AlertDialogListener() {
                        @Override
                        public void onConFirm() {
                            mAlertDialog.dismiss();
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialog.dismiss();
                        }
                    },"未删除通知","未上传的线下课没有批量删除,请确认后单个删除。","知道了");
                }


        }

    }
    private void startEditAnimation(){
        ValueAnimator mAnimator = ValueAnimator.ofInt(-(int)DimenUtil.dp2px(60), 0);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatorValue = (int)animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mEditBar.getLayoutParams();
                marginLayoutParams.bottomMargin = animatorValue;
                mEditBar.setLayoutParams(marginLayoutParams);
            }
        });
        mAnimator.setDuration(300);
        mAnimator.setTarget(mEditBar);
        mAnimator.start();
    }

    private void endEditAnimation(){

        ValueAnimator mAnimator = ValueAnimator.ofInt(0,-(int)DimenUtil.dp2px(60));
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatorValue = (int)animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mEditBar.getLayoutParams();
                marginLayoutParams.bottomMargin = animatorValue;
                mEditBar.setLayoutParams(marginLayoutParams);


            }
        });
        mAnimator.setDuration(300);
        mAnimator.setTarget(mEditBar);
        mAnimator.start();

    }

    private void updateSelectNum(){

        int num = 0;

        for(int i = 0;i<dataSource.size();i++){

            if(dataSource.get(i).isSelected()){
                num++;
            }
        }

        mDelete.setText(num == 0?"删除":"删除("+num+")");



    }

    private void checkAndExitEdit(){

        if(dataSource.size() == 0){
            mTvEdit.setSelected(!mTvEdit.isSelected());
            mDelete.setText("删除");
            mTvEdit.setText("编辑");
            endEditAnimation();
            mAdapter.setData(dataSource,false);
            isSelectedAll = false;
        }


    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_off_line_sign_list;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new OfflineSignListAdapter(dataSource,this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(OffLineSignListActivity.this,OfflineDetailActivity.class);
                intent.putExtra("id",dataSource.get(position).getId());
                intent.putExtra("isOnlineMode",false);
                startActivityForResult(intent,100);
            }
        });
        mAdapter.setOnSelectClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OffLineSignedEntity entity  = dataSource.get(position);
                dataSource.get(position).setSelected(!entity.isSelected());
                mAdapter.notifyDataSetChanged();
                updateSelectNum();
            }
        });
        mAdapter.setOnDeleteClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                if(dataSource.get(position).isUploaded() == false){

                    mAlertDialog = DialogUtils.create(OffLineSignListActivity.this);
                    mAlertDialog.show(new AlertDialogListener() {
                        @Override
                        public void onConFirm() {
                            OffLineLessonsHelper.getInstance().deleteLesson(dataSource.get(position).getId());
                            ParticipantHelper.getInstance().deleteAll(dataSource.get(position).getId());
                            SignHelper.getInstance().deleteAll(dataSource.get(position).getId());
                            mAdapter.delete(position);
                            updateSelectNum();

                            if(mAdapter.isEdit())
                                checkAndExitEdit();

                            mAlertDialog.dismiss();
                        }

                        @Override
                        public void onCancel() {

                            mAlertDialog.dismiss();

                        }
                    },"删除离线数据","该线下课签到的数据还未上传,您确定要删除吗？删除后可能会造成数据丢失!");

                }else{
                    OffLineLessonsHelper.getInstance().deleteLesson(dataSource.get(position).getId());
                    ParticipantHelper.getInstance().deleteAll(dataSource.get(position).getId());
                    mAdapter.delete(position);
                    updateSelectNum();

                    if(mAdapter.isEdit())
                        checkAndExitEdit();

                }





            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullRefreshEnable(false);
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);

        getDataFromDb();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK ){
            Log.i("signList","onActivityResult");
           getDataFromDb();
           return;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getDataFromDb() {

        //从数据库读取数据
        List<OffLineLessons> lessons = OffLineLessonsHelper.getInstance().getAll();

        if(lessons != null){
            dataSource = new ArrayList<>();
            for (OffLineLessons lesson:lessons
                    ) {
                OffLineSignedEntity entity = new OffLineSignedEntity();
                entity.setId(lesson.getId().intValue());
                entity.setStatus(lesson.getStatus());
                entity.setTitle(lesson.getTitle());
                entity.setBrief_image(lesson.getBrief_image());
                entity.setJoin_num(lesson.getJoin_num());
                dataSource.add(entity);
            }

            mAdapter.setData(dataSource,false);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
