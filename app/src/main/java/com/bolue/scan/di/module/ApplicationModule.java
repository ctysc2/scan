package com.bolue.scan.di.module;

import android.content.Context;

import com.bolue.scan.application.App;
import com.bolue.scan.di.scope.ContextLife;
import com.bolue.scan.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }

}
