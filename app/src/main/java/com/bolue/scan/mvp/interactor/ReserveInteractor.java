package com.bolue.scan.mvp.interactor;

import com.bolue.scan.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/6/14.
 */

public interface ReserveInteractor<T> {
    Subscription getReserveList(RequestCallBack<T> callback,int year,int day,int city_id);
}
