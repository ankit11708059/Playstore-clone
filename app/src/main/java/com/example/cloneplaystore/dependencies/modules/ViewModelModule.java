package com.example.cloneplaystore.dependencies.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cloneplaystore.viewmodel.AppDetailViewModel;
import com.example.cloneplaystore.viewmodel.AppViewModel;
import com.example.cloneplaystore.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AppViewModel.class)
    abstract ViewModel bindViewModel(AppViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AppDetailViewModel.class)
    abstract ViewModel bindDetailViewModel(AppDetailViewModel userViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindFactory(ViewModelFactory factory);

}
