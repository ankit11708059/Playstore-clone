package com.example.cloneplaystore.dependencies.components;


import com.example.cloneplaystore.AppDetailActivity;
import com.example.cloneplaystore.AppList;
import com.example.cloneplaystore.MainActivity;
import com.example.cloneplaystore.dependencies.modules.ContextModule;
import com.example.cloneplaystore.dependencies.modules.NetworkModule;
import com.example.cloneplaystore.fragments.Categories;
import com.example.cloneplaystore.fragments.EditorsChoice;
import com.example.cloneplaystore.fragments.ForYou;
import com.example.cloneplaystore.fragments.TopCharts;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class})
public interface AppComponent{
    void injectForYou(ForYou forYou);
    void injectDetails(AppDetailActivity appDetails);
    void injectList(AppList appList);
    void injectTopApps(TopCharts topCharts);
    void injectMainActivity(MainActivity mainActivity);
    void injectCategories(Categories categories);
    void injectEditorsChoice(EditorsChoice editorsChoice);

}
