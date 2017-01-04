package com.ping.cookbook.Retrofit;


public interface ResultListener<T> {
    void onSuccess(T... result);

    void onError(String msg);
}