package com.example.cloneplaystore;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import static com.example.cloneplaystore.MainActivity.appInfoAdapter;

public class InstalledApps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed_apps);
        ListView appListView = findViewById(R.id.appListView);
        appListView.setAdapter(appInfoAdapter);
        appInfoAdapter.notifyDataSetChanged();
    }
}