package com.example.cloneplaystore.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.cloneplaystore.fragments.Categories;
import com.example.cloneplaystore.fragments.EditorsChoice;
import com.example.cloneplaystore.fragments.ForYou;
import com.example.cloneplaystore.fragments.TopCharts;

public class Pager extends FragmentStatePagerAdapter {

    final int tabCount;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount= tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ForYou();
            case 1:
                return new TopCharts();
            case 2:
                return new Categories();
            case 3:
                return new EditorsChoice();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}