package com.ping.cookbook.base;

public interface BaseView<T> {
    void setPresenter(T presenter);
    void initView();
}