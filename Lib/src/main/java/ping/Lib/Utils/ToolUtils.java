package ping.Lib.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 温华平
 * @describe <常用工具类>
 * @time 2016-3-17
 */
public class ToolUtils {

    /**
     * 刷新时间
     */
    public static String refreshtime() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }


    /**
     * @describe 获取选定时间格式的毫秒
     * @format 例如：yyyy-M-d HH:mm:ss
     * @time 具体的时间
     */
    public static long timepulish(String format, String time) {
        long millisecond = 0;
        try {
            DateFormat formatter = new SimpleDateFormat(format);
            Date date = formatter.parse(time);
            millisecond = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millisecond;
    }


    /**
     * 秒转换成时间
     */
    public static String publishtimes(long time) {
        long currentTimeMillis = System.currentTimeMillis();
        long seconds = currentTimeMillis / 1000 - time;
        if (seconds < 60) {
            return "刚刚";
        } else if (seconds < 3600) {
            return seconds / 60 + "分钟前";
        } else if (seconds < 3600 * 24) {
            return seconds / 3600 + "小时前";
        } else if (seconds < 3600 * 24 * 2) {
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time * 1000);
            return "昨天" + formatter.format(calendar.getTime());
        } else {
            DateFormat formatter = new SimpleDateFormat("MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time * 1000);
            return formatter.format(calendar.getTime());
        }
    }

    /**
     * 打开键盘
     */
    public static void openKeyBoard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        View view = context.getCurrentFocus();
        if (view != null) {
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 关闭键盘
     */
    public static void closeKeyBoard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (imm.isActive() && context.getCurrentFocus() != null) {
            if (context.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 打卡软键盘
     */
    public static void openKeyBoard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭键盘
     */
    public static void closeKeyBoard(EditText et, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    /**
     * 保留小数点后2位
     */
    public static String getDecimal(double d) {
        DecimalFormat df2 = new DecimalFormat("0.00");
        return df2.format(d);
    }

    /**
     * EditText监听小数点让用户只能输入小数点后两位
     */
    public static void setDecimal(CharSequence s, EditText et, int shunum) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > shunum) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + shunum + 1);
                et.setText(s);
                et.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            et.setText(s);
            et.setSelection(2);
        }

        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                et.setText(s.subSequence(0, 1));
                et.setSelection(1);
                return;
            }
        }
    }

    /**
     * 加密手机号
     */
    public static String encryptPhoneNum(String phonenum) {
        StringBuilder sb = new StringBuilder();
        sb.append(phonenum.substring(0, 3));
        sb.append("****");
        sb.append(phonenum.substring(7, 11));
        return sb.toString();
    }

    /**
     * 判断手机号
     */
    public static boolean isPhone(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        boolean matches = m.matches();
        return matches;
    }

    /**
     * edittext内容是否为空
     */
    public static boolean etIsEmpty(EditText edit) {
        if ("".equals(edit.getText().toString().trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @describe 计算分类图片的高度
     * @padding 每行的padding（padding-left和padding-right总和）
     * @count 每行中有多少个图片
     */
    public static int getImageHeight(Context context, int padding, int count) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (int) (dm.widthPixels - padding * dm.density) / count;
    }

    /**
     * 判断url地址是否正确
     */
    public static boolean isurl(String utl) {
        Pattern pattern = Pattern.compile("^(http://|www.)(([a-zA-z0-9]|-){1,}\\.){1," + 
                "}[a-zA-z0-9]{1,}-*");
        Matcher matcher = pattern.matcher(utl);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断mail邮箱是否正确
     */
    public static boolean ismail(String mail) {
        Pattern pattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-" + "" +
                ".][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        Matcher matcher = pattern.matcher(mail);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * @describe 获取渐变颜色
     * @fraction 渐变的高度比例
     * @startValue 开始颜色
     * @endValue 结束颜色
     */
    public static Integer evaluateColor(float fraction, Object startValue, Integer endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) | (int) ((startR + 
                (int) (fraction * (endR - startR))) << 16) | (int) ((startG + (int) (fraction * 
                (endG - startG))) << 8) | (int) ((startB + (int) (fraction * (endB - startB))));
    }

    /**
     * @describe 简单的从资源 id 转换成 Uri。
     * @context 上下文
     * @resourceId 资源id
     */
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
