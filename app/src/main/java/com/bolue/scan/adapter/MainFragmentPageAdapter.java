package com.bolue.scan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by cty on 2017/6/5.
 */

public class MainFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    public MainFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }
}
