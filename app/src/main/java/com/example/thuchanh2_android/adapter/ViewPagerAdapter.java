package com.example.thuchanh2_android.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.thuchanh2_android.fragment.FragmentHistory;
import com.example.thuchanh2_android.fragment.FragmentSearch;
import com.example.thuchanh2_android.fragment.FragmentToday;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int numPage ;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        numPage=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new FragmentToday();
            case 1 : return new FragmentHistory();
            case 2 : return new FragmentSearch();
        }
        return new FragmentToday();
    }

    @Override
    public int getCount() {
        return numPage;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : return "TODAY";
            case 1 : return "HISTORY";
            case 2 : return "SEARCH";
            default: return "TODAY";
        }
    }
}
