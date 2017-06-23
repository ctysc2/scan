package com.bolue.scan.di.component;


import android.content.Context;


import com.bolue.scan.di.module.ServiceModule;
import com.bolue.scan.di.scope.ContextLife;
import com.bolue.scan.di.scope.PerService;

import dagger.Component;


@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
