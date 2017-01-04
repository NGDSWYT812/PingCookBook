package com.ping.cookbook.Retrofit;


import com.ping.cookbook.bean.BaseBean;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.bean.CookList;

import java.util.Map;

import ping.Lib.RetrofitProgress.okhttp.OKHttpFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * ClassName RetrofitService
 * description 静态内部类的单例模式
 * author  wenhuaping
 * date createTime：2016/03/11
 * version
 */
public class RetrofitService {
    private ApiService apiService;

    private static final class LazyHolder {
        private static final RetrofitService retrofitService = new RetrofitService();
    }

    public static RetrofitService getInstance() {
        return LazyHolder.retrofitService;
    }

    public RetrofitService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apis.haoservice.com/lifeservice/cook/").addCallAdapterFactory(RxJavaCallAdapterFactory.create())
               /* .addConverterFactory(new NullOnEmptyConverterFactory())*/.addConverterFactory(GsonConverterFactory.create()).client(OKHttpFactory.getInstance().getOkHttpClient()).build();
        apiService = retrofit.create(ApiService.class);
    }

    public interface ApiService {
        @GET("category")
        Call<BaseBean<CookList>> getCookList(@Query("key") String key);

        @Headers("Cache-Control: public, max-age=86400")
        @GET("index")
        Call<BaseBean<CookIndex>> getCookIndex(@QueryMap Map<String, String> map);

        @POST("index")
        @FormUrlEncoded
        Call<BaseBean<CookIndex>> postCookIndex(@FieldMap Map<String, String> map);

        @Headers("Cache-Control: public, max-age=86400")
        @GET("query")
        Call<BaseBean<CookIndex>> SearchCook(@QueryMap Map<String, String> map);
    }
    public Call<BaseBean<CookList>> getCookList(String key) {
        return apiService.getCookList(key);
    }
    public Call<BaseBean<CookIndex>> getCookIndex(Map<String, String> map) {
        return apiService.getCookIndex(map);
    }
    public Call<BaseBean<CookIndex>> postCookIndex(Map<String, String> map) {
        return apiService.postCookIndex(map);
    }
    public Call<BaseBean<CookIndex>> SearchCook(Map<String, String> map) {
        return apiService.SearchCook(map);
    }
}