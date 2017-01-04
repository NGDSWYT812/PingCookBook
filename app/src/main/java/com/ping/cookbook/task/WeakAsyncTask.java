package com.ping.cookbook.task;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * User: wtf
 * Date: 2015-10-29 0029 13:43
 * Email: wtf@ylzinfo.com
 */
public abstract class WeakAsyncTask<Params, Progress, Result, WeakContext>
        extends AsyncTask<Params, Progress, Result> {
    protected WeakReference<WeakContext> mContext;

    public WeakAsyncTask(WeakContext context) {
        mContext = new WeakReference<WeakContext>(context);
    }

    @Override
    protected final void onPreExecute() {
        final WeakContext context = mContext.get();
        if (context != null) {
            this.onPreExecute(context);
        }
    }

    @Override
    protected final Result doInBackground(Params... params) {
        final WeakContext context = mContext.get();
        if (context != null) {
            return this.doInBackground(context, params);
        } else {
            return null;
        }
    }

    @Override
    protected final void onPostExecute(Result result) {
        final WeakContext context = mContext.get();
        if (context != null) {
            this.onPostExecute(context, result);
        }
    }

    protected void onPreExecute(WeakContext context) { }

    protected abstract Result doInBackground(WeakContext context, Params... params);

    protected void onPostExecute(WeakContext context, Result result) {

    }
}
