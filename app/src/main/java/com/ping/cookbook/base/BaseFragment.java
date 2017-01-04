package com.ping.cookbook.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ping.cookbook.R;
import com.umeng.analytics.MobclickAgent;

import ping.Lib.Utils.ResourceHelper;


/**
 * function: 基础fragment
 */
public abstract class BaseFragment extends Fragment {
    protected FragmentActivity activity;
    private View mToolbar;
    protected View mContentFrame, mSubRootView, mLayoutLoading, mLayoutEmpty, mLayoutError;
    protected FrameLayout mFraLayoutContent;
    protected TextView mProgressText, mEmptyContentText;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentFrame == null) {
            mContentFrame = ResourceHelper.loadLayout(activity, R.layout.fragment_base);
            mToolbar = mContentFrame.findViewById(R.id.toolbar);
            mFraLayoutContent = (FrameLayout) mContentFrame.findViewById(R.id.fraLayoutContent);
            mLayoutLoading = mContentFrame.findViewById(R.id.layoutLoading);
            mLayoutError = mContentFrame.findViewById(R.id.layoutError);
            mLayoutEmpty = mContentFrame.findViewById(R.id.layoutEmpty);
            mProgressText = (TextView) mContentFrame.findViewById(R.id.progress_text);
            mEmptyContentText = (TextView) mContentFrame.findViewById(R.id.emptycontent);
            mSubRootView = onCreateViewProxy(inflater, mFraLayoutContent, savedInstanceState);
            if (mSubRootView != null && mSubRootView.getParent() == null) {
                mFraLayoutContent.addView(mSubRootView);
            }
        }
        return mContentFrame;

    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View onCreateViewProxy(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


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

    public void showLoading(boolean isShow) {
        if (isShow) {
            showLayoutLoading();
        } else {
            hideLayoutLoading();
        }
    }

    //---------------------暂无数据布局---------------------//

    public synchronized void showLayoutEmpty(final View.OnClickListener listener) {
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
    public synchronized void showLayoutError(final View.OnClickListener listener) {
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("cookbook");
    }
    /***
     * 隐藏键盘
     */
    public void hideInput() {
        try {
            if (getActivity().getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("cookbook");
    }

}
