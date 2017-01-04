package com.ping.cookbook.module.cookbook.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ping.cookbook.R;
import com.ping.cookbook.base.BaseFragment;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.contract.Recommend_Contract;
import com.ping.cookbook.module.cookbook.adapter.CookBookListAdapter;
import com.ping.cookbook.presenter.Recommend_Presenter;
import com.ping.cookbook.widgets.SpacesItemDecoration;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.BannerView;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ping.Lib.Utils.NetUtil;
import ping.Lib.Utils.ToastUtil;
import ping.Lib.widgets.recyclerrefreshlayout.LoadOrRefreshView;


public class Fragment_cookbookInstance extends BaseFragment implements Recommend_Contract.View, LoadOrRefreshView.PullCallBack {
    private Recommend_Contract.Presenter presenter;
    private LoadOrRefreshView refresh_layout;
    private RecyclerView recycler_view;
    private CookBookListAdapter adapter;
    private ViewGroup layout;
    private int Postself;
    private List<CookIndex> mList = new ArrayList<CookIndex>();
    private long id;
    private int page = 1;

    public static Fragment_cookbookInstance newInstance(long id) {
        Fragment_cookbookInstance fragment = new Fragment_cookbookInstance();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments() == null ? savedInstanceState : getArguments();
        if (bundle != null) {
            id = bundle.getLong("id");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("id", id);
    }

    @Override
    public View onCreateViewProxy(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_cookbookinstance, container, false);
        new Recommend_Presenter(this);
        presenter.start();
        return layout;
    }

    @Override
    public void initView() {
        setToolbarVisible(View.GONE);
        refresh_layout = (LoadOrRefreshView) layout.findViewById(R.id.refresh_layout);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        refresh_layout.setLayoutManager(layoutManager);
        recycler_view = refresh_layout.getRecyclerView();
        recycler_view.setHasFixedSize(true);
        recycler_view.addItemDecoration(new SpacesItemDecoration(getActivity()));
        BannerView banner = new BannerView(getActivity(), ADSize.BANNER, "1105752382", "6020512577172164");
        adapter = new CookBookListAdapter(getActivity(), banner);
        recycler_view.setHasFixedSize(true);
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
            presenter.loadCookIndex(page, 10, id, false);
            refresh_layout.setIsLoadMoreEnable(false);
        } else {
            ToastUtil.showShort("网络异常");
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refresh_layout.setIsLoadMoreEnable(true);
        presenter.loadCookIndex(page, 10, id, false);
    }

    @Override
    public void loadError(String msg) {
        mList = null;
        LoadEnd();
        if (msg.startsWith("java.io.EOFException: End of input at line") && Postself == 0) {
            presenter.loadCookIndex(page, 10, id, true);
            Postself = +1;
            return;
        } else {
            Postself = 0;
        }
        if (page != 1)
            refresh_layout.setIsLoadMoreEnable(false);
        if (adapter.getItemCount() <= 1) {
            showLayoutError(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideLayoutError();
                    showLoading(true);
                    presenter.loadCookIndex(page, 10, id, true);
                }
            });
        } else if (page != 1) {
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