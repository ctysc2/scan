package com.bolue.scan.mvp.presenter.base;

import android.support.annotation.NonNull;

import com.bolue.scan.mvp.view.base.BaseView;


/**
 * Created by cty on 2017/6/5.
 */

public interface BasePresenter {

    void onCreate();

    void attachView(@NonNull BaseView view);

    void onDestroy();
}