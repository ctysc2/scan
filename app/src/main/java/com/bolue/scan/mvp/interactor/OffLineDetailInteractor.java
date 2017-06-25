package com.bolue.scan.mvp.interactor;

import com.bolue.scan.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/6/23.
 */

public interface OffLineDetailInteractor<T>{
    Subscription getOffLineDetail(RequestCallBack<T> callback, int id);
}
