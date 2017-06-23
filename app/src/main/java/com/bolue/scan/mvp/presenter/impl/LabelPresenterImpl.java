package com.bolue.scan.mvp.presenter.impl;

import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.interactor.impl.LabelInteractorImpl;
import com.bolue.scan.mvp.interactor.impl.ReserveInteractorImpl;
import com.bolue.scan.mvp.presenter.base.BasePresenterImpl;
import com.bolue.scan.mvp.view.LabelView;
import com.codbking.calendar.CalendarView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/6/22.
 */

public class LabelPresenterImpl extends BasePresenterImpl<LabelView,LabelEntity>{

    private LabelInteractorImpl mLabelInteractorImpl;

    @Inject
    public LabelPresenterImpl(LabelInteractorImpl labelInteractorImpl){
        mLabelInteractorImpl = labelInteractorImpl;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getLabelList(int reqType, String type){
        this.reqType = reqType;
        mSubscription = mLabelInteractorImpl.getLabelList(this,type);

    }

    @Override
    public void success(LabelEntity data) {
        super.success(data);
        mView.getLabelViewCompleted(data);
    }

}
