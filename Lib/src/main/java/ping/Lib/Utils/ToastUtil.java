package ping.Lib.Utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Toast统一管理类
 *
 */
public class ToastUtil {
    // Toast
    private static Toast toast;
    private final static  Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(final CharSequence message) {
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if (null == toast) {
                            toast = Toast.makeText(CommonUtil.getApplicationContext(), message, Toast.LENGTH_SHORT);
                        } else {
                            toast.setText(message);
                        }
                        toast.show();
                    }
                }
        );
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(final int message) {
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if (null == toast) {
                            toast = Toast.makeText(CommonUtil.getApplicationContext(), message, Toast.LENGTH_LONG);
                        } else {
                            toast.setText(message);
                        }
                        toast.show();
                    }
                }
        );

    }


    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(final CharSequence message) {
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if (null == toast) {
                            toast = Toast.makeText(CommonUtil.getApplicationContext(), message, Toast.LENGTH_LONG);
                        } else {
                            toast.setText(message);
                        }
                        toast.show();
                    }
                }
        );

    }



    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(final int message) {
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if (null == toast) {
                            toast = Toast.makeText(CommonUtil.getApplicationContext(), message, Toast.LENGTH_LONG);
                        } else {
                            toast.setText(message);
                        }
                        toast.show();
                    }
                }
        );
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(final CharSequence message,final int duration) {
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if (null == toast) {
                            toast = Toast.makeText(CommonUtil.getApplicationContext(), message, duration);
                        } else {
                            toast.setText(message);
                        }
                        toast.show();
                    }
                }
        );
    }



    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(final int message,final int duration) {
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if (null == toast) {
                            toast = Toast.makeText(CommonUtil.getApplicationContext(), message, duration);
                            // toast.setGravity(Gravity.CENTER, 0, 0);
                        } else {
                            toast.setText(message);
                        }
                        toast.show();
                    }
                }
        );
    }

    /**
     * Hide the toast, if any.
     */
    public static void hideToast() {
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if (null != toast) {
                            toast.cancel();
                        }
                    }
                }
        );
    }
}
