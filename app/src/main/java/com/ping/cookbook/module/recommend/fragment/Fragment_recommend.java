package com.ping.cookbook.module.recommend.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ping.cookbook.R;
import com.ping.cookbook.base.BaseFragment;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.bean.CookList;
import com.ping.cookbook.contract.Recommend_Contract;
import com.ping.cookbook.dbHelper.DBHelper;
import com.ping.cookbook.module.recommend.adapter.RecommendListAdapter;
import com.ping.cookbook.presenter.Recommend_Presenter;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

import ping.Lib.Utils.NetUtil;
import ping.Lib.Utils.ToastUtil;
import ping.Lib.widgets.recyclerrefreshlayout.LoadOrRefreshView;
import ping.Lib.widgets.recyclerrefreshlayout.RecyclerRefreshLayout;


public class Fragment_recommend extends BaseFragment implements Recommend_Contract.View, RecyclerRefreshLayout.OnRefreshListener, LoadOrRefreshView.PullCallBack {
    private Recommend_Contract.Presenter presenter;
    private LoadOrRefreshView refresh_layout;
    private RecyclerView recycler_view;
    private RecommendListAdapter adapter;
    private List<CookIndex> mList = new ArrayList<CookIndex>();
    private List<CookList> lists;
    private int Postself;
    private int page = 1;
    private View contentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateViewProxy(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_recommend, container, false);
            new Recommend_Presenter(this);
            presenter.start();
        }
        return contentView;
    }

    @Override
    public void initView() {
        setToolbarVisible(View.GONE);
        lists = DBHelper.getDaoSession().getCookListDao().loadAll();
        refresh_layout = (LoadOrRefreshView) contentView.findViewById(R.id.refresh_layout);
        recycler_view = refresh_layout.getRecyclerView();
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        BannerView banner = new BannerView(getActivity(), ADSize.BANNER, "1105752382", "6020512577172164");
        adapter = new RecommendListAdapter(getActivity(), banner);
        recycler_view.setAdapter(adapter);
        refresh_layout.setPullCallBack(this);
        showLoading(true);
        onRefresh();
    }

    private void LoadEnd() {
        showLoading(false);
        refresh_layout.setRefreshing(false);
        refresh_layout.setLoadComplete();
    }

    @Override
    public void onLoadMore() {
        if (NetUtil.networkEnable()) {
            page = page + 1;
            presenter.loadCookIndex(page, 15, lists.get(0).getId(),false);
            refresh_layout.setIsLoadMoreEnable(false);
        } else {
            ToastUtil.showShort("网络异常");
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refresh_layout.setIsLoadMoreEnable(true);
        presenter.loadCookIndex(page, 15, lists.get(0).getId(),false);
    }

    @Override
    public void loadError(String msg) {
        mList = null;
        LoadEnd();
        if (msg.startsWith("java.io.EOFException: End of input at line")) {
            presenter.loadCookIndex(page, 15, lists.get(0).getId(),true);
            Postself = +1;
            return;
        } else {
            Postself = 0;
        }
        if (page != 1)
            refresh_layout.setIsLoadMoreEnable(false);
        if (adapter.getItemCount() <= 0) {
            showLayoutError(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideLayoutError();
                    showLoading(true);
                    presenter.loadCookIndex(page, 15, lists.get(0).getId(),true);
                }
            });
        } else if(page!=1){
            ToastUtil.showShort("没有更多了");
        }
    }

    @Override
    public void loadSuccess(List<CookIndex> list) {
        if (mList != null)
            mList.clear();
        this.mList = list;
        LoadEnd();
        Postself = 0;
        if (mList.size() > 0) {
            refresh_layout.setIsLoadMoreEnable(true);
            if (page == 1) {
                adapter.setData(mList);
            } else {
                adapter.addData(mList);
            }
        }
    }

    @Override
    public void setPresenter(Recommend_Contract.Presenter presenter) {
        this.presenter = presenter;
    }
}
