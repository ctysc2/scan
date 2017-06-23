package com.bolue.scan.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.andview.refreshview.XRefreshView;
import com.bolue.scan.R;
import com.bolue.scan.adapter.CitysRecycleAdapter;
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.utils.DimenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/6/22.
 */

public class CityPopUpWindow extends PopupWindow{

    private ArrayList<LabelEntity.DataEntity.City> cities;

    private Activity mActivity;

    private View mView;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.xrefreshview)
    XRefreshView xRefreshView;
//    private RecyclerView recyclerView;
//
//    private XRefreshView xRefreshView;

    private LinearLayoutManager layoutManager;

    private CitysRecycleAdapter mAdapter;

    private OnItemClickListener mOnItemClickListener;

    public CityPopUpWindow(Activity mActivity,View view,ArrayList<LabelEntity.DataEntity.City> cities){

        super((int) DimenUtil.dp2px(80),(int)DimenUtil.dp2px(300));

        this.mActivity = mActivity;
        this.mView = view;
        this.cities = cities;
    }


    public void setData(ArrayList<LabelEntity.DataEntity.City> cities){
        this.cities = cities;
        mAdapter.setData(cities);
        mAdapter.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public void create(){

        LinearLayout container =
                (LinearLayout)mActivity.getLayoutInflater().inflate(
                        R.layout.layout_pop_list, null);

        ButterKnife.bind(this, container);

        backGroundFloatOn();
        setContentView(container);
        //setFocusable(true);
        setTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                return false;
                // 这里如果返回true的话,touch事件将被拦截,拦截后 PopupWindow 的 onTouchEvent 不被调用,这样点击外部区域无法dismiss
            }
        });
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backGroundFloatOff();
            }
        });
        showAsDropDown(mView, 0, 0);


        initRecycleView();

    }

    private void initRecycleView() {

        mAdapter = new CitysRecycleAdapter(cities,mActivity);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mOnItemClickListener.onItemClick(position);
                dismiss();
            }
        });
        layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        // 静默加载模式不能设置footerview
        recyclerView.setAdapter(mAdapter);
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.setPullRefreshEnable(false);
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);

    }

    private void  backGroundFloatOn(){
        WindowManager.LayoutParams params2 = mActivity.getWindow().getAttributes();
        params2.alpha = 0.8f;
        mActivity.getWindow().setAttributes(params2);
    }
    private void  backGroundFloatOff(){
        WindowManager.LayoutParams params2 = mActivity.getWindow().getAttributes();
        params2.alpha = 1.0f;
        mActivity.getWindow().setAttributes(params2);
    }

}
