package com.bolue.scan.mvp.interactor.impl;

import com.bolue.scan.common.HostType;
import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.IndexEntity;
import com.bolue.scan.mvp.interactor.ReserveInteractor;
import com.bolue.scan.repository.network.RetrofitManager;
import com.bolue.scan.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/6/14.
 */

public class ReserveInteractorImpl implements ReserveInteractor<CalendarEntity>{

    @Inject
    public ReserveInteractorImpl(){

    }

    @Override
    public Subscription getReserveList(final RequestCallBack<CalendarEntity> callback,int year,int day,int city_id) {
        return RetrofitManager.getInstance(HostType.RESERVE).getReserveList(year,day,city_id)
                .compose(TransformUtils.<CalendarEntity>defaultSchedulers())
                .subscribe(new Observer<CalendarEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(CalendarEntity data) {
                        if(data!=null ){
                            callback.success(data);
                        }
                    }

                });
    }
}
