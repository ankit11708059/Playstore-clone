package com.example.cloneplaystore.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cloneplaystore.AppList;
import com.example.cloneplaystore.BaseApplication;
import com.example.cloneplaystore.R;
import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.viewmodel.AppViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class Categories extends Fragment implements View.OnClickListener {
    private String category;
    private boolean isLoading;
    private List<App> currentList=new ArrayList<>();
    private String currentCategory;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.categories_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.art_design).setOnClickListener(this);
        view.findViewById(R.id.auto_vehicles).setOnClickListener(this);
        view.findViewById(R.id.beauty).setOnClickListener(this);
        view.findViewById(R.id.books_reference).setOnClickListener(this);
        view.findViewById(R.id.business).setOnClickListener(this);
        view.findViewById(R.id.comics).setOnClickListener(this);
        view.findViewById(R.id.communication).setOnClickListener(this);
        view.findViewById(R.id.dating).setOnClickListener(this);
        view.findViewById(R.id.education).setOnClickListener(this);
        view.findViewById(R.id.entertainment).setOnClickListener(this);
        view.findViewById(R.id.events).setOnClickListener(this);
        view.findViewById(R.id.food_drink).setOnClickListener(this);
        view.findViewById(R.id.finance).setOnClickListener(this);
        view.findViewById(R.id.games).setOnClickListener(this);
        view.findViewById(R.id.health_fitness).setOnClickListener(this);
        view.findViewById(R.id.sports).setOnClickListener(this);
        view.findViewById(R.id.social).setOnClickListener(this);
        view.findViewById(R.id.maps).setOnClickListener(this);
        view.findViewById(R.id.photography).setOnClickListener(this);
        view.findViewById(R.id.music).setOnClickListener(this);
        view.findViewById(R.id.shopping).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView textView =(TextView)view;
        switch (textView.getText().toString()) {
            case "Art and Design":
                category = "ART_AND_DESIGN";
                break;
            case "Auto and Vehicles":
                category = "AUTO_AND_VEHICLES";
                break;
            case "Beauty":
                category = "BEAUTY";
                break;
            case "Books and Reference":
                category = "BOOKS_AND_REFERENCE";
                break;
            case "Business":
                category = "BUSINESS";
                break;
            case "Comics":
                category = "COMICS";
                break;
            case "Communication":
                category = "COMMUNICATION";
                break;
            case "Dating":
                category = "DATING";
                break;
            case "Education":
                category = "EDUCATION";
                break;
            case "Events":
                category = "EVENTS";
                break;
            case "Finance":
                category = "FINANCE";
                break;
            case "Food and Drink":
                category = "FOOD_AND_DRINK";
                break;
            case "Health and Fitness":
                category = "HEALTH_AND_FITNESS";
                break;
            case "Games":
                category = "GAME";
                break;
            case "Maps & Navigation":
                category = "MAPS_AND_NAVIGATION";
                break;
            case "Music & Audio":
                category = "MUSIC_AND_AUDIO";
                break;
            case "Photography":
                category = "PHOTOGRAPHY";
                break;
            case "Shopping":
                category = "SHOPPING";
                break;
            case "Social":
                category = "SOCIAL";
                break;
            case "Sports":
                category = "SPORTS";
                break;
            case "Entertainment":
                category = "ENTERTAINMENT";
                break;

        }
        isLoading=true;
        currentCategory=category;
        setUpCategories(category);
    }

    private void setUpCategories(String category) {
        Objects.requireNonNull(getView()).findViewById(R.id.categoriesList).setVisibility(View.GONE);
        getView().findViewById(R.id.categoriesProgress).setVisibility(View.VISIBLE);
        BaseApplication.getAppComponent().injectCategories(this);
        AppViewModel userViewModel = new ViewModelProvider(this, viewModelFactory).get(AppViewModel.class);
        userViewModel.getCategories(category).observe(this, apps -> {
            if(!currentList.equals(apps) ) {
                currentList=apps;
                isLoading = false;
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(AppList.APP_LIST, (ArrayList<? extends Parcelable>) apps);
                Intent intent = new Intent(getContext(), AppList.class);
                intent.putExtra(AppList.TYPE, AppList.APP_LIST);
                intent.putExtra(AppList.CATEGORY, currentCategory);
                intent.putExtras(bundle);
                startActivity(intent);
                checkLoading();
            }
        });
    }

    private void checkLoading() {
        if(!isLoading){
            Objects.requireNonNull(getView()).findViewById(R.id.categoriesList).setVisibility(View.VISIBLE);
            Objects.requireNonNull(getView()).findViewById(R.id.categoriesProgress).setVisibility(View.GONE);
        }
    }

}
