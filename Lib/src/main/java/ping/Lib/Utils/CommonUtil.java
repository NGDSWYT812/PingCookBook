package ping.Lib.Utils;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;

/**
 * @describe <获取上下文>
 * @time 2016-3-18
 */
public class CommonUtil {
    private static long lastClickTime;
	private static WeakReference<Context> contextRef;

	public static void setContext(Context context) {
		if (context == null) {
			throw new InvalidParameterException("Invalid context parameter!");
		}

		Context appContext = context.getApplicationContext();
		contextRef = new WeakReference<Context>(appContext);
	}

	public static Context getApplicationContext() {
		Context context = contextRef.get();
		if (context == null) {
			throw new InvalidParameterException("Context parameter not set!");
		} else {
			return context;
		}
	}


    public static boolean isNull(Object dest)
    {
        boolean result = true;
        if (dest != null);
        try
        {
            if (!"".equals(dest.toString().replaceAll(" ", "")))
            {
                if (!("null".equals(dest.toString())))
                    result = false;
            }
            return result;
        }
        catch (Exception localException)
        {
        }
        return result;
    }


    public static boolean isRepeatedClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 600) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
