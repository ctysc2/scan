package com.bolue.scan.mvp.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.bolue.scan.R;
import com.bolue.scan.application.App;
import com.bolue.scan.di.component.DaggerFragmentComponent;
import com.bolue.scan.di.component.FragmentComponent;
import com.bolue.scan.di.module.FragmentModule;
import com.bolue.scan.mvp.presenter.base.BasePresenter;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.DimenUtil;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by cty on 2017/6/5.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    protected FragmentComponent mFragmentComponent;
    protected T mPresenter;
    protected DialogUtils mAlertDialog;
    protected DialogUtils mLoadDialog;
    private View mFragmentView;
    protected boolean isVisible;
    protected boolean isViewCreated;
    protected boolean isFirstLoad = true;

    public abstract void initInjector();

    public abstract void initViews(View view);

    public abstract int getLayoutId();

    protected Subscription mSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((App) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        initInjector();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mFragmentView);
            isViewCreated = true;
            initViews(mFragmentView);
            adaptToolBarHeight();
        }

        return mFragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.onDestroy();
        }


    }

    private  void adaptToolBarHeight() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){

            Toolbar toolbar = (Toolbar) mFragmentView.findViewById(R.id.toolbar);
            Log.i("cty", "toolbar:" + toolbar);
            if (toolbar != null) {
                toolbar.setPadding(0, 0, 0, 0);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();

                params.height = (int) DimenUtil.dp2px(60);
                toolbar.setLayoutParams(params);

            }
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInVisible();
        }
    }
    //fragment视图创建完成
    protected void onViewCreated(){

    }
    //fragment显示
    protected void onVisible(){

    }
    //fragment不显示
    protected void onInVisible(){

    }
}
