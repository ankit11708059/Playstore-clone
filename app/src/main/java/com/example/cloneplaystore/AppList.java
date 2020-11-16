package com.example.cloneplaystore;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneplaystore.adapters.AppListAdapter;
import com.example.cloneplaystore.adapters.EditorsChoiceAdapter;
import com.example.cloneplaystore.adapters.PermissionsAdapter;
import com.example.cloneplaystore.adapters.ReviewDetailAdapter;
import com.example.cloneplaystore.model.App;
import com.example.cloneplaystore.viewmodel.AppDetailViewModel;

import java.util.List;

import javax.inject.Inject;

public class AppList extends AppCompatActivity {
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "description";
    public static final String REVIEW = "review";
    public static final String APP = "app";
    public static final String APP_LIST = "appList";
    public static final String CATEGORY = "category";
    public static final String EDITOR = "editor";

    private App app;
    private List<App> appList;
    private String type, category;
    TextView categoryName;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private AppDetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        type = getIntent().getStringExtra(TYPE);
        category = getIntent().getStringExtra(CATEGORY);
        BaseApplication.getAppComponent().injectList(this);
        detailViewModel = new ViewModelProvider(this, viewModelFactory).get(AppDetailViewModel.class);
        setUpDetails();
    }

    private void setUpDetails() {
        switch (type) {
            case DESCRIPTION:
                app = getIntent().getParcelableExtra(APP);
                setDescription();
                break;
            case REVIEW:
                app = getIntent().getParcelableExtra(APP);
                setReviews();
                break;
            case APP_LIST:
                appList = getIntent().getParcelableArrayListExtra(APP_LIST);
                categoryName = findViewById(R.id.textView4);
                categoryName.setText(category);
                setAppList();
                break;
            case EDITOR:
                appList = getIntent().getParcelableArrayListExtra(APP_LIST);
                categoryName = findViewById(R.id.textView4);
                categoryName.setText(R.string.editors_choice);
                setEditorsChoice();
                break;
        }
    }

    private void setEditorsChoice() {
        findViewById(R.id.scroll).setVisibility(View.GONE);
        findViewById(R.id.appsList).setVisibility(View.VISIBLE);
        RecyclerView recyclerView = findViewById(R.id.appsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new EditorsChoiceAdapter(getApplicationContext(), appList.subList(0, 5)));
    }

    private void setAppList() {
        findViewById(R.id.scroll).setVisibility(View.GONE);
        findViewById(R.id.appsList).setVisibility(View.VISIBLE);
        RecyclerView recyclerView = findViewById(R.id.appsList);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        AppListAdapter adapter = new AppListAdapter(getApplicationContext(), appList);
        recyclerView.setAdapter(adapter);
    }


    private void setReviews() {
        TextView textView = findViewById(R.id.textView4);
        textView.setText(R.string.reviews);
        findViewById(R.id.scroll).setVisibility(View.GONE);
        findViewById(R.id.appsList).setVisibility(View.VISIBLE);
        detailViewModel.getReviews(app.getAppId()).observe(this, reviewResponse -> {
            RecyclerView recyclerView = findViewById(R.id.appsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            ReviewDetailAdapter adapter = new ReviewDetailAdapter(reviewResponse.getData(), getApplicationContext());
            recyclerView.setAdapter(adapter);
        });

    }

    private void setDescription() {
        TextView textView = findViewById(R.id.textView4);
        textView.setText(R.string.about_this_app);
        findViewById(R.id.scroll).setVisibility(View.VISIBLE);
        findViewById(R.id.appsList).setVisibility(View.GONE);
        textView = findViewById(R.id.textView5);
        Spanned html = Html.fromHtml(app.getDescriptionHTML());
        textView.setText(html);
        detailViewModel.getPermissions(app.getAppId()).observe(this, permissions -> {
            RecyclerView recyclerView = findViewById(R.id.permissions);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            PermissionsAdapter adapter = new PermissionsAdapter(permissions);
            recyclerView.setAdapter(adapter);
        });
    }
}