package com.example.cloneplaystore.fragments;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneplaystore.AppList;
import com.example.cloneplaystore.BaseApplication;
import com.example.cloneplaystore.R;
import com.example.cloneplaystore.adapters.AppAdapter;
import com.example.cloneplaystore.adapters.AppClickListener;
import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.viewmodel.AppViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ForYou extends Fragment implements View.OnClickListener {
    public RecyclerView recyclerView, gameRecycler, entertainmentView
            , foodRecycler;
    public AppAdapter appAdapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    public static List<App> appDetailsList=new ArrayList<>();
    public static List<App> gamesDetailsList=new ArrayList<>();
    public static List<App> entertainmentDetailsList=new ArrayList<>();
    public static List<App> foodDetailsList=new ArrayList<>();
    private int loadingCount =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.foryou_fragment,container,false);
        recyclerView=view.findViewById(R.id.default_recyclerView);
        gameRecycler =view.findViewById(R.id.games_recyclerView);
        entertainmentView =view.findViewById(R.id.entertainment_recyclerView);
        foodRecycler =view.findViewById(R.id.food_recyclerView);
        view.findViewById(R.id.tv_recommended_arrow).setOnClickListener(this);
        view.findViewById(R.id.tv_games_arrow).setOnClickListener(this);
        view.findViewById(R.id.tv_entertainment_arrow).setOnClickListener(this);
        view.findViewById(R.id.tv_foodAndDrink).setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseApplication.getAppComponent().injectForYou(this);
        AppViewModel userViewModel = ViewModelProviders.of(this, viewModelFactory).get(AppViewModel.class);
        checkLoading(view);
        userViewModel.getDefaultApps().observe(getViewLifecycleOwner(), appDetails -> {
            appDetailsList=appDetails;
            loadingCount++;
            checkLoading(view);
            appAdapter=new AppAdapter(getActivity(),appDetailsList,(AppClickListener)getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            recyclerView.setAdapter(appAdapter);


        });
        userViewModel.getGamesLiveData().observe(getViewLifecycleOwner(), appDetails -> {
            gamesDetailsList=appDetails;
            loadingCount++;
            checkLoading(view);
            appAdapter=new AppAdapter(getActivity(),gamesDetailsList, (AppClickListener) getActivity());
            gameRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            gameRecycler.setAdapter(appAdapter);

        });
        userViewModel.getFoodLiveData().observe(getViewLifecycleOwner(), appDetails -> {
            foodDetailsList=appDetails;
            loadingCount++;
            checkLoading(view);
            appAdapter=new AppAdapter(getActivity(),foodDetailsList, (AppClickListener) getActivity());
            foodRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            foodRecycler.setAdapter(appAdapter);


        });
        userViewModel.getEntertainmentLiveData().observe(getViewLifecycleOwner(), appDetails -> {
            entertainmentDetailsList=appDetails;
            loadingCount++;
            checkLoading(view);
            appAdapter=new AppAdapter(getActivity(),entertainmentDetailsList, (AppClickListener) getActivity());
            entertainmentView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            entertainmentView.setAdapter(appAdapter);

        });
    }

    private void checkLoading(View view) {
        if(loadingCount <4){
            view.findViewById(R.id.forYouScroll).setVisibility(View.GONE);
            view.findViewById(R.id.forYouProgress).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.forYouScroll).setVisibility(View.VISIBLE);
            view.findViewById(R.id.forYouProgress).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        String category=null;
        List<App> currentList=new ArrayList<>();
     if(v.getId()==R.id.tv_recommended_arrow)
     {
         currentList=appDetailsList;
         category="Recommended For You";
     }
     else if(v.getId()==R.id.tv_entertainment_arrow)
        {
            currentList=entertainmentDetailsList;
            category="Entertainment";
        }
     else if(v.getId()==R.id.tv_games_arrow)
        {
            currentList=gamesDetailsList;
            category="Games";
        }
     else if(v.getId()==R.id.tv_foodAndDrink)
        {
            currentList=foodDetailsList;
            category="Food and Drink";
        }

        Bundle args =new Bundle();
        args.putParcelableArrayList(AppList.APP_LIST, (ArrayList<? extends Parcelable>) currentList);
        Intent intent=new Intent(getContext(), AppList.class);
        intent.putExtra(AppList.TYPE, AppList.APP_LIST);
         intent.putExtra(AppList.CATEGORY,category);
        intent.putExtras(args);
        startActivity(intent);
    }
}
