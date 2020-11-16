package com.example.cloneplaystore.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.model.Permission;
import com.example.cloneplaystore.model.ReviewResponse;
import com.example.cloneplaystore.repository.AppRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AppDetailViewModel extends ViewModel {

    private final AppRepository appRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<App>> similarApps = new MutableLiveData<>();
    private final MutableLiveData<List<Permission>> permissions = new MutableLiveData<>();
    private final MutableLiveData<ReviewResponse> reviews= new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public AppDetailViewModel(AppRepository userRepository) {
        this.appRepository = userRepository;
    }

    public MutableLiveData<List<App>> getSimilarApps(String id) {
        loadSimilarApps(id);
        return similarApps;
    }
    public MutableLiveData<ReviewResponse> getReviews(String id) {
        loadReviews(id);
        return reviews;
    }
    public MutableLiveData<List<Permission>> getPermissions(String id) {
        loadPermissions(id);
        return permissions;
    }

    private void loadPermissions(String id) {
        isLoading.setValue(true);
        disposable.add(appRepository.Permissions(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Permission>>() {
                    @Override
                    public void onSuccess(@NonNull List<Permission> appDetails) {
                        isLoading.setValue(false);
                        permissions.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }

    private void loadSimilarApps(String id) {
        isLoading.setValue(true);
        disposable.add(appRepository.SimilarApps(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoading.setValue(false);
                        similarApps.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }
    private void loadReviews(String id) {
        isLoading.setValue(true);
        disposable.add(appRepository.Reviews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ReviewResponse>() {
                    @Override
                    public void onSuccess(@NonNull ReviewResponse appDetails) {
                        isLoading.setValue(false);
                        reviews.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }
}
