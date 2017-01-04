package com.ping.cookbook.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ping.cookbook.R;

import ping.Lib.Utils.StringUtil;


/**
 * User: Walten
 * Date: 16/4/29下午3:12
 * Email: wtf55@vip.qq.com
 */
public class ToolBarUtils {
    private final int TAG_TOOLBAR_FROM_ACTIVITY = 1;

    private final int TAG_TOOLBAR_FROM_FRAGMENT = 2;

    private int mTag = -1;

    private Activity mAty;

    private View mRootView;

    private ToolBarUtils(Activity aty){
        mAty = aty;
        mTag = TAG_TOOLBAR_FROM_ACTIVITY;
    }

    private ToolBarUtils(View rootView){
        mRootView = rootView;
        mTag = TAG_TOOLBAR_FROM_FRAGMENT;
    }

    public static ToolBarUtils getSettor(Activity aty){
        return new ToolBarUtils(aty);
    }

    public TextView getRightTextView(){
        return findTextView(R.id.tv_right_custom);
    }

    public TextView getLeftTextView(){
        return findTextView(R.id.tv_left_custom);
    }

    public ImageView getRightImageView(){
        return findImageView(R.id.iv_right_custom);
    }

    public ImageView getLeftImageView(){
        return findImageView(R.id.iv_left_custom);
    }

    public static ToolBarUtils getSettor(View rootView){
        return new ToolBarUtils(rootView);
    }

    public ToolBarUtils setTitle(String title){
        if(StringUtil.isBlank(title))
            findTextView(R.id.tv_title_custom).setVisibility(View.GONE);
        else {
            findTextView(R.id.tv_title_custom).setVisibility(View.VISIBLE);
            findTextView(R.id.tv_title_custom).setText(title);
        }
        return this;
    }

    public ToolBarUtils setLeftText(String leftText){
        if(StringUtil.isBlank(leftText))
            findTextView(R.id.tv_left_custom).setVisibility(View.GONE);
        else {
            findTextView(R.id.tv_left_custom).setVisibility(View.VISIBLE);
            findTextView(R.id.tv_left_custom).setText(leftText);
        }
        return this;
    }

    public ToolBarUtils setRightText(String rightText){
        if(StringUtil.isBlank(rightText))
            findTextView(R.id.tv_right_custom).setVisibility(View.GONE);
        else {
            findTextView(R.id.tv_right_custom).setVisibility(View.VISIBLE);
            findTextView(R.id.tv_right_custom).setText(rightText);
        }
        return this;
    }

    public ToolBarUtils setLeftImage(int rid){
        findImageView(R.id.iv_left_custom).setVisibility(View.VISIBLE);
        findImageView(R.id.iv_left_custom).setImageResource(rid);
        return this;
    }

    public ToolBarUtils setRightImage(int rid){
        findImageView(R.id.iv_right_custom).setVisibility(View.VISIBLE);
        findImageView(R.id.iv_right_custom).setImageResource(rid);
        return this;
    }

    public ToolBarUtils setLeftTextOnClick(View.OnClickListener listener){
        findView(R.id.rv_tv_left).setOnClickListener(listener);
        return this;
    }

    public ToolBarUtils setRightTextOnClick(View.OnClickListener listener){
        findView(R.id.rv_tv_right).setOnClickListener(listener);
        return this;
    }

    public ToolBarUtils setLeftImageOnClick(View.OnClickListener listener){
        findView(R.id.rv_iv_left).setOnClickListener(listener);
        return this;
    }

    public ToolBarUtils setRightImageOnClick(View.OnClickListener listener){
        findView(R.id.rv_iv_right).setOnClickListener(listener);
        return this;
    }

    public ToolBarUtils setBackToolBar(String title){
        setTitle(title).setLeftImage(R.drawable.btn_back).setLeftImageOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTag == TAG_TOOLBAR_FROM_ACTIVITY)
                    mAty.finish();
            }
        });
        return this;
    }
    public ToolBarUtils setBackToolBar(final Fragment fragment, String title){
        setTitle(title).setLeftImage(R.drawable.btn_back).setLeftImageOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTag == TAG_TOOLBAR_FROM_FRAGMENT)
                    fragment.getActivity().finish();
            }
        });
        return this;
    }

    private View findView(int id){
        if (mTag == TAG_TOOLBAR_FROM_ACTIVITY){
            return mAty.findViewById(id);
        }else{
            return mRootView.findViewById(id);
        }
    }

    private TextView findTextView(int id){
        if (mTag == TAG_TOOLBAR_FROM_ACTIVITY){
            return (TextView)mAty.findViewById(id);
        }else{
            return (TextView)mRootView.findViewById(id);
        }
    }

    private ImageView findImageView(int id){
        if (mTag == TAG_TOOLBAR_FROM_ACTIVITY){
            return (ImageView)mAty.findViewById(id);
        }else{
            return (ImageView)mRootView.findViewById(id);
        }
    }
}
