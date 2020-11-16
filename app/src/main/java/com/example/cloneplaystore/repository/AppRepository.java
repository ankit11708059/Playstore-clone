package com.example.cloneplaystore.repository;

import com.example.cloneplaystore.Service;
import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.model.Permission;
import com.example.cloneplaystore.model.ReviewResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AppRepository {

    private final Service service;

    @Inject
    public AppRepository(Service service) {
        this.service = service;
    }

    public Single<List<App>> Apps(){
        return service.getApps();
    }
    public Single<List<App>> TopCharts(){
        return service.getTopApps();
    }
    public Single<List<App>> Games(){
        return service.getGames();
    }
    public Single<List<App>> Entertainment(){
        return service.getEntertainment();
    }
    public Single<List<App>> Food(){
        return service.getFood();
    }

    public Single<App> AppDetails(String id){
        return service.getAppDetails(id);
    }
    public Single<List<App>> Business(){
        return service.getBusiness();
    }
    public Single<List<App>> Dating(){
        return service.getDating();
    }
    public Single<List<App>> Sports(){
        return service.getSports();
    }

    public Single<ReviewResponse> Reviews(String id){
        return service.getReviews(id);
    }

    public Single<List<App>> SimilarApps(String id){
        return service.getSimilarApps(id);
    }
    public Single<List<App>> Search(String search){
        return service.getSearch(search);
    }
    public Single<List<String>> Suggestion(String suggestion){
        return service.getSuggestions(suggestion);
    }

    public Single<List<App>> Categories(String category){
        return service.getCategories(category);
    }

    public Single<List<Permission>> Permissions(String id){
        return service.getPermissions(id);
    }


}
