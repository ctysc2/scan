package com.bolue.scan.mvp.presenter.base;

import android.support.annotation.NonNull;


import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.view.base.BaseView;
import com.bolue.scan.utils.RxBus;

import rx.Subscription;

/**
 * Created by cty on 16/10/19.
 */
public class BasePresenterImpl<T extends BaseView, E> implements BasePresenter, RequestCallBack<E> {
    protected T mView;
    protected Subscription mSubscription;
    protected int reqType;
    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        RxBus.cancelSubscription(mSubscription);
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        // TODO?
        mView = (T) view;
    }

    @Override
    public void beforeRequest() {
        mView.showProgress(reqType);
    }

    @Override
    public void success(E data) {
        mView.hideProgress(reqType);

    }

    @Override
    public void onError(String errorMsg) {
        mView.hideProgress(reqType);
        mView.showErrorMsg(reqType,errorMsg);
    }


}