package ping.Lib.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

/**
 * @describe <获取设备信息，操作系统信息>
 * @time 2016-3-18
 */
public class DeviceUtil {

    /**
     * 获取 国际移动设备身份码(设备唯一识别码)
     */
    public static String getIMEI(Context context) {
        String imei = null;
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = manager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            imei = "";
        }
        return imei;
    }

    /**
     * 获取 国际移动用户识别码(国际上为唯一识别一个移动用户所分配的号码)
     */
    public static String getIMSI(Context context) {
        String imsi = null;
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context
					.TELEPHONY_SERVICE);
            imsi = manager.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
            imsi = "";
        }
        return imsi;
    }

    /**
     * 获取 用户手机号码(有的手机获取不到)
     */
    public static String getNumber(Context context) {
        String number = null;
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context
					.TELEPHONY_SERVICE);
            number = manager.getLine1Number();
            if (number == null) {
                number = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            number = "";
        }

        return number;
    }

    /**
     * 获取 用户手机型号
     */
    public static String getType(Context context) {
        return android.os.Build.MODEL;
    }

    /**
     * 获取 用户手机品牌
     */
    public static String getBrand(Context context) {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取 设备搭载操作系统版本号
     */
    public static String getSystemVer() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getUUID(Context context) {
        UUIDFactory uuid = new UUIDFactory(context);
        return uuid.getDeviceUuid().toString();
    }

    /**
     * 判断设备是否安装了某个应用
     *
     * @param appPackageName要查询的应用包名
     * @return true:已经安装
     */
    public static boolean isAppInstalled(Context context, String appPackageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(appPackageName, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
}
