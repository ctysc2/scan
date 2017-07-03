package com.bolue.scan.mvp.interactor;

import com.bolue.scan.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/6/26.
 */

public interface ParticipantInteractor<T> {
    Subscription getParticipantDetail(RequestCallBack<T> callback, int user_id,boolean isInvited, int resource_id);
}
