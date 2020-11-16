package com.example.cloneplaystore.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cloneplaystore.AppInfo;
import com.example.cloneplaystore.R;

import java.util.List;


public class AppInfoAdapter extends ArrayAdapter<AppInfo> {
    private final List<AppInfo> appList;
    private final LayoutInflater inflater;

    public AppInfoAdapter(Context context, int resource, List<AppInfo> objects) {
        super(context, resource, objects);
        this.appList = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public AppInfo getItem(int i) {
        return appList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View appView = view;
        if (view == null)
            appView = inflater.inflate(R.layout.app_info_item,null);
        AppInfo appInfo = appList.get(i);
        TextView appName = appView.findViewById(R.id.appName);
        TextView appVersion = appView.findViewById(R.id.appVersion);
        appVersion.setText(appInfo.versionName);
        ImageView appIcon = appView.findViewById(R.id.appIcon);
        appIcon.setImageDrawable(appInfo.icon);
        appName.setText(appInfo.name);
        appView.findViewById(R.id.open_button).setOnClickListener(v -> {
            Intent launchIntent =v.getContext().getPackageManager().getLaunchIntentForPackage(appInfo.packageName);
            v.getContext().startActivity(launchIntent);
        });
        return appView;
    }
}