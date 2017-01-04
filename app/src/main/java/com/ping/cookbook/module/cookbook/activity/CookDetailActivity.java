package com.ping.cookbook.module.cookbook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ping.cookbook.R;
import com.ping.cookbook.base.BaseActivity;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.bean.Step;
import com.ping.cookbook.dao.CookIndexDao;
import com.ping.cookbook.dao.StepDao;
import com.ping.cookbook.dbHelper.DBHelper;
import com.ping.cookbook.module.cookbook.adapter.CookBookStepsAdapter;
import com.ping.cookbook.module.cookbook.widgets.ImageDetailDialog;
import com.ping.cookbook.module.cookbook.widgets.MyEventBus;
import com.ping.cookbook.widgets.MyScrollView;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ping.Lib.Utils.DensityUtil;
import ping.Lib.Utils.ToastUtil;
import ping.Lib.Utils.ToolUtils;
import ping.Lib.widgets.CJListView;
import ping.Lib.widgets.flowLayout.TagBaseAdapter;
import ping.Lib.widgets.flowLayout.TagCloudLayout;


public class CookDetailActivity extends BaseActivity implements View.OnClickListener, MyScrollView.OnScrollListener {
    private ImageView iv_like;
    private CookIndex cookIndex;
    private CJListView cjl_steps;
    private TextView tv_top_title;
    private RelativeLayout rl_top;
    private List<CookIndex> list;
    private List<Step> stepList;
    private ImageDetailDialog imageDetailDialog;
    private List<String> ingredients = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookdetail);
        initData(savedInstanceState);
        initView();
    }

    private void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        cookIndex = (CookIndex) intent.getSerializableExtra("CookIndex");
        if (cookIndex == null && savedInstanceState != null) {
            cookIndex = (CookIndex) savedInstanceState.getSerializable("CookIndex");
        }
    }

    private void initView() {
        setToolbarVisible(View.GONE);
        cjl_steps = (CJListView) findViewById(R.id.cjl_steps);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        ImageView iv_image = (ImageView) findViewById(R.id.iv_image);
        rl_top = (RelativeLayout) findViewById(R.id.rl_top);
        MyScrollView sv_view = (MyScrollView) findViewById(R.id.sv_view);
        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_intro = (TextView) findViewById(R.id.tv_intro);
        TagCloudLayout tv_tags = (TagCloudLayout) findViewById(R.id.tv_tags);
        TextView tv_ingredients = (TextView) findViewById(R.id.tv_ingredients);
        TextView tv_ingredients1 = (TextView) findViewById(R.id.tv_ingredients1);
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        String[] ingred = cookIndex.getIngredients().split(";");
        String[] burden = cookIndex.getBurden().split(";");
        if (ingred.length > 0 && !ingred[0].startsWith("无")) {
            Collections.addAll(ingredients, ingred);
        }
        if (burden.length > 0 && !burden[0].startsWith("无")) {
            Collections.addAll(ingredients, burden);
        }
        for (int i = 0; i < ingredients.size(); i++) {
            if (i % 2 != 0) {
                sb1.append(ingredients.get(i) + "\n");
            } else {
                sb.append(ingredients.get(i) + "\n");
            }
        }
        if (cookIndex.getIntro().length() > 1) {
            tv_intro.setVisibility(View.VISIBLE);
            tv_intro.setText("        "+cookIndex.getIntro());
        } else {
            tv_intro.setVisibility(View.GONE);
        }
        sv_view.setOnScrollListener(this);
        tv_ingredients.setText(sb.toString());
        tv_ingredients1.setText(sb1.toString());
        cookIndex.__setDaoSession(DBHelper.getDaoSession());
        cjl_steps.setAdapter(new CookBookStepsAdapter(cookIndex.getSteps(), this));
        tv_title.setText(cookIndex.getTitle());
        tv_top_title.setText(cookIndex.getTitle());
        ArrayList<String> taglists = new ArrayList<>();
        if (cookIndex.getTags().length() > 1) {
            Collections.addAll(taglists, cookIndex.getTags().split(";"));
        } else {
            taglists.add("快手菜");
        }
        tv_tags.setAdapter(new TagBaseAdapter(this, taglists));
        Glide.with(this).load(cookIndex.getAlbums()).placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(iv_image);
        iv_like.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        cjl_steps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showImage(position);
            }
        });
        isCollected();
    }

    //是否收藏
    private void isCollected() {
        list = DBHelper.getDaoSession().getCookIndexDao().queryBuilder().where(CookIndexDao.Properties.Id.eq(cookIndex.getId())).list();
        stepList = DBHelper.getDaoSession().getStepDao().queryBuilder().where(StepDao.Properties.IndexId.eq(cookIndex.getId())).list();
        if (list.size() > 0) {
            iv_like.setImageResource(R.drawable.icon_like);
        } else {
            iv_like.setImageResource(R.drawable.icon_nolike);
        }
    }

    private void showImage(int position) {
        if (imageDetailDialog == null) {
            imageDetailDialog = new ImageDetailDialog(this, cookIndex.getSteps());
        }
        imageDetailDialog.setPosition(position);
        imageDetailDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_like:
                if (list.size() > 0) {
                    DBHelper.getDaoSession().getCookIndexDao().deleteInTx(list);
                    DBHelper.getDaoSession().getStepDao().deleteInTx(stepList);
                    ToastUtil.showShort("取消收藏");
                    isCollected();
                } else {
                    DBHelper.getDaoSession().getCookIndexDao().insertOrReplace(cookIndex);
                    for (int i = 0; i < cookIndex.getSteps().size(); i++) {
                        Step step = cookIndex.getSteps().get(i);
                        step.setIndexId(cookIndex.getId());
                        DBHelper.getDaoSession().getStepDao().insertOrReplace(step);
                    }
                    ToastUtil.showShort("已收藏");
                    isCollected();
                }
                EventBus.getDefault().post(new MyEventBus(), "TAG_SEARCH");
                break;
        }
    }

    @Override
    public void onScroll(int scrollY) {
        int height = rl_top.getHeight();
        float fraction = (float) scrollY / height;
        if (scrollY > height) {
            rl_top.setBackgroundColor(Color.parseColor("#ffffffff"));
        } else if (scrollY < 0) {
            rl_top.setBackgroundColor(Color.parseColor("#22ffffff"));
        }
        if (scrollY > DensityUtil.dip2px(this, 240)) {
            tv_top_title.setVisibility(View.VISIBLE);
        } else {
            tv_top_title.setVisibility(View.INVISIBLE);
        }
        if (fraction <= 1 && scrollY > 0) {
            int filtrate_bg = ToolUtils.evaluateColor(fraction, 0x22ffffff, 0xffffffff);
            rl_top.setBackgroundColor(filtrate_bg);
        }
    }
}
