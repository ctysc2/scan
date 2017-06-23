package com.bolue.scan.mvp.view.base;

/**
 * Created by cty on 2017/6/5.
 */

public interface BaseView {
    void showProgress(int reqType);

    void hideProgress(int reqType);

    void showErrorMsg(int reqType, String msg);
}
