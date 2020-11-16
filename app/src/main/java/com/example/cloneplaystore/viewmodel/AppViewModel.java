package com.example.cloneplaystore.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.repository.AppRepository;

import java.util.List;

import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AppViewModel extends ViewModel {
    private final AppRepository appRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<App>> defaultApps = new MutableLiveData<>();
    private final MutableLiveData<App> screenshots=new MutableLiveData<>();
    private final MutableLiveData<List<App>> topChartsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<App>> gamesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<App>> entertainmentLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<App>> foodLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingForYou = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingCategories = new MutableLiveData<>();

    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final MutableLiveData<List<App>> businessLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<App>> datingLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<App>> sportsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<App>> searchLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> suggestLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<App>> categories = new MutableLiveData<>();


    @Inject
    public AppViewModel(AppRepository userRepository) {
        this.appRepository = userRepository;
    }

    public MutableLiveData<List<App>> getDefaultApps() {
        loadData();
        return defaultApps;
    }
    public MutableLiveData<List<App>> getFoodLiveData() {
        loadFood();
        return foodLiveData;
    }
    public MutableLiveData<List<App>> getBusinessLiveData() {
        loadBusiness();
        return businessLiveData;
    }
    public MutableLiveData<List<App>> getCategories(String category) {
        loadCategories(category);
        return categories;
    }

    private void loadCategories(String category) {
        isLoadingCategories.setValue(true);
        disposable.add(appRepository.Categories(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoadingCategories.setValue(false);
                        categories.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }

    private void loadBusiness() {
        isLoading.setValue(true);
        disposable.add(appRepository.Business()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoading.setValue(false);
                        businessLiveData.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));

    }

    public MutableLiveData<List<App>> getDatingLiveData() {
        loadDating();
        return datingLiveData;
    }

    private void loadDating() {
        isLoading.setValue(true);
        disposable.add(appRepository.Dating()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoading.setValue(false);
                        datingLiveData.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));

    }

    public MutableLiveData<List<App>> getSportsLiveData() {
        loadSports();
        return sportsLiveData;
    }

    private void loadSports() {
        isLoading.setValue(true);
        disposable.add(appRepository.Sports()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoading.setValue(false);
                        sportsLiveData.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }


    private void loadFood() {
        isLoadingForYou.setValue(true);
        disposable.add(appRepository.Food()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoadingForYou.setValue(false);
                        foodLiveData.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }

    public MutableLiveData<List<App>> getGamesLiveData() {
        loadGames();
        return gamesLiveData;
    }
    public MutableLiveData<List<App>> getEntertainmentLiveData() {
        loadEntertainment();
        return entertainmentLiveData;
    }

    private void loadEntertainment() {
        isLoadingForYou.setValue(true);
        disposable.add(appRepository.Entertainment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoadingForYou.setValue(false);
                        entertainmentLiveData.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }

    private void loadGames() {
        isLoadingForYou.setValue(true);
        disposable.add(appRepository.Games()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoadingForYou.setValue(false);
                        gamesLiveData.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }

    public MutableLiveData<List<App>> getTopChartsLiveData(){
        loadTopCharts();
        return topChartsLiveData;
    }

    private void loadTopCharts() {
        isLoading.setValue(true);
        disposable.add(appRepository.TopCharts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoading.setValue(false);
                        topChartsLiveData.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }

    public MutableLiveData<App> getScreenshots(String id) {
        loadScreenshots(id);
        return screenshots;
    }

    private void loadScreenshots(String id) {
        isLoading.setValue(true);
        disposable.add(appRepository.AppDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<App>() {
                    @Override
                    public void onSuccess(@NonNull App appDetails) {
                        isLoading.setValue(false);
                        screenshots.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("error","screenshots",e);
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }

    private void loadData() {
        isLoadingForYou.setValue(true);
        disposable.add(appRepository.Apps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> appDetails) {
                        isLoadingForYou.setValue(false);
                        defaultApps.setValue(appDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }
    public MutableLiveData<List<App>> getSearchLiveData(String query) {
        loadSearch(query);
        return searchLiveData;
    }
    public MutableLiveData<List<String>> getSuggestionLiveData(String suggestion) {
        loadSuggestion(suggestion);
        return suggestLiveData;
    }

    private void loadSuggestion(String suggestion) {
        isLoading.setValue(true);
        disposable.add(appRepository.Suggestion(suggestion)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<String>>() {
                    @Override
                    public void onSuccess(@NonNull List<String> apps) {
                        isLoading.setValue(false);
                        suggestLiveData.setValue(apps);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.getMessage());
                        isLoading.setValue(false);
                    }
                }));
    }

    private void loadSearch(String query) {
        isLoading.setValue(true);
        disposable.add(appRepository.Search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<App>>() {
                    @Override
                    public void onSuccess(@NonNull List<App> apps) {
                        isLoading.setValue(false);
                        searchLiveData.setValue(apps);
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
