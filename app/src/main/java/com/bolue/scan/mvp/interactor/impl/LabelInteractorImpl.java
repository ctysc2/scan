package com.bolue.scan.mvp.interactor.impl;

import com.bolue.scan.common.HostType;
import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.interactor.LabelInteractor;
import com.bolue.scan.repository.network.RetrofitManager;
import com.bolue.scan.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/6/22.
 */

public class LabelInteractorImpl implements LabelInteractor<LabelEntity> {

    @Inject
    public LabelInteractorImpl(){

    }

    @Override
    public Subscription getLabelList(final RequestCallBack<LabelEntity> callback, String type) {
        return RetrofitManager.getInstance(HostType.LABEL).getLabelList(type)
                .compose(TransformUtils.<LabelEntity>defaultSchedulers())
                .subscribe(new Observer<LabelEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(LabelEntity data) {
                        if(data!=null ){
                            callback.success(data);
                        }
                    }

                });
    }
}
