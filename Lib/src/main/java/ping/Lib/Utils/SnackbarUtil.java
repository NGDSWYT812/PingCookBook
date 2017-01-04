package ping.Lib.Utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

/**
 * @author whping
 * @describe <Snackbar的帮助类>
 * @time 2016-7-14
 */
public class SnackbarUtil {

    private SnackbarUtil() {
    }

    public static Snackbar snackBar(View view, String message, int duration) {
        return Snackbar.make(view, message, duration);
    }

    public static void longShow(View view, String message) {
        snackBar(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void shortShow(View view, String message) {
        snackBar(view, message, Snackbar.LENGTH_SHORT).show();
    }
    public static void shortShow(View view, String message,int ImageResource) {
        Snackbar snackbar = snackBar(view, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        ImageView iconImage = new ImageView(CommonUtil.getApplicationContext());
        iconImage.setImageResource(ImageResource);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(iconImage, 0);
        snackbar.show();
    }

    public static void snackBarShow(View view, String message, int duration) {
        snackBar(view, message, duration).show();
    }

    public static void snackBarShow(View view, String message, int duration, Snackbar.Callback callback) {
        snackBar(view, message, duration).setCallback(callback).show();
    }

    public static void snackBarShow(View view, String message, int duration, int BackgroundResource) {
        Snackbar snackbar = snackBar(view, message, duration);
        snackbar.getView().setBackgroundResource(BackgroundResource);
        snackbar.show();
    }

    public static void snackBarShow(View view, String message, int duration, String BackgroundColorString) {
        Snackbar snackbar = snackBar(view, message, duration);
        snackbar.getView().setBackgroundColor(Color.parseColor(BackgroundColorString));
        snackbar.show();
    }

    public static void snackBarShow(View view, String message, int duration, String BackgroundColorString, int ImageResource) {
        Snackbar snackbar = snackBar(view, message, duration);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor(BackgroundColorString));
        ImageView iconImage = new ImageView(CommonUtil.getApplicationContext());
        iconImage.setImageResource(ImageResource);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(iconImage, 0);
        snackbar.show();
    }

    public static void snackBarShow(View view, String message, String actionText, int duration, int ActionTextColor, int BackgroundResource, int ImageResource, View.OnClickListener listener) {
        Snackbar snackbar = snackBar(view, message, duration).setActionTextColor(ActionTextColor).setAction(actionText, listener);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(BackgroundResource);
        ImageView iconImage = new ImageView(CommonUtil.getApplicationContext());
        iconImage.setImageResource(ImageResource);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(iconImage, 0);
        snackbar.show();
    }

    public static void snackBarShow(View view, String message, String actionText, int duration, String ActionTextColorString, String BackgroundColorString, int ImageResource, View.OnClickListener listener) {
        Snackbar snackbar = snackBar(view, message, duration).setActionTextColor(Color.parseColor(ActionTextColorString)).setAction(actionText, listener);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor(BackgroundColorString));
        ImageView iconImage = new ImageView(CommonUtil.getApplicationContext());
        iconImage.setImageResource(ImageResource);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(iconImage, 0);
        snackbar.show();
    }

    public static void snackBarShow(View view, String message, String actionText, int duration, int ActionTextColor, int BackgroundResource, int ImageResource, View.OnClickListener listener, Snackbar.Callback callback) {
        Snackbar snackbar = snackBar(view, message, duration).setActionTextColor(ActionTextColor).setAction(actionText, listener).setCallback(callback);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(BackgroundResource);
        ImageView iconImage = new ImageView(CommonUtil.getApplicationContext());
        iconImage.setImageResource(ImageResource);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(iconImage, 0);
        snackbar.show();
    }

    public static void snackBarShow(View view, String message, String actionText, int duration, String ActionTextColorString, String BackgroundColorString, int ImageResource, View.OnClickListener listener, Snackbar.Callback callback) {
        Snackbar snackbar = snackBar(view, message, duration).setActionTextColor(Color.parseColor(ActionTextColorString)).setAction(actionText, listener).setCallback(callback);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor(BackgroundColorString));
        ImageView iconImage = new ImageView(CommonUtil.getApplicationContext());
        iconImage.setImageResource(ImageResource);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(iconImage, 0);
        snackbar.show();
    }
}
