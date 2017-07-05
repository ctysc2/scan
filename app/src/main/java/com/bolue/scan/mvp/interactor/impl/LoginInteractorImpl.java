package com.bolue.scan.mvp.interactor.impl;

import com.bolue.scan.common.HostType;
import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.LoginEntity;
import com.bolue.scan.mvp.entity.LoginRequestEntity;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.mvp.interactor.LoginInteractor;
import com.bolue.scan.repository.network.RetrofitManager;
import com.bolue.scan.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/7/5.
 */

public class LoginInteractorImpl implements LoginInteractor<LoginEntity> {

    @Inject
    public LoginInteractorImpl(){

    }

    @Override
    public Subscription doLogin(final RequestCallBack<LoginEntity> callback, LoginRequestEntity request) {
        return RetrofitManager.getInstance(HostType.LOGIN).doLogin(request)
                .compose(TransformUtils.<LoginEntity>defaultSchedulers())
                .subscribe(new Observer<LoginEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginEntity data) {
                        if(data!=null ){
                            callback.success(data);
                        }
                    }

                });
    }
}
