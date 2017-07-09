package com.bolue.scan.mvp.view;

import com.bolue.scan.mvp.entity.base.BaseEntity;
import com.bolue.scan.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/7/7.
 */

public interface DoSignView extends BaseView{
    void doSignCompleted(BaseEntity entity);
}
