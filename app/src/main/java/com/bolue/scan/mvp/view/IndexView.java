package com.bolue.scan.mvp.view;


import com.bolue.scan.mvp.entity.IndexEntity;
import com.bolue.scan.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/6/6.
 */

public interface IndexView extends BaseView {
    void getIndexCompleted(IndexEntity entity);
}
