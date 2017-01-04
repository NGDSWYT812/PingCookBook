package ping.Lib.Utils;

import android.util.Log;

/**
 * @describe <log>
 * @time 2016-3-17
 */
public class LogUtil {
    private static final String TAG = "PLOG";

    public static void e(String msg) {
        if (DebugUtil.isDebug) {// 如果当前设置成Debug模式
            Log.e(TAG, msg);
        }
    }

    public static void e(int msg) {
        if (DebugUtil.isDebug) {// 如果当前设置成Debug模式
            Log.e(TAG, msg + "");
        }
    }

    public static void e(boolean b) {
        if (DebugUtil.isDebug) {// 如果当前设置成Debug模式
            String msg = "false";
            if (b) msg = "true";
            Log.e(TAG, msg);
        }
    }

    public static void e(String[] ary) {
        if (DebugUtil.isDebug) {// 如果当前设置成Debug模式
            String msg = "";
            for (String string : ary) {
                msg += string;
            }
            Log.e(TAG, msg);
        }
    }

    public static void e(Object obj) {
        if (DebugUtil.isDebug) {// 如果当前设置成Debug模式
            Log.e(TAG, obj.toString());
        }
    }
}
