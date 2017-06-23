package com.bolue.scan.di.component;


import android.content.Context;

import com.bolue.scan.di.module.ApplicationModule;
import com.bolue.scan.di.scope.ContextLife;
import com.bolue.scan.di.scope.PerApp;

import dagger.Component;


@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();

}

