package com.ping.cookbook.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ping.cookbook.R;
import com.umeng.analytics.MobclickAgent;

import ping.Lib.statebar.SystemBarTintManager;


/**
 * Description : Activity基础类
 */
public abstract class BaseActivity extends AppCompatActivity {
    private View mToolbar;
    protected View mLayoutLoading, mLayoutEmpty, mLayoutError;
    protected FrameLayout mFraLayoutContent;
    protected TextView mProgressText, mEmptyContentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        App app = (App) getApplication();
        app.pushActivity(this);
        initStatusBar(R.color.mainColor);
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        mToolbar = findViewById(R.id.toolbar);
        mFraLayoutContent = (FrameLayout) findViewById(R.id.fraLayoutContent);
        mLayoutLoading = findViewById(R.id.layoutLoading);
        mLayoutError = findViewById(R.id.layoutError);
        mLayoutEmpty = findView(R.id.layoutEmpty);
        mProgressText = (TextView) findViewById(R.id.progress_text);
        mEmptyContentText = (TextView) findViewById(R.id.emptycontent);
    }

    //---------------------网络加载布局---------------------//
    public synchronized void showLayoutLoading() {
        mLayoutLoading.setVisibility(View.VISIBLE);
    }

    public synchronized void hideLayoutLoading() {
        mLayoutLoading.setVisibility(View.GONE);
    }

    public void setProgressText(String str) {
        mProgressText.setText(str);
    }

    public void setLoadingBackgroundColor(int color) {
        mLayoutLoading.setBackgroundColor(color);
    }

    public void showLoading(boolean isShow) {
        if (isShow) {
            showLayoutLoading();
        } else {
            hideLayoutLoading();
        }
    }

    //---------------------暂无数据布局---------------------//

    public synchronized void showLayoutEmpty(final OnClickListener listener) {
        mLayoutEmpty.setOnClickListener(listener);
        mLayoutEmpty.setVisibility(View.VISIBLE);
    }

    public synchronized void hideLayoutEmpty() {
        mLayoutEmpty.setVisibility(View.GONE);
    }

    public void setEmptyContentText(String str) {
        mEmptyContentText.setText(str);
    }

    //---------------------加载失败布局---------------------//
    public synchronized void showLayoutError(final OnClickListener listener) {
        mLayoutError.setOnClickListener(listener);
        mLayoutError.setVisibility(View.VISIBLE);
    }

    public synchronized void hideLayoutError() {
        mLayoutError.setVisibility(View.GONE);
    }

    //---------------------设置toolbar---------------------//
    public void setToolbarVisible(int visible) {
        mToolbar.setVisibility(visible);
    }

    public View getToolbar() {
        return mToolbar;
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, mFraLayoutContent, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void finish() {
        App app = (App) getApplication();
        app.popActivity();
        super.finish();
    }

    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /***
     * 隐藏键盘
     */
    public void hideInput() {
        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
        }
    }

    public void initStatusBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏透明 需要在创建SystemBarTintManager 之前调用。
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);

        tintManager.setStatusBarTintEnabled(true);
        //使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
        tintManager.setStatusBarTintResource(color);

        tintManager.setStatusBarDarkMode(false, this);
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
