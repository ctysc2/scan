package com.bolue.scan.mvp.interactor.impl;


import com.bolue.scan.common.HostType;
import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.CarouselEntity;
import com.bolue.scan.mvp.interactor.CarouselInteractor;
import com.bolue.scan.repository.network.RetrofitManager;
import com.bolue.scan.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/6/7.
 */

public class CarouselInteractorImpl implements CarouselInteractor<CarouselEntity> {

    @Inject
    public CarouselInteractorImpl(){

    }

    @Override
    public Subscription getCarousel(final RequestCallBack<CarouselEntity> callback) {
        return RetrofitManager.getInstance(HostType.CAROUSEL).getCarousel()
                .compose(TransformUtils.<CarouselEntity>defaultSchedulers())
                .subscribe(new Observer<CarouselEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(CarouselEntity data) {
                        if(data!=null ){
                            callback.success(data);
                        }
                    }

                });
    }
}
