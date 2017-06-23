package com.bolue.scan.mvp.interactor;

import com.bolue.scan.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/6/22.
 */

public interface LabelInteractor<T> {
    Subscription getLabelList(RequestCallBack<T> callback, String type);
}
