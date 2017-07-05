package com.bolue.scan.mvp.interactor;

import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.LoginRequestEntity;

import rx.Subscription;

/**
 * Created by cty on 2017/7/5.
 */

public interface LoginInteractor<T> {
    Subscription doLogin(RequestCallBack<T> callback, LoginRequestEntity request);
}
