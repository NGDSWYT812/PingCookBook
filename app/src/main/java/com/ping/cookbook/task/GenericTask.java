package com.ping.cookbook.task;

public class GenericTask<T> extends AbstractTask<T> {

    private Completion<T> completion;
    GenericTask(BackgroundWork<T> backgroundWork, Completion<T> completion) {
        super(backgroundWork);
        this.completion = completion;
    }

    @Override
    protected void onSuccess(T result) {
        if (completion != null) {
            completion.onSuccess(result);
        }
    }

    @Override
    protected void onError(Exception e) {
        if (completion != null) {
            completion.onError(e);
        }
    }
}
