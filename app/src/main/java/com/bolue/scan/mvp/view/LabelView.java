package com.bolue.scan.mvp.view;

import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/6/22.
 */

public interface LabelView extends BaseView{
    void getLabelViewCompleted(LabelEntity entity);
}
