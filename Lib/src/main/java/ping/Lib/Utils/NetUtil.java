package ping.Lib.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * @author 温华平
 * @describe <检测网络>
 * @time 2016-3-17
 */
public class NetUtil {

    /**
     * 判断网络是否可用
     */
    public static boolean networkEnable() {
        // 网络管理对象
        ConnectivityManager cm = (ConnectivityManager) CommonUtil.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取所有类型的链接管理对象
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * @param WIFI是否连接
     * @return
     */
    public static boolean isWifiConnected() {
        // 网络管理对象
        ConnectivityManager cm = (ConnectivityManager) CommonUtil.getApplicationContext().getSystemService(Context
				.CONNECTIVITY_SERVICE);
        // 获取所有类型的链接管理对象
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        State state = info.getState();
        if (State.CONNECTED == state) {
            return true;
        }
        return false;
    }

    /**
     * @param 网络连接类型
     * @return
     */
    public static String getNetType() {
        String netType = null;
        ConnectivityManager manager = (ConnectivityManager) CommonUtil.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (networkEnable()) {
            NetworkInfo activeInfo = manager.getActiveNetworkInfo();
            netType = activeInfo.getTypeName();
        }
        return netType;
    }
}
