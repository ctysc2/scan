package com.bolue.scan.mvp.presenter.impl;

import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.IndexEntity;
import com.bolue.scan.mvp.interactor.impl.IndexInteractorImpl;
import com.bolue.scan.mvp.interactor.impl.ReserveInteractorImpl;
import com.bolue.scan.mvp.presenter.base.BasePresenterImpl;
import com.bolue.scan.mvp.view.ReserveView;
import com.codbking.calendar.CalendarView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/6/14.
 */

public class ReservePresenterImpl extends BasePresenterImpl<ReserveView,CalendarEntity> {
    private ReserveInteractorImpl mReserveInteractorImpl;
    private CalendarView updateView;
    @Inject
    public ReservePresenterImpl(ReserveInteractorImpl reserveInteractorImpl){
        mReserveInteractorImpl = reserveInteractorImpl;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getReserveList(CalendarView updateView,int year,int day,int city_id){
        mSubscription = mReserveInteractorImpl.getReserveList(this,year,day,city_id);
        this.updateView = updateView;
    }

    @Override
    public void success(CalendarEntity data) {
        super.success(data);
        mView.getReserveListCompleted(updateView,data);
    }
}
