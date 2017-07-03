package com.bolue.scan.mvp.view;

import com.bolue.scan.mvp.entity.ParticipantEntity;
import com.bolue.scan.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/6/26.
 */

public interface ParticipantDetailView extends BaseView{
    void getParticipantDetailCompleted(ParticipantEntity entity);
}
