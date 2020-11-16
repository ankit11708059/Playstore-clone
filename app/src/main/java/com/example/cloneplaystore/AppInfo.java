package com.example.cloneplaystore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppInfo {
    public String name;
    public String versionName;
    public Drawable icon;
    public String packageName;
    public static final Map<String, Integer> map
            = new HashMap<>();

    static List<AppInfo> getAppInfoList(Activity activity) {
        ArrayList<AppInfo> appInfoList = new ArrayList<>();
        @SuppressLint("QueryPermissionsNeeded") List<PackageInfo> packs = activity.getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
        final int size = packs.size();
        for (int i = 0; i < size; i++) {
            PackageInfo packageInfo = packs.get(i);
            String installer = activity.getPackageManager().getInstallerPackageName(
                    packageInfo.packageName);
            if (installer != null && packageInfo.versionName != null) {
                map.put(packageInfo.packageName, 1);
                AppInfo newInfo = new AppInfo();
                newInfo.name = packageInfo.applicationInfo.loadLabel(activity.getPackageManager()).toString();
                newInfo.packageName = packageInfo.packageName;
                newInfo.versionName = packageInfo.versionName;
                newInfo.icon = packageInfo.applicationInfo.loadIcon(activity.getPackageManager());
                appInfoList.add(newInfo);
            }

        }
        return appInfoList;
    }

}


