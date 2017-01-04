package ping.Lib.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @describe 当前应用的信息
 * @time 2016-3-18
 */
public class AppUtil {

    /**
     * 获取当前应用 版本号 eg:3
     */
    public static int getVersionCode(Context context) {
        int code = 1;
        try {
            String packgeName = context.getPackageName();
            code = context.getPackageManager().getPackageInfo(packgeName, 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 获取当前应用 版本名字  eg:2.1
     */
    public static String getVersionName(Context context) {
        String name = null;
        try {
            String packgeName = context.getPackageName();
            name = context.getPackageManager().getPackageInfo(packgeName, 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取当前应用的名称
     */
    public static String getAppName(Context context) {
        String appName = null;
        try {
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
            appName = (String) pm.getApplicationLabel(appInfo);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appName;
    }

    /**
     * 获取当前应用包名
     */
    public static String getAppPackageName(Context context) {
        String packageName = null;
        try {
            packageName = context.getApplicationContext().getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageName;
    }
}
