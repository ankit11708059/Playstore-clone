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
import com.example.cloneplaystore.adapters.topChartAdapter;
import com.example.cloneplaystore.adapters.AppClickListener;
import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.viewmodel.AppViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TopCharts extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView, businessRecyclerView, sportsRecycler, datingRecycler;
    public topChartAdapter topChartAdapters;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    public static List<App> appDetailsList=new ArrayList<>();
    public static List<App> datingList =new ArrayList<>();
    public static List<App> businessList =new ArrayList<>();
    public static List<App> sportsList =new ArrayList<>();
    private int loadingCount=0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseApplication.getAppComponent().injectTopApps(this);
        AppViewModel userViewModel = ViewModelProviders.of(this, viewModelFactory).get(AppViewModel.class);
        checkLoading(view);

        userViewModel.getTopChartsLiveData().observe(getViewLifecycleOwner(), appDetails -> {
            appDetailsList=appDetails;
            loadingCount++;
            checkLoading(view);
            topChartAdapters =new topChartAdapter(getActivity(),appDetailsList, (AppClickListener) getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            recyclerView.setAdapter(topChartAdapters);

        });

        userViewModel.getBusinessLiveData().observe(getViewLifecycleOwner(), appDetails -> {
            businessList =appDetails;
            loadingCount++;
            checkLoading(view);
            topChartAdapters =new topChartAdapter(getActivity(), businessList, (AppClickListener) getActivity());
            businessRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            businessRecyclerView.setAdapter(topChartAdapters);

        });
        userViewModel.getDatingLiveData().observe(getViewLifecycleOwner(), appDetails -> {
            datingList =appDetails;
            loadingCount++;
            checkLoading(view);
            topChartAdapters =new topChartAdapter(getActivity(), datingList, (AppClickListener) getActivity());
            datingRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            datingRecycler.setAdapter(topChartAdapters);

        });
        userViewModel.getSportsLiveData().observe(getViewLifecycleOwner(), appDetails -> {
            sportsList =appDetails;
            loadingCount++;
            checkLoading(view);
            topChartAdapters =new topChartAdapter(getActivity(), sportsList, (AppClickListener) getActivity());
            sportsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            sportsRecycler.setAdapter(topChartAdapters);

        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.topcharts_fragment,container,false);
        recyclerView=view.findViewById(R.id.default_recyclerView);
        businessRecyclerView =view.findViewById(R.id.business_recyclerView);
        sportsRecycler =view.findViewById(R.id.sports_recyclerView);
        datingRecycler =view.findViewById(R.id.dating_recyclerView);
        view.findViewById(R.id.tv_comics).setOnClickListener(this);
        view.findViewById(R.id.tv_business).setOnClickListener(this);
        view.findViewById(R.id.tv_dating).setOnClickListener(this);
        view.findViewById(R.id.tv_sports).setOnClickListener(this);
        return view;
    }

    private void checkLoading(View view) {
        if(loadingCount <4){
            view.findViewById(R.id.topChartsScroll).setVisibility(View.GONE);
            view.findViewById(R.id.topChartsProgress).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.topChartsScroll).setVisibility(View.VISIBLE);
            view.findViewById(R.id.topChartsProgress).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        List<App> currentList=new ArrayList<>();
        String category = null;
        if(v.getId()==R.id.tv_comics)
        {
            currentList=appDetailsList;
            category="Comics";
        }
        else if(v.getId()==R.id.tv_business)
        {
            currentList= businessList;
            category="Business";
        }
        else if(v.getId()==R.id.tv_dating)
        {
            currentList= datingList;
            category="Dating";
        }
        else if(v.getId()==R.id.tv_sports)
        {
            currentList= sportsList;
            category="Sports";
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
