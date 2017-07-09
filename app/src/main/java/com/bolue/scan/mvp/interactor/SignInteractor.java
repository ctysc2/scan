package com.bolue.scan.mvp.interactor;

import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.SignRequestEntity;

import rx.Subscription;

/**
 * Created by cty on 2017/7/7.
 */

public interface SignInteractor<T> {
    Subscription doSign(RequestCallBack<T> callback, SignRequestEntity data);
}
