package com.bolue.scan.mvp.view;

import com.bolue.scan.mvp.entity.LoginEntity;
import com.bolue.scan.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/7/5.
 */

public interface LoginView extends BaseView{
    void doLoginCompleted(LoginEntity entity);
}
