package com.ping.cookbook.task;
import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.Executor;


public final class TinyTasks {

    private TinyTasks() { throw new UnsupportedOperationException(); }

    public static <T> void perform( BackgroundWork<T> backgroundWork, Completion<T> completion) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            new GenericTask<T>(backgroundWork, completion).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else {
            new GenericTask<T>(backgroundWork, completion).execute();
        }

    }

    public static <T> void perform(BackgroundWork<T> backgroundWork, Completion<T> completion, Executor executor) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            new GenericTask<T>(backgroundWork, completion).executeOnExecutor(executor);
        }
        else {
            new GenericTask<T>(backgroundWork, completion).execute();
        }
    }

}
