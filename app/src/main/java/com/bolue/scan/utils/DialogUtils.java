package com.bolue.scan.utils;

/**
 * Created by cty on 2016/12/12.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;




import com.bolue.scan.R;
import com.bolue.scan.listener.AlertDialogListener;



public class DialogUtils {

    private Context mContext;
    private Dialog mDialog;

    public static  DialogUtils create(Context context){

        return new DialogUtils(context);


    }
    private DialogUtils(Context context){
        mContext = context;
    }
    public boolean isShowing(){
        return mDialog.isShowing();

    }
    public void show(String title){
        showLoadingDialog(title);
    }
    public void show(AlertDialogListener listener, String title, String content){
        showAlertDialog(listener,title,content);
    }
    public void show(AlertDialogListener listener, String title, String content,String nagitive,String positive){
        showAlertDialog(listener,title,content,nagitive,positive);
    }
    //加载中弹框
    private void showLoadingDialog(String title){

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contentView = inflater.inflate(R.layout.common_loading, null);
        TextView tv = (TextView)contentView.findViewById(R.id.tv_loading);
        tv.setText(title);
        mDialog = new AlertDialog.Builder(
                new ContextThemeWrapper(mContext, R.style.DialogStyle)).create();
        mDialog.show();
        mDialog.setCancelable(false);
        mDialog.setContentView(contentView);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backGroundFloatOff();
            }
        });
        Window window = mDialog.getWindow();
        //window.setBackgroundDrawableResource(R.color.color_dialog_loading_float); //背景
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = (int)(DimenUtil.getScreenWidth()*0.5);
        window.setAttributes(params);
        backGroundFloatOn();

    }
    public void dismiss(){
        if(mDialog!=null)
            mDialog.dismiss();


    }

    //两行提示框
    private  void showAlertDialog(final AlertDialogListener listenerString,String hint1,String hint2){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contentView = inflater.inflate(R.layout.alert_dialog, null);
        ((RelativeLayout)contentView.findViewById(R.id.rl_cancle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerString.onCancel();
            }
        });
        ((RelativeLayout)contentView.findViewById(R.id.rl_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerString.onConFirm();
            }
        });
        TextView mhint1 = (TextView)contentView.findViewById(R.id.Tv_title);
        TextView mhint2 = (TextView)contentView.findViewById(R.id.Tv_content);
        mhint1.setText(hint1);
        mhint2.setText(hint2);
        //mDialog = new AlertDialog.Builder(mContext).create();

        mDialog = new AlertDialog.Builder(
                new ContextThemeWrapper(mContext, R.style.DialogStyle)).create();
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setContentView(contentView);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backGroundFloatOff();
            }
        });
        Window window = mDialog.getWindow();
        //window.setBackgroundDrawableResource(R.color.color_dialog_loading_float);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int)(DimenUtil.getScreenWidth()*0.8);
        //window.setGravity(Gravity.TOP);
        window.setAttributes(params);
        backGroundFloatOn();

    }

    //两行提示框自定义文言
    private  void showAlertDialog(final AlertDialogListener listenerString,String hint1,String hint2,String nagitive,String positive){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contentView = inflater.inflate(R.layout.alert_dialog, null);

        ((TextView)(contentView.findViewById(R.id.tv_cancle))).setText(nagitive);
        ((TextView)(contentView.findViewById(R.id.tv_confirm))).setText(positive);

        ((RelativeLayout)contentView.findViewById(R.id.rl_cancle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerString.onCancel();
            }
        });
        ((RelativeLayout)contentView.findViewById(R.id.rl_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerString.onConFirm();
            }
        });
        TextView mhint1 = (TextView)contentView.findViewById(R.id.Tv_title);
        TextView mhint2 = (TextView)contentView.findViewById(R.id.Tv_content);
        mhint1.setText(hint1);
        mhint2.setText(hint2);
        //mDialog = new AlertDialog.Builder(mContext).create();

        mDialog = new AlertDialog.Builder(
                new ContextThemeWrapper(mContext, R.style.DialogStyle)).create();
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setContentView(contentView);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backGroundFloatOff();
            }
        });
        Window window = mDialog.getWindow();
        //window.setBackgroundDrawableResource(R.color.color_dialog_loading_float);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int)(DimenUtil.getScreenWidth()*0.8);
        //window.setGravity(Gravity.TOP);
        window.setAttributes(params);
        backGroundFloatOn();

    }
    private void  backGroundFloatOn(){
        WindowManager.LayoutParams params2 = ((Activity)mContext).getWindow().getAttributes();
        params2.alpha = 0.8f;
        ((Activity)mContext).getWindow().setAttributes(params2);
    }
    private void  backGroundFloatOff(){
        WindowManager.LayoutParams params2 = ((Activity)mContext).getWindow().getAttributes();
        params2.alpha = 1.0f;
        ((Activity)mContext).getWindow().setAttributes(params2);
    }
}
