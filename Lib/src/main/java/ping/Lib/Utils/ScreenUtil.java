package ping.Lib.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author 温华平
 * @Description <获取手机的测量值>
 * @date 2015-6-15
 */
public class ScreenUtil {
    public static DisplayMetrics getWindowDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return Resources.getSystem().getDisplayMetrics();
    }

    //获取屏幕的高度
    public static int getDisplayHeight() {
        return getWindowDisplayMetrics(CommonUtil.getApplicationContext()).heightPixels;
    }

    //获取屏幕的宽度
    public static int getDisplayWidth() {
        return getWindowDisplayMetrics(CommonUtil.getApplicationContext()).widthPixels;
    }
}
