package com.bolue.scan.mvp.presenter.impl;

import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.ParticipantEntity;
import com.bolue.scan.mvp.interactor.impl.ParticipantInteractorImpl;
import com.bolue.scan.mvp.interactor.impl.ReserveInteractorImpl;
import com.bolue.scan.mvp.presenter.base.BasePresenterImpl;
import com.bolue.scan.mvp.view.ParticipantDetailView;
import com.codbking.calendar.CalendarView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/6/26.
 */

public class ParticipantPresenterImpl extends BasePresenterImpl<ParticipantDetailView,ParticipantEntity>{
    private ParticipantInteractorImpl mParticipantInteractorImpl;

    @Inject
    public ParticipantPresenterImpl(ParticipantInteractorImpl participantInteractorImpl){
        mParticipantInteractorImpl = participantInteractorImpl;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getParticipantDetail(int user_id,boolean is_invited,int resource_id){
        mSubscription = mParticipantInteractorImpl.getParticipantDetail(this,user_id,is_invited,resource_id);
    }

    @Override
    public void success(ParticipantEntity data) {
        super.success(data);
        mView.getParticipantDetailCompleted(data);
    }

}
