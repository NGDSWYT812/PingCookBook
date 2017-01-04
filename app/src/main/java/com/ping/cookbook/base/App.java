package com.ping.cookbook.base;

import android.app.Activity;

import java.util.ArrayList;

import ping.Lib.BaseApplication;
import ping.Lib.Utils.CommonUtil;


public class App extends BaseApplication {
    private ArrayList<Activity> activities = new ArrayList<Activity>();
    private static App INSTANCE;

    public static App getInstance() {
        if (INSTANCE == null) {
            synchronized (App.class) {
                if (INSTANCE == null)
                    INSTANCE = new App();
            }
        }
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        CommonUtil.setContext(getApplicationContext());
//        UmengHelper.initUM();
    }

    public void pushActivity(Activity activity) {
        try {
            activities.add(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void popActivity() {
        try {
            activities.remove(activities.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 移除当前activity之前所有的activity，并finish
    public void removeBefore() {
        if (activities.isEmpty()) {
            return;
        }
        try {
            Activity lastActivity = activities.remove(activities.size() - 1);

            int size = activities.size();
            for (int i = size - 1; i >= 0; i--) {
                Activity temp = activities.get(i);
                temp.finish();
            }
            activities.clear();
            activities.add(lastActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
