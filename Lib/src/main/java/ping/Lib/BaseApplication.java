package ping.Lib;

import android.app.Application;

import ping.Lib.Utils.CommonUtil;


public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CommonUtil.setContext(getApplicationContext());
    }
}
