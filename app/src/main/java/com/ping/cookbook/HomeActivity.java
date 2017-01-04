package com.ping.cookbook;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ping.cookbook.base.BaseActivity;
import com.ping.cookbook.module.cookbook.fragment.Fragment_cookbook;
import com.ping.cookbook.module.mine.fragment.Fragment_mine;
import com.ping.cookbook.module.recommend.fragment.Fragment_recommend;

import ping.Lib.Utils.ToastUtil;


public class HomeActivity extends BaseActivity {
    private Class mFragmentList[] = {Fragment_recommend.class, Fragment_cookbook.class, Fragment_mine.class};
    private int mImageList[] = {R.drawable.tab_btn_search, R.drawable.tab_btn_recommend,R.drawable.tab_btn_mine};
    private String mTaglist[] = {"推荐", "食谱","我的"};
    private FragmentTabHost fTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        setToolbarVisible(View.GONE);
        fTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fTabHost.setup(this, getSupportFragmentManager(), R.id.rl_content);
        fTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mFragmentList.length; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = fTabHost.newTabSpec(mTaglist[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            fTabHost.addTab(tabSpec, mFragmentList[i], null);
            //设置Tab按钮的背景
            fTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.white);
        }
    }

    private View getTabItemView(int index) {
        View view = getLayoutInflater().inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_tab);
        imageView.setImageResource(mImageList[index]);
        TextView textView = (TextView) view.findViewById(R.id.text_tab);
        textView.setText(mTaglist[index]);
        return view;
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
