package com.ping.cookbook.module.cookbook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ping.cookbook.bean.CookList;
import com.ping.cookbook.module.cookbook.fragment.Fragment_cookbookInstance;

import java.util.List;

public class CookBookPagerAdapter extends FragmentPagerAdapter {
    private List<CookList> lists;

    public CookBookPagerAdapter(FragmentManager fm, List<CookList> lists) {
        super(fm);
        this.lists = lists;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment_cookbookInstance.newInstance(lists.get(position + 1).getId());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lists.get(position + 1).getType();
    }

    @Override
    public int getCount() {
        return lists.size() - 1;
    }
}