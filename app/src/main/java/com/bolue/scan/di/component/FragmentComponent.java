package com.bolue.scan.di.component;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;


import com.bolue.scan.di.module.FragmentModule;
import com.bolue.scan.di.scope.ContextLife;
import com.bolue.scan.di.scope.PerFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    FragmentActivity getFragmentActivity();

}
