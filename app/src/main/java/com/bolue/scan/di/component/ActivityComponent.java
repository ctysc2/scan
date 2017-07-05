package com.bolue.scan.di.component;

import android.app.Activity;
import android.content.Context;


import com.bolue.scan.di.module.ActivityModule;
import com.bolue.scan.di.scope.ContextLife;
import com.bolue.scan.di.scope.PerActivity;
import com.bolue.scan.mvp.ui.activity.LoginActivity;
import com.bolue.scan.mvp.ui.activity.MainActivity;
import com.bolue.scan.mvp.ui.activity.OffLineSignListActivity;
import com.bolue.scan.mvp.ui.activity.OfflineDetailActivity;
import com.bolue.scan.mvp.ui.activity.ParticipantDetailActivity;
import com.bolue.scan.mvp.ui.activity.SplashActivity;

import dagger.Component;

/**
 * Created by cty on 16/10/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(MainActivity mainActivity);
    void inject(SplashActivity splashActivity);
    void inject(OfflineDetailActivity offlineDetailActivity);
    void inject(ParticipantDetailActivity participantDetailActivity);
    void inject(LoginActivity loginActivity);
    void inject(OffLineSignListActivity offLineSignListActivity);


}