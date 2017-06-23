package com.bolue.scan.mvp.interactor;


import com.bolue.scan.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/6/6.
 */

public interface IndexInteractor<T>{
    Subscription getIndex(RequestCallBack<T> callback);
}
