package com.ping.cookbook.module.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ping.cookbook.R;
import com.ping.cookbook.base.BaseActivity;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.dbHelper.DBHelper;
import com.ping.cookbook.module.cookbook.widgets.MyEventBus;
import com.ping.cookbook.module.mine.adapter.CollectListAdapter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;


public class CollectActivity extends BaseActivity {
    private View layoutEmpty;
    private RecyclerView recycler_view;
    private CollectListAdapter adapter;
    private List<CookIndex> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initViews();
        initData();
    }

    private void initData() {
        mList = DBHelper.getDaoSession().getCookIndexDao().loadAll();
        if (mList.size() > 0) {
            layoutEmpty.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            adapter.setData(mList);
        } else {
            recycler_view.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
        setToolbarVisible(View.GONE);
        EventBus.getDefault().register(this);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        TextView emptycontent = (TextView) findViewById(R.id.emptycontent);
        emptycontent.setText("暂无收藏");
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new CollectListAdapter(this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setHasFixedSize(true);
        recycler_view.setAdapter(adapter);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = "TAG_SEARCH")
    public void search(MyEventBus events) {
        if (adapter != null)
            initData();
    }
}
