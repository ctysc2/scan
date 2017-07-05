package com.bolue.scan.mvp.ui.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.mvp.entity.OffLineSignedEntity;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.utils.DimenUtil;
import com.bolue.scan.zxing.activity.CaptureActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.Iterator;

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

    private SlidingMenu menu;
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
                for(int i = dataSource.size()-1;i>=0;i--){
                    if(dataSource.get(i).isSelected() == true){
                        dataSource.remove(i);
                    }
                }
                updateSelectNum();
                checkAndExitEdit();
                mAdapter.notifyDataSetChanged();


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

        OffLineSignedEntity entity = new OffLineSignedEntity();
        entity.setBrief_image("http://g.hiphotos.baidu.com/baike/s%3D220/sign=ef1b45b36e061d957946303a4bf50a5d/1b4c510fd9f9d72a26432c96d42a2834349bbb11.jpg");
        entity.setJoin_num("13");
        entity.setTitle("瓜肉");

        OffLineSignedEntity entity2 = new OffLineSignedEntity();
        entity2.setBrief_image("http://f.hiphotos.baidu.com/baike/s%3D220/sign=b34f01fef6246b607f0eb576dbf91a35/77094b36acaf2eddae8a16448d1001e939019327.jpg");
        entity2.setJoin_num("10");
        entity2.setTitle("Gio");


        OffLineSignedEntity entity3 = new OffLineSignedEntity();
        entity3.setBrief_image("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2106708056,1729294802&fm=58");
        entity3.setJoin_num("17");
        entity3.setTitle("马丁斯");

        dataSource.add(entity);
        dataSource.add(entity2);
        dataSource.add(entity3);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new OfflineSignListAdapter(dataSource,this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(OffLineSignListActivity.this,"item",Toast.LENGTH_SHORT).show();
                menu.toggle();
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
            public void onItemClick(int position) {
                mAdapter.delete(position);
                updateSelectNum();

                if(mAdapter.isEdit())
                    checkAndExitEdit();
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


        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.setOffsetFadeDegree(0.2f);
        menu.setBehindWidth((int)DimenUtil.dp2px(160));
        menu.setFadeEnabled(true);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
