package com.bolue.scan.mvp.interactor.impl;

import com.bolue.scan.common.HostType;
import com.bolue.scan.listener.ReLoginEvent;
import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.mvp.interactor.OffLineDetailInteractor;
import com.bolue.scan.repository.network.RetrofitManager;
import com.bolue.scan.utils.RxBus;
import com.bolue.scan.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/6/23.
 */

public class OffLineDetailInteractorImpl implements OffLineDetailInteractor<OffLineLessonEntity> {

    @Inject
    public OffLineDetailInteractorImpl(){

    }

    @Override
    public Subscription getOffLineDetail(final RequestCallBack<OffLineLessonEntity> callback, int id) {
        return RetrofitManager.getInstance(HostType.OFFLINE_DETAIL).getOffLineDetail(id)
                .compose(TransformUtils.<OffLineLessonEntity>defaultSchedulers())
                .subscribe(new Observer<OffLineLessonEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(OffLineLessonEntity data) {
                        if(data!=null ){
                            if(data.getUser() != null && data.getUser().isLogined() == false)
                                RxBus.getInstance().post(new ReLoginEvent());
                            callback.success(data);
                        }
                    }

                });
    }
}
