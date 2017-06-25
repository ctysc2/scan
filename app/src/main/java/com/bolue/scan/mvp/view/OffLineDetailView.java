package com.bolue.scan.mvp.view;

import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/6/23.
 */

public interface OffLineDetailView extends BaseView{
    void getOffLineDetailCompleted(OffLineLessonEntity entity);
}
