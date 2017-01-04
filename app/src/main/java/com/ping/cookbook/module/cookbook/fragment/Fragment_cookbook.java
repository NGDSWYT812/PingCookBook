package com.ping.cookbook.module.cookbook.fragment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ping.cookbook.R;
import com.ping.cookbook.base.BaseFragment;
import com.ping.cookbook.bean.CookList;
import com.ping.cookbook.dbHelper.DBHelper;
import com.ping.cookbook.module.cookbook.activity.SearchActivity;
import com.ping.cookbook.module.cookbook.adapter.CookBookPagerAdapter;

import java.util.List;


public class Fragment_cookbook extends BaseFragment {
    private AlphaAnimation alphaAnimation;
    private View contentView;
    private List<CookList> lists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateViewProxy(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_cookbook, container, false);
            initViews(contentView);
        }
        return contentView;
    }

    private void initViews(View view) {
        setToolbarVisible(View.GONE);
        lists = DBHelper.getDaoSession().getCookListDao().loadAll();
        CookBookPagerAdapter adapter = new CookBookPagerAdapter(getChildFragmentManager(), lists);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        final ImageView iv_search = (ImageView) view.findViewById(R.id.iv_search);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(iv_search, "translationX", 30, 5, 30);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.setRepeatCount(ValueAnimator.INFINITE);
        ofFloat.setRepeatMode(ValueAnimator.REVERSE);
        ofFloat.setDuration(2000).start();
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        alphaAnimation = new AlphaAnimation(0f, 1);
        iv_search.setAnimation(alphaAnimation);
        alphaAnimation.setDuration(2000);
    }

}
