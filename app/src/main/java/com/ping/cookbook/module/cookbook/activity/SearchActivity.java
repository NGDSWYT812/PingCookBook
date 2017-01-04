package com.ping.cookbook.module.cookbook.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.ping.cookbook.R;
import com.ping.cookbook.base.BaseActivity;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.contract.Search_Contract;
import com.ping.cookbook.module.cookbook.adapter.SearchListAdapter;
import com.ping.cookbook.presenter.Search_Presenter;

import java.util.List;

import ping.Lib.Utils.NetUtil;
import ping.Lib.Utils.ToastUtil;
import ping.Lib.widgets.recyclerrefreshlayout.LoadOrRefreshView;


public class SearchActivity extends BaseActivity implements Search_Contract.View, View.OnClickListener, LoadOrRefreshView.PullCallBack {
    private Search_Contract.Presenter presenter;
    private LoadOrRefreshView refresh_layout;
    private RecyclerView recycler_view;
    private SearchListAdapter adapter;
    private View layoutLoading, layoutError, layoutEmpty;
    private List<CookIndex> mList;
    private int page = 1;
    private EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        new Search_Presenter(this);
        presenter.start();
    }

    @Override
    public void setPresenter(Search_Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initView() {
        setToolbarVisible(View.GONE);
        layoutLoading = findViewById(R.id.layoutLoading);
        layoutError = findViewById(R.id.layoutError);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        et_search = (EditText) findViewById(R.id.et_search);
        refresh_layout = (LoadOrRefreshView) findViewById(R.id.refresh_layout);
        recycler_view = refresh_layout.getRecyclerView();
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchListAdapter(this);
        recycler_view.setHasFixedSize(true);
        recycler_view.setAdapter(adapter);
        refresh_layout.setPullCallBack(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                hideInput();
                finish();
                break;
            case R.id.iv_search:
                hideInput();
                adapter.clearData();
                refresh_layout.setVisibility(View.GONE);
                layoutLoading.setVisibility(View.VISIBLE);
                onRefresh();
                break;
        }
    }

    private void ShowView() {
        layoutLoading.setVisibility(View.GONE);
        if (mList != null && mList.size() > 0) {
            refresh_layout.setVisibility(View.VISIBLE);
            if (page == 1) {
                adapter.setData(mList);
            } else {
                if (mList.size() < 15)
                    refresh_layout.setIsLoadMoreEnable(false);
                adapter.addData(mList);
            }
        } else if (adapter.getItemCount() <= 0) {
            layoutEmpty.setVisibility(View.VISIBLE);
            refresh_layout.setVisibility(View.GONE);
            layoutError.setVisibility(View.GONE);
        } else {
            ToastUtil.showShort("没有更多了");
        }
        refresh_layout.setRefreshing(false);
        refresh_layout.setLoadComplete();

    }

    @Override
    public void onLoadMore() {
        LoadData(true);
    }

    @Override
    public void onRefresh() {
        LoadData(false);
    }

    private void LoadData(boolean isload) {
        String search = et_search.getText().toString().trim();
        if (search.length() > 0) {
            layoutEmpty.setVisibility(View.GONE);
            layoutError.setVisibility(View.GONE);
            if (NetUtil.networkEnable()) {
                if (isload) {
                    page = page + 1;
                } else {
                    page = 1;
                    refresh_layout.setIsLoadMoreEnable(true);
                }
                presenter.search(page,15,search);
            } else {
                layoutLoading.setVisibility(View.GONE);
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoadComplete();
                if (adapter.getItemCount() <= 0) {
                    refresh_layout.setVisibility(View.GONE);
                    layoutError.setVisibility(View.VISIBLE);
                    layoutError.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            layoutLoading.setVisibility(View.VISIBLE);
                            refresh_layout.setVisibility(View.GONE);
                            layoutError.setVisibility(View.GONE);
                            LoadData(false);
                        }
                    });
                } else {
                    ToastUtil.showShort("网络异常");
                }
            }
        } else {
            ToastUtil.showShort("请输入食谱");
            layoutLoading.setVisibility(View.GONE);
            refresh_layout.setRefreshing(false);
            refresh_layout.setLoadComplete();
        }
    }

    @Override
    public void loadError(String msg) {
        mList = null;
        ShowView();
        refresh_layout.setIsLoadMoreEnable(false);
    }

    @Override
    public void loadSuccess(List<CookIndex> list) {
        if (mList != null)
            mList.clear();
        this.mList = list;
        ShowView();
    }
}
