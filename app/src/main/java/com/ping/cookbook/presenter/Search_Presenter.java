package com.ping.cookbook.presenter;

import com.ping.cookbook.Retrofit.NetWorkManager;
import com.ping.cookbook.Retrofit.ResultListener;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.contract.Search_Contract;

import java.util.List;

public class Search_Presenter implements Search_Contract.Presenter {
    private Search_Contract.View view;

    public Search_Presenter(Search_Contract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.initView();
    }

    @Override
    public void search(int page, int rn, String menu) {
        NetWorkManager.getInstance().SearchCook(page, rn, menu, new ResultListener() {
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
