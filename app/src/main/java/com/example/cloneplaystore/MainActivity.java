package com.example.cloneplaystore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.cloneplaystore.adapters.AppClickListener;
import com.example.cloneplaystore.adapters.AppInfoAdapter;
import com.example.cloneplaystore.adapters.Pager;
import com.example.cloneplaystore.adapters.searchAdapter;
import com.example.cloneplaystore.dependencies.modules.NetworkModule;
import com.example.cloneplaystore.dependencies.modules.RxSearch;
import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.viewmodel.AppViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import retrofit2.Retrofit;

@SuppressLint("StaticFieldLeak")
public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, AppClickListener, NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private AppViewModel appViewModel;
    private FloatingSearchView mSearchView;
    private RecyclerView search;
    public static AppInfoAdapter appInfoAdapter;
    private DrawerLayout drawerLayout;
    public static boolean isSearchEntered = false;
    public static View tabActivity, searchResult;
    final int VOICE_SEARCH_CODE =100;
    Ankit ankit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseApplication.getAppComponent().injectMainActivity(this);
        appViewModel = new ViewModelProvider(this, viewModelFactory).get(AppViewModel.class);
        setUpTabLayout();
        List<AppInfo> appInfoList = AppInfo.getAppInfoList(this);
        appInfoAdapter = new AppInfoAdapter(this, R.layout.app_info_item, appInfoList);
        mSearchView = findViewById(R.id.search_view);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        setUpNavigationView();
        SearchWithDebounce(mSearchView);
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
            }

            @Override
            public void onSearchAction(String currentQuery) {
                isSearchEntered = true;
                searchQuery(currentQuery);
            }
        });
        findViewById(R.id.voice_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                intent.putExtra("android.speech.extra.PROMPT", "Sepeak Now");
                startActivityForResult(intent, VOICE_SEARCH_CODE);
            }
        });
    }
    @ Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_SEARCH_CODE && resultCode == RESULT_OK) {
            ArrayList< String > matches = data
                    .getStringArrayListExtra("android.speech.extra.RESULTS");
            mSearchView.setSearchText(matches.get(0));
            suggestQuery(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setUpTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        search = findViewById(R.id.search_recyclerview);
        tabLayout.addTab(tabLayout.newTab().setText("For You"));
        tabLayout.addTab(tabLayout.newTab().setText("Top Charts"));
        tabLayout.addTab(tabLayout.newTab().setText("Categories"));
        tabLayout.addTab(tabLayout.newTab().setText("Editor's Choice"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = findViewById(R.id.pager);
        tabActivity = findViewById(R.id.tabActivity);
        searchResult = findViewById(R.id.searchResult);
        Pager pager = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pager);
        tabLayout.addOnTabSelectedListener(this);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                findViewById(R.id.searchResult).setVisibility(View.GONE);
                findViewById(R.id.suggestLayout).setVisibility(View.GONE);
                if (item.getItemId() == R.id.bottom_apps) {
                    findViewById(R.id.tabActivity).setVisibility(View.VISIBLE);
                    findViewById(R.id.notApps).setVisibility(View.GONE);

                } else {
                    findViewById(R.id.tabActivity).setVisibility(View.GONE);
                    findViewById(R.id.notApps).setVisibility(View.VISIBLE);
                }
                return true;
            };


    @Override
    public void onBackPressed() {
        if (isSearchEntered) {
            findViewById(R.id.tabActivity).setVisibility(View.VISIBLE);
            findViewById(R.id.searchResult).setVisibility(View.GONE);
            isSearchEntered = false;
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void SearchWithDebounce(FloatingSearchView mSearchView) {
        RxSearch.fromFloatingSearchView(mSearchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(item -> item.length() > 2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    isSearchEntered = true;
                    suggestQuery(query);
                });
    }

    public void suggestQuery(String newQuery) {
        appViewModel.getSuggestionLiveData("suggestions?q=" + newQuery).observe(this, apps -> {
            tabActivity.setVisibility(View.GONE);
            searchResult.setVisibility(View.GONE);
            findViewById(R.id.suggestLayout).setVisibility(View.VISIBLE);
            RecyclerView recyclerView = findViewById(R.id.suggest_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new SuggestionAdapter(getApplicationContext(), apps));
        });
    }

    public void searchQuery(String currentQuery) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        tabActivity.setVisibility(View.GONE);
        findViewById(R.id.suggestLayout).setVisibility(View.GONE);
        searchResult.setVisibility(View.VISIBLE);
        findViewById(R.id.suggestLayout).setVisibility(View.GONE);
        appViewModel.getSearchLiveData("search?q=" + currentQuery).observe(this, apps -> {
            search.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            search.setAdapter(new searchAdapter(getApplicationContext(), apps));
        });
    }

    private void setUpNavigationView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,
                null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        mSearchView.attachNavigationDrawerToMenuButton(drawerLayout);

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_apps_and_games) {
            Intent intent = new Intent(this, InstalledApps.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClickApp(App appDetails) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("App", appDetails);
        Intent intent = new Intent(this, AppDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> {
        final List<String> apps;
        final Context mContext;

        public SuggestionAdapter(Context applicationContext, List<String> suggestions) {
            mContext = applicationContext;
            apps = suggestions;
        }

        @androidx.annotation.NonNull
        @Override
        public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.suggestitem, parent, false);
            return new SuggestionAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
            holder.suggestion.setText(apps.get(position));
        }

        @Override
        public int getItemCount() {
            return apps.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            final TextView suggestion;

            public ViewHolder(@androidx.annotation.NonNull View itemView) {
                super(itemView);
                suggestion = itemView.findViewById(R.id.tv_suggest);

                itemView.setOnClickListener(v -> searchQuery(apps.get(getAdapterPosition())));
            }
        }
    }
}



