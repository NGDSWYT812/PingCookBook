package com.ping.cookbook.task;

public interface BackgroundWork<T> {
    T doInBackground() throws Exception;
}
