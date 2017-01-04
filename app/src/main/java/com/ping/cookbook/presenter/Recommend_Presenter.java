package com.ping.cookbook.presenter;

import com.ping.cookbook.Retrofit.NetWorkManager;
import com.ping.cookbook.Retrofit.ResultListener;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.contract.Recommend_Contract;

import java.util.List;

public class Recommend_Presenter implements Recommend_Contract.Presenter {
    private Recommend_Contract.View view;

    public Recommend_Presenter(Recommend_Contract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.initView();
    }

    @Override
    public void loadCookIndex(int page, int rn, long cid, boolean isPost) {
        if (isPost) {
            NetWorkManager.getInstance().postCookIndex(page, rn, cid, new ResultListener() {
                @Override
                public void onSuccess(Object[] result) {
                    view.loadSuccess((List<CookIndex>) result[0]);
                }

                @Override
                public void onError(String msg) {
                    view.loadError("没有更多了");
                }
            });
        } else {
            NetWorkManager.getInstance().getCookIndex(page, rn, cid, new ResultListener() {
                @Override
                public void onSuccess(Object[] result) {
                    view.loadSuccess((List<CookIndex>) result[0]);
                }

                @Override
                public void onError(String msg) {
                    view.loadError(msg);
                }
            });
        }
    }
}
