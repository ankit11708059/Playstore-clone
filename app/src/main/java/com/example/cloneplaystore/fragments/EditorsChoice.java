package com.example.cloneplaystore.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class EditorsChoice extends Fragment implements View.OnClickListener {
    String category;
    public View linearLayout;
    private boolean isLoading;
    private String currentCategory;
    private List<App> currentList=new ArrayList<>();
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editorschoice_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.editorsChoice1).setOnClickListener(this);
        view.findViewById(R.id.editorsChoice2).setOnClickListener(this);
        view.findViewById(R.id.editorsChoice3).setOnClickListener(this);
        view.findViewById(R.id.editorsChoice4).setOnClickListener(this);
        linearLayout=view.findViewById(R.id.editorsChoiceLayout);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editorsChoice1:
                category="FOOD_AND_DRINK";
                break;
            case R.id.editorsChoice2:
                category="VIDEO_PLAYERS";
                break;
            case R.id.editorsChoice3:
                category="PHOTOGRAPHY";
                break;
            default:
                category="PRODUCTIVITY";
                break;
        }
        isLoading=true;
        currentCategory=category;
        setUpEditorsChoice(category);
    }

    private void setUpEditorsChoice(String category) {
        linearLayout.setVisibility(View.GONE);
        Objects.requireNonNull(getView()).findViewById(R.id.editorsChoiceProgress).setVisibility(View.VISIBLE);
        BaseApplication.getAppComponent().injectEditorsChoice(this);
        AppViewModel userViewModel = new ViewModelProvider(this, viewModelFactory).get(AppViewModel.class);
        userViewModel.getCategories(category).observe(this, apps -> {
            if(!currentList.equals(apps) ) {
                currentList=apps;
                isLoading = false;
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(AppList.APP_LIST, (ArrayList<? extends Parcelable>) apps);
                Intent intent = new Intent(getContext(), AppList.class);
                intent.putExtra(AppList.TYPE, AppList.EDITOR);
                intent.putExtra(AppList.CATEGORY, currentCategory);
                intent.putExtras(bundle);
                startActivity(intent);
                checkLoading();
            }
        });
    }

    private void checkLoading() {
        if(!isLoading){
            linearLayout.setVisibility(View.VISIBLE);
            Objects.requireNonNull(getView()).findViewById(R.id.editorsChoiceProgress).setVisibility(View.GONE);
        }
    }
}
