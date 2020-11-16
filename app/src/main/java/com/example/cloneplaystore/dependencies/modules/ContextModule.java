package com.example.cloneplaystore.dependencies.modules;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ContextModule {

    public ContextModule(Context context) {
    }

    @Binds
    abstract Context bindContext(Application application);
}
