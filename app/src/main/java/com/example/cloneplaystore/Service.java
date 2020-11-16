package com.example.cloneplaystore;


import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.model.Permission;
import com.example.cloneplaystore.model.ReviewResponse;

import java.util.List;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Service {
    @GET(" ")
    Single<List<App>> getApps();

    @GET("apps/details")
    Single<App> getAppDetails(@Query("id") String id);
    @GET("apps/similar")
    Single<List<App>> getSimilarApps(@Query("id") String id);
    @GET("apps/reviews")
    Single<ReviewResponse> getReviews(@Query("id") String id);

    @GET("?collection=topselling_free&category=COMICS")
    Single<List<App>> getTopApps();
    @GET("?category=GAME")
    Single<List<App>> getGames();
    @GET("?category=ENTERTAINMENT")
    Single<List<App>> getEntertainment();
    @GET("?category=FOOD_AND_DRINK")
    Single<List<App>> getFood();

    @GET("?collection=topselling_free&category=BUSINESS")
    Single<List<App>> getBusiness();
    @GET("?collection=topselling_free&category=DATING")
    Single<List<App>> getDating();
    @GET("?collection=topselling_free&category=SPORTS")
    Single<List<App>> getSports();
    @GET
    Single<List<App>> getSearch(@Url String url);

    @GET
    Single<List<String>> getSuggestions(@Url String url);

    @GET(" ")
    Single<List<App>> getCategories(@Query("category") String category);

    @GET("apps/permissions")
    Single<List<Permission>> getPermissions(@Query("id") String id);


}
