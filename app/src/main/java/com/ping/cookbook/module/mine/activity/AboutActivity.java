package com.ping.cookbook.module.mine.activity;

import android.os.Bundle;
import android.view.View;

import com.ping.cookbook.R;
import com.ping.cookbook.base.BaseActivity;


public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initViews();
    }

    private void initViews() {
        setToolbarVisible(View.GONE);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}