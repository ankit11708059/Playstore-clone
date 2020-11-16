package com.example.cloneplaystore;

import android.app.Application;

import com.example.cloneplaystore.dependencies.components.AppComponent;
import com.example.cloneplaystore.dependencies.components.DaggerAppComponent;


public class BaseApplication extends Application {

    private static AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=DaggerAppComponent.create();
    }

    public static AppComponent getAppComponent() {

        return appComponent;
    }
}
