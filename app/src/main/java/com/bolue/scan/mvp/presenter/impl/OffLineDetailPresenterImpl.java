package com.bolue.scan.mvp.presenter.impl;

import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.mvp.interactor.impl.LabelInteractorImpl;
import com.bolue.scan.mvp.interactor.impl.OffLineDetailInteractorImpl;
import com.bolue.scan.mvp.presenter.base.BasePresenterImpl;
import com.bolue.scan.mvp.view.OffLineDetailView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/6/23.
 */

public class OffLineDetailPresenterImpl extends BasePresenterImpl<OffLineDetailView,OffLineLessonEntity> {

    private OffLineDetailInteractorImpl mOffLineDetailInteractorImpl;

    @Inject
    public OffLineDetailPresenterImpl(OffLineDetailInteractorImpl offLineDetailInteractorImpl){
        mOffLineDetailInteractorImpl = offLineDetailInteractorImpl;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getOffLineDetail(int id){
        this.reqType = reqType;
        mSubscription = mOffLineDetailInteractorImpl.getOffLineDetail(this,id);

    }

    @Override
    public void success(OffLineLessonEntity data) {
        super.success(data);
        mView.getOffLineDetailCompleted(data);
    }

}
