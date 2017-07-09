package com.bolue.scan.mvp.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.bolue.scan.application.App;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.utils.SystemTool;

/**
 * Created by cty on 2017/7/8.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = SystemTool.getNetWorkState(context);
            // 接口回调传过去状态的类型
            ((BaseActivity)App.getActivity()).onNetChange(netWorkState);
        }
    }
}
