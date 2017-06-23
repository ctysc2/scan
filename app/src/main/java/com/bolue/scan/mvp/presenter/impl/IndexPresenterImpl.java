package com.bolue.scan.mvp.presenter.impl;


import com.bolue.scan.mvp.entity.IndexEntity;
import com.bolue.scan.mvp.interactor.impl.IndexInteractorImpl;
import com.bolue.scan.mvp.presenter.base.BasePresenterImpl;
import com.bolue.scan.mvp.view.IndexView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/6/6.
 */

public class IndexPresenterImpl extends BasePresenterImpl<IndexView,IndexEntity> {

    private IndexInteractorImpl mIndexInteractorImpl;

    @Inject
    public IndexPresenterImpl(IndexInteractorImpl indexInteractorImpl){
        mIndexInteractorImpl = indexInteractorImpl;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getIndex(){
        mSubscription = mIndexInteractorImpl.getIndex(this);

    }

    @Override
    public void success(IndexEntity data) {
        super.success(data);
        mView.getIndexCompleted(data);
    }
}
