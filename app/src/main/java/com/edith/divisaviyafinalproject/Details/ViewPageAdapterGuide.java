package com.edith.divisaviyafinalproject.Details;/*
Created by Akila Ishan on 2021-02-11.
*/

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapterGuide extends FragmentPagerAdapter {
    final List<Fragment> fragmentList = new ArrayList<>();
    final List<String> fragmentListTitle = new ArrayList<>();

    public ViewPageAdapterGuide(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {

        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return fragmentListTitle.get(position);
    }

    public void AddFragment (Fragment fragment, String Title){
        fragmentList.add(fragment);
        fragmentListTitle.add(Title);
    }
}
