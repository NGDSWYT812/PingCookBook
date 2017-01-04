package com.ping.cookbook.task;

public interface Completion<T> {
    void onSuccess(T result);
    void onError(Exception e);
}
