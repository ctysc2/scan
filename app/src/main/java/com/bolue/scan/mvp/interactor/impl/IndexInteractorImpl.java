package com.bolue.scan.mvp.interactor.impl;


import com.bolue.scan.common.HostType;
import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.IndexEntity;
import com.bolue.scan.mvp.interactor.IndexInteractor;
import com.bolue.scan.repository.network.RetrofitManager;
import com.bolue.scan.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/6/6.
 */

public class IndexInteractorImpl implements IndexInteractor<IndexEntity> {

    @Inject
    public IndexInteractorImpl(){

    }

    @Override
    public Subscription getIndex(final RequestCallBack<IndexEntity> callback) {
        return RetrofitManager.getInstance(HostType.INDEX).getIndex()
                .compose(TransformUtils.<IndexEntity>defaultSchedulers())
                .subscribe(new Observer<IndexEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(IndexEntity data) {
                        if(data!=null ){
                            callback.success(data);
                        }
                    }

                });
    }

}
