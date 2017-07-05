package com.bolue.scan.mvp.presenter.impl;

import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.entity.LoginEntity;
import com.bolue.scan.mvp.entity.LoginRequestEntity;
import com.bolue.scan.mvp.interactor.impl.LabelInteractorImpl;
import com.bolue.scan.mvp.interactor.impl.LoginInteractorImpl;
import com.bolue.scan.mvp.presenter.base.BasePresenterImpl;
import com.bolue.scan.mvp.view.LabelView;
import com.bolue.scan.mvp.view.LoginView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/7/5.
 */

public class LoginPresenterImpl extends BasePresenterImpl<LoginView,LoginEntity> {
    private LoginInteractorImpl mLoginInteractorImpl;

    @Inject
    public LoginPresenterImpl(LoginInteractorImpl loginInteractorImpl){
        mLoginInteractorImpl = loginInteractorImpl;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void doLogin(String userName,String pw){

        LoginRequestEntity request = new LoginRequestEntity(userName,pw);
        mSubscription = mLoginInteractorImpl.doLogin(this,request);

    }

    @Override
    public void success(LoginEntity data) {
        super.success(data);
        mView.doLoginCompleted(data);
    }
}
