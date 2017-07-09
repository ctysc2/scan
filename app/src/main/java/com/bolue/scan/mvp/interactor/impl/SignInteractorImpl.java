package com.bolue.scan.mvp.interactor.impl;

import com.bolue.scan.common.HostType;
import com.bolue.scan.listener.ReLoginEvent;
import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.SignRequestEntity;
import com.bolue.scan.mvp.entity.base.BaseEntity;
import com.bolue.scan.mvp.interactor.SignInteractor;
import com.bolue.scan.repository.network.RetrofitManager;
import com.bolue.scan.utils.RxBus;
import com.bolue.scan.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/7/7.
 */

public class SignInteractorImpl implements SignInteractor<BaseEntity> {

    @Inject
    public SignInteractorImpl(){

    }

    @Override
    public Subscription doSign(final RequestCallBack<BaseEntity> callback, SignRequestEntity data) {
        return RetrofitManager.getInstance(HostType.SIGN).doSign(data)
                .compose(TransformUtils.<BaseEntity>defaultSchedulers())
                .subscribe(new Observer<BaseEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseEntity data) {
                        if(data!=null ){
                            if(data.getUser() != null && data.getUser().isLogined() == false)
                                RxBus.getInstance().post(new ReLoginEvent());
                            callback.success(data);
                        }
                    }

                });
    }
}
