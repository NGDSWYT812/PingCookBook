package com.ping.cookbook.contract;


import com.ping.cookbook.base.BasePresenter;
import com.ping.cookbook.base.BaseView;
import com.ping.cookbook.bean.CookIndex;

import java.util.List;

public interface Search_Contract {
    interface View extends BaseView<Presenter> {
        void loadError(String msg);

        void loadSuccess(List<CookIndex> mList);
    }

    interface Presenter extends BasePresenter {
        void search(int page, int rn, String menu);
    }
}