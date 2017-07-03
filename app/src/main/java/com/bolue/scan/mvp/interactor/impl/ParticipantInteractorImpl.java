package com.bolue.scan.mvp.interactor.impl;

import com.bolue.scan.common.HostType;
import com.bolue.scan.listener.RequestCallBack;
import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.ParticipantEntity;
import com.bolue.scan.mvp.interactor.ParticipantInteractor;
import com.bolue.scan.repository.network.RetrofitManager;
import com.bolue.scan.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/6/26.
 */

public class ParticipantInteractorImpl implements ParticipantInteractor<ParticipantEntity> {

    @Inject
    public ParticipantInteractorImpl(){

    }

    @Override
    public Subscription getParticipantDetail(final RequestCallBack<ParticipantEntity> callback, int user_id, boolean isInvited, int resource_id) {
        return RetrofitManager.getInstance(HostType.PARTICIPANT_DETAIL).getParticipantDetail(user_id,isInvited,resource_id)
                .compose(TransformUtils.<ParticipantEntity>defaultSchedulers())
                .subscribe(new Observer<ParticipantEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ParticipantEntity data) {
                        if(data!=null ){
                            callback.success(data);
                        }
                    }

                });
    }
}
