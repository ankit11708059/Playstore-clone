package com.example.cloneplaystore;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneplaystore.adapters.ReviewAdapter;
import com.example.cloneplaystore.adapters.ScreenshotAdapter;
import com.example.cloneplaystore.adapters.SimilarAppsAdapter;
import com.example.cloneplaystore.adapters.searchAdapter;
import com.example.cloneplaystore.dependencies.modules.RxSearch;
import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.viewmodel.AppDetailViewModel;
import com.example.cloneplaystore.viewmodel.AppViewModel;
import com.ferfalk.simplesearchview.SimpleOnQueryTextListener;
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.squareup.picasso.Picasso;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.view.View.GONE;


public class AppDetailActivity extends AppCompatActivity {
    private App app;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TextView textView;
    private SimpleSearchView searchView;
    private AppViewModel appViewModel;
    private RecyclerView search;
    public View searchResult;
    private String share;
    public boolean isSearchEntered = false;

    @Override
    public void onBackPressed() {
        if (isSearchEntered) {
            findViewById(R.id.id_detail_content).setVisibility(View.VISIBLE);
            findViewById(R.id.searchResult).setVisibility(GONE);
            findViewById(R.id.suggestLayout).setVisibility(GONE);
            isSearchEntered = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        setupSearchView(menu);
        menu.findItem(R.id.menu_flag).setCheckable(true);
        return true;
    }

    private void setupSearchView(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, share);
                startActivity(intent);
                return true;
            case R.id.menu_flag:
                if (item.isChecked()) {
                    item.setChecked(false);
                    Toast.makeText(getApplicationContext(), "Removed Flag", Toast.LENGTH_LONG).show();
                } else {
                    item.setChecked(true);
                    Toast.makeText(getApplicationContext(), "Flagged as Inappropriate", Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        Toolbar actionBar = findViewById(R.id.toolbar);
        search = findViewById(R.id.search_recyclerview);
        searchResult = findViewById(R.id.searchResult);
        setSupportActionBar(actionBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        BaseApplication.getAppComponent().injectDetails(this);
        appViewModel = new ViewModelProvider(this, viewModelFactory).get(AppViewModel.class);
        searchView = findViewById(R.id.searchView);
        app = getIntent().getParcelableExtra("App");
        setInitialDetails(app);
        setAppDetails();
        searchView.setOnQueryTextListener(new SimpleOnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               searchQuery(query);
               return true;
           }
       });
      SearchWithDebounce(searchView);
       searchView.setOnSearchViewListener(new SimpleSearchView.SearchViewListener() {
           @Override
           public void onSearchViewShown() {
               findViewById(R.id.id_detail_content).setVisibility(GONE);
               isSearchEntered=true;
           }
           @Override
           public void onSearchViewClosed() {
               findViewById(R.id.id_detail_content).setVisibility(View.VISIBLE);
               findViewById(R.id.searchResult).setVisibility(GONE);
               searchView.onBackPressed();
           }
           @Override
           public void onSearchViewShownAnimation() {

           }

           @Override
           public void onSearchViewClosedAnimation() {

           }
       });
    searchView.enableVoiceSearch(true);
    searchView.showVoice(true);
    searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
          searchQuery(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }

        @Override
        public boolean onQueryTextCleared() {
            return false;
        }
    });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String query = data.getStringExtra(SearchManager.QUERY);
            suggestQuery(query);
            searchView.setQuery(query, false);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @SuppressLint("CheckResult")
  private void SearchWithDebounce(SimpleSearchView searchView) {
        RxSearch.fromSearchView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(item -> item.length() > 2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    isSearchEntered = true;
                    suggestQuery(query);
                });
    }

    private void suggestQuery(String query) {
        findViewById(R.id.suggestLayout).setVisibility(View.VISIBLE);
        appViewModel.getSuggestionLiveData("suggestions?q=" + query).observe(this, apps -> {
            searchResult.setVisibility(View.GONE);
            findViewById(R.id.suggestLayout).setVisibility(View.VISIBLE);
            RecyclerView recyclerView = findViewById(R.id.suggest_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new AppDetailActivity.SuggestionAdapter(getApplicationContext(), apps));
        });
    }

    public void searchQuery(String currentQuery) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

        findViewById(R.id.suggestLayout).setVisibility(View.GONE);
        searchResult.setVisibility(View.VISIBLE);
        appViewModel.getSearchLiveData("search?q=" + currentQuery).observe(this, apps -> {
            search.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            search.setAdapter(new searchAdapter(getApplicationContext(), apps));
        });

    }

    private void setHistogram(App app) {
        RatingReviews ratingReviewsBackground = findViewById(R.id.rating_reviews1);
        RatingReviews ratingReviews = findViewById(R.id.rating_reviews);

        int[] rater = new int[5];
        long total = app.getRatings();
        int currentRating = (int) ((long) app.getHistogram().get5() * (100) / total);
        rater[0] = currentRating;
        currentRating = (int) ((long) app.getHistogram().get4() * (100) / total);
        rater[1] = currentRating;
        currentRating = (int) ((long) app.getHistogram().get3() * (100) / total);
        rater[2] = currentRating;
        currentRating = (int) ((long) app.getHistogram().get2() * (100) / total);
        rater[3] = currentRating;
        currentRating = (int) ((long) app.getHistogram().get1() * (100) / total);
        rater[4] = currentRating;
        int[] raterBackGround = new int[]{100, 100, 100, 100, 100};

        TextView scoreTextView = findViewById(R.id.tv_score_text);
        TextView totalRatings = findViewById(R.id.tv_totalRatings);
        NumberFormat format = NumberFormat.getInstance();
        totalRatings.setText(format.format(total));
        scoreTextView.setText(app.getScoreText());
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        double score = app.getScore();
        ratingBar.setRating((float) score);
        ratingReviews.createRatingBars(100, BarLabels.STYPE3, Color.parseColor("#00875f"), rater);
        ratingReviewsBackground.createRatingBars(100, BarLabels.STYPE3, Color.parseColor("#ececec"), raterBackGround);
    }

    private void setAppDetails() {
        AppViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(AppViewModel.class);
        viewModel.getScreenshots(app.getAppId()).observe(this, this::setUpAppDetailsActivity);
        AppDetailViewModel detailViewModel = new ViewModelProvider(this, viewModelFactory).get(AppDetailViewModel.class);
        detailViewModel.getSimilarApps(app.getAppId()).observe(this, this::setUpSimilarApps);
    }

    private void setUpSimilarApps(List<App> apps) {
        SimilarAppsAdapter appAdapter = new SimilarAppsAdapter(getApplicationContext(), apps);
        RecyclerView recyclerView = findViewById(R.id.similarAppsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(appAdapter);
        findViewById(R.id.similarAppsButton).setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putParcelableArrayList(AppList.APP_LIST, (ArrayList<? extends Parcelable>) apps);
            Intent intent = new Intent(getApplicationContext(), AppList.class);
            intent.putExtra(AppList.TYPE, AppList.APP_LIST);
            intent.putExtra(AppList.CATEGORY,"Similar apps");
            intent.putExtras(args);
            startActivity(intent);
        });
    }

    private void setInitialDetails(App app) {
        if (AppInfo.map.containsKey(app.getAppId())) {
            findViewById(R.id.btn_install).setVisibility(GONE);
            findViewById(R.id.openAppButton).setVisibility(View.VISIBLE);
            findViewById(R.id.uninstall_button).setVisibility(View.VISIBLE);
            findViewById(R.id.openAppButton).setOnClickListener(v -> {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(app.getAppId());
                startActivity(launchIntent);
            });

            findViewById(R.id.uninstall_button).setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:" + app.getAppId()));
                startActivity(intent);
            });
        }
        textView = findViewById(R.id.appDetailName);
        textView.setText(app.getTitle());
        ImageView imageView = findViewById(R.id.appDetailIcon);
        imageView.setClipToOutline(true);
        Picasso.get()
                .load(app.getIcon())
                .into(imageView);
        textView = findViewById(R.id.appDetailDev);
        textView.setText(app.getDeveloper());
        textView = findViewById(R.id.score);
        textView.setText(app.getScoreText());
        textView = findViewById(R.id.appDetailsSummary);
        textView.setText(app.getSummary());
    }

    private void setUpAppDetailsActivity(App app) {
        if (app != null) {
            ScreenshotAdapter screenshotAdapter = new ScreenshotAdapter(app.getScreenshots());
            RecyclerView recyclerView = findViewById(R.id.screenshots);
            recyclerView.setAdapter(screenshotAdapter);
            share = app.getUrl();
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            findViewById(R.id.aboutThisApp).setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), AppList.class);
                intent.putExtra(AppList.TYPE, AppList.DESCRIPTION);
                Bundle args = new Bundle();
                args.putParcelable(AppList.APP, app);
                intent.putExtras(args);
                startActivity(intent);
            });
            findViewById(R.id.reviewButton).setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), AppList.class);
                intent.putExtra(AppList.TYPE, AppList.REVIEW);
                Bundle args = new Bundle();
                args.putParcelable(AppList.APP, app);
                intent.putExtras(args);
                startActivity(intent);
            });
            recyclerView = findViewById(R.id.reviewsList);
            ReviewAdapter adapter = new ReviewAdapter(app.getComments().subList(0, 3));
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
            textView = findViewById(R.id.ratedForText);
            textView.setText(app.getContentRating());
            setUpInstalls(app);
            setUpDeveloperContact(app);
            setHistogram(app);
            if (app.getAdSupported()) {
                findViewById(R.id.contains_ads).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.contains_ads).setVisibility(GONE);
            }
            if (app.getOffersIAP()) {
                findViewById(R.id.contains_purchases).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.contains_purchases).setVisibility(GONE);
            }
            textView = findViewById(R.id.appGenre);
            textView.setText(app.getGenre());
            textView = findViewById(R.id.dSize);
            String random = new Random().nextInt(50)+10 + " MB";
            textView.setText(random);
            ImageView view = findViewById(R.id.ratedFor);
            if (app.getContentRating().endsWith("3+")) {
                view.setImageResource(R.drawable.sc_rated_3);
            } else if (app.getContentRating().endsWith("7+")) {
                view.setImageResource(R.drawable.sc_rated_7);
            } else {
                view.setImageResource(R.drawable.sc_rated_12);
            }
        }
    }

    private void setUpInstalls(App app) {
        long installs = app.getMinInstalls();
        String result;
        final int BILLION = 1000000000;
        final int MILLION = 1000000;
        final int THOUSAND = 1000;
        if ((installs / BILLION) >= 1) {
            result = (installs / BILLION) + "B+";
        } else if ((installs / MILLION) >= 1) {
            result = (installs / MILLION) + "M+";
        } else if ((installs / THOUSAND) >= 1) {
            result = (installs / THOUSAND) + "K+";
        } else {
            result = installs + "";
        }
        textView = findViewById(R.id.installs);
        textView.setText(result);
        installs = app.getRatings();
        if ((installs / BILLION) >= 1) {
            result = (installs / BILLION) + "B";
        } else if ((installs / MILLION) >= 1) {
            result = (installs / MILLION) + "M";
        } else if ((installs / THOUSAND) >= 1) {
            result = (installs / THOUSAND) + "K";
        } else {
            result = installs + "";
        }
        result += " reviews";
        textView = findViewById(R.id.noOfReviews);
        textView.setText(result);
    }

    private void setUpDeveloperContact(App app) {
        final String MAPS = "https://www.google.com/maps/place/";
        TextView developer = findViewById(R.id.developerEmail);
        developer.setText(app.getDeveloperEmail());
        developer.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + app.getDeveloperEmail()));
            startActivity(Intent.createChooser(intent, "Email via "));
        });
        developer = findViewById(R.id.developerWebsite);
        developer.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(app.getDeveloperWebsite()));
            startActivity(intent);
        });
        developer = findViewById(R.id.developerAddress);
        developer.setText(app.getDeveloperAddress());
        developer.setOnClickListener(view -> {
            String uri = MAPS + app.getDeveloperAddress();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        });
        developer = findViewById(R.id.privacyPolicy);
        developer.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(app.getPrivacyPolicy()));
            startActivity(intent);
        });
        findViewById(R.id.developerContactDownButton).setOnClickListener(view -> {
            findViewById(R.id.developerContact).setVisibility(View.VISIBLE);
            findViewById(R.id.developerContactDownButton).setVisibility(GONE);
            findViewById(R.id.developerContactUpButton).setVisibility(View.VISIBLE);
        });
        findViewById(R.id.developerContactUpButton).setOnClickListener(view -> {
            findViewById(R.id.developerContact).setVisibility(GONE);
            findViewById(R.id.developerContactDownButton).setVisibility(View.VISIBLE);
            findViewById(R.id.developerContactUpButton).setVisibility(GONE);
        });
    }

    public class SuggestionAdapter extends RecyclerView.Adapter<AppDetailActivity.SuggestionAdapter.ViewHolder> {
        final List<String> apps;
        final Context mContext;

        public SuggestionAdapter(Context applicationContext, List<String> suggestions) {
            mContext = applicationContext;
            apps = suggestions;
        }

        @androidx.annotation.NonNull
        @Override
        public AppDetailActivity.SuggestionAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.suggestitem, parent, false);
            return new AppDetailActivity.SuggestionAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull AppDetailActivity.SuggestionAdapter.ViewHolder holder, int position) {
            holder.textView.setText(apps.get(position));
        }

        @Override
        public int getItemCount() {
            return apps.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final TextView textView;

            public ViewHolder(@androidx.annotation.NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv_suggest);
                itemView.setOnClickListener(v -> searchQuery(apps.get(getAdapterPosition())));
            }
        }
    }
}
