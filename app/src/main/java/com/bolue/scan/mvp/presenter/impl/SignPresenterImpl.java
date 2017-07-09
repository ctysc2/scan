package com.bolue.scan.mvp.presenter.impl;

import com.bolue.scan.mvp.entity.ParticipantEntity;
import com.bolue.scan.mvp.entity.SignRequestEntity;
import com.bolue.scan.mvp.entity.base.BaseEntity;
import com.bolue.scan.mvp.interactor.impl.ParticipantInteractorImpl;
import com.bolue.scan.mvp.interactor.impl.SignInteractorImpl;
import com.bolue.scan.mvp.presenter.base.BasePresenterImpl;
import com.bolue.scan.mvp.view.DoSignView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/7/7.
 */

public class SignPresenterImpl extends BasePresenterImpl<DoSignView,BaseEntity>{

    private SignInteractorImpl mSignPresenterImpl;

    @Inject
    public SignPresenterImpl(SignInteractorImpl signInteractorImpl){
        mSignPresenterImpl = signInteractorImpl;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setReqType(int type){

        reqType = type;

    }

    public void doSign(SignRequestEntity entity){
        mSubscription = mSignPresenterImpl.doSign(this,entity);
    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.doSignCompleted(data);
    }


}
