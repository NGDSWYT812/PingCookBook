package com.ping.cookbook.Retrofit;

import com.ping.cookbook.bean.BaseBean;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.bean.CookList;

import java.util.HashMap;
import java.util.Map;

import ping.Lib.Utils.LogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lhy on 2016/3/29.
 */
public class NetWorkManager {
    private static NetWorkManager INSTANCE = null;

    private NetWorkManager() {
    }

    public static NetWorkManager getInstance() {
        if (INSTANCE == null) {
            synchronized (NetWorkManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetWorkManager();
                }
            }
        }
        return INSTANCE;
    }

    public void getCookList(final ResultListener resultListener) {
        RetrofitService.getInstance().getCookList(RequestCode.APIKEY).enqueue(new Callback<BaseBean<CookList>>() {
            @Override
            public void onResponse(Call<BaseBean<CookList>> call, Response<BaseBean<CookList>> response) {
                BaseBean<CookList> cookList = response.body();
                if (cookList != null && cookList.getError_code() == 0) {
                    resultListener.onSuccess(cookList.getResult());
                } else{
                    resultListener.onError("加载失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<CookList>> call, Throwable t) {
                resultListener.onError("加载失败");
            }
        });
    }

    public void getCookIndex(int page, int rn, long cid, final ResultListener resultListener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", RequestCode.APIKEY);
        map.put("pn", "" + page);//数据返回起始下标，默认0
        map.put("rn", "" + rn); //数据返回条数，最大30，默认10
        map.put("cid", "" + cid);
        RetrofitService.getInstance().getCookIndex(map).enqueue(new Callback<BaseBean<CookIndex>>() {
            @Override
            public void onResponse(Call<BaseBean<CookIndex>> call, Response<BaseBean<CookIndex>> response) {
                BaseBean<CookIndex> cookList = response.body();
                if (cookList != null && cookList.getError_code() == 0) {
                    resultListener.onSuccess(cookList.getResult());
                } else{
                    resultListener.onError("加载失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<CookIndex>> call, Throwable t) {
                resultListener.onError(t.toString());
            }
        });
    }
    public void postCookIndex(int page, int rn, long cid, final ResultListener resultListener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", RequestCode.APIKEY);
        map.put("pn", "" + page);//数据返回起始下标，默认0
        map.put("rn", "" + rn); //数据返回条数，最大30，默认10
        map.put("cid", "" + cid);
        RetrofitService.getInstance().postCookIndex(map).enqueue(new Callback<BaseBean<CookIndex>>() {
            @Override
            public void onResponse(Call<BaseBean<CookIndex>> call, Response<BaseBean<CookIndex>> response) {
                BaseBean<CookIndex> cookList = response.body();
                if (cookList != null && cookList.getError_code() == 0) {
                    resultListener.onSuccess(cookList.getResult());
                } else{
                    resultListener.onError("加载失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<CookIndex>> call, Throwable t) {
                resultListener.onError("加载失败");
                LogUtil.e(t.toString());
            }
        });
    }

    public void SearchCook(int page, int rn, String menu, final ResultListener resultListener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", RequestCode.APIKEY);
        map.put("pn", "" + page);//数据返回起始下标，默认0
        map.put("rn", "" + rn); //数据返回条数，最大30，默认10
        map.put("menu", menu);
        RetrofitService.getInstance().SearchCook(map).enqueue(new Callback<BaseBean<CookIndex>>() {
            @Override
            public void onResponse(Call<BaseBean<CookIndex>> call, Response<BaseBean<CookIndex>> response) {
                BaseBean<CookIndex> cookList = response.body();
                if (cookList != null && cookList.getError_code() == 0) {
                    resultListener.onSuccess(cookList.getResult());
                } else{
                    resultListener.onError("加载失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<CookIndex>> call, Throwable t) {
                resultListener.onError("加载失败");
            }
        });
    }

}
