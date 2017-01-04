package com.ping.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ping.cookbook.Retrofit.NetWorkManager;
import com.ping.cookbook.Retrofit.ResultListener;
import com.ping.cookbook.bean.CookList;
import com.ping.cookbook.dbHelper.DBHelper;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import ping.Lib.Utils.NetUtil;
import ping.Lib.Utils.SnackbarUtil;


public class SplashActivity extends AppCompatActivity {
    private AlphaAnimation alphaAnimation;
    private TextView splash;
    private FrameLayout splash_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = (TextView) findViewById(R.id.app);
        splash_ad = (FrameLayout) findViewById(R.id.splash_ad);
        List<CookList> lists = DBHelper.getDaoSession().getCookListDao().loadAll();
        if (lists.size() == 0 || (lists.size() > 0 && (System.currentTimeMillis() - lists.get(0).getSaveTime() > 259200000))) {
            NetWorkManager.getInstance().getCookList(resultListener);
        } else if (NetUtil.networkEnable()) {
            initAD();
        } else {
            showAlpha();
        }
    }

    private void initAD() {
        new SplashAD(this, splash_ad, "1105752382", "1020417527274105", new SplashADListener() {
            @Override
            public void onADDismissed() {
                next();
            }

            @Override
            public void onNoAD(int i) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                next();
            }

            @Override
            public void onADPresent() {
            }

            @Override
            public void onADClicked() {
            }
        });
    }


    // 默认开机动画
    private void showAlpha() {
        // 启动动画
        alphaAnimation = new AlphaAnimation(1f, 1);
        splash.setAnimation(alphaAnimation);
        alphaAnimation.setDuration(2000);
        /**
         * 处理alphaAnimation动画完成时的跳转
         */
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                next();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    private void next() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        finish();
    }

    //网络请求结果
    private ResultListener resultListener = new ResultListener() {
        @Override
        public void onSuccess(Object... result) {
            final List<CookList> cookLists = (List<CookList>) result[0];
            if (cookLists != null && cookLists.size() > 0) {
                splash.post(new Runnable() {
                    @Override
                    public void run() {
                        DeleteQuery<CookList> db = DBHelper.getDaoSession().getCookListDao().queryBuilder().buildDelete();
                        db.executeDeleteWithoutDetachingEntities();
                        for (int i = 0; i < cookLists.size(); i++) {
                            CookList clist = cookLists.get(i);
                            clist.setSaveTime(System.currentTimeMillis());
                            DBHelper.getDaoSession().getCookListDao().insertOrReplace(clist);
                        }
                        next();
                    }
                });
            }
        }

        @Override
        public void onError(String msg) {
            SnackbarUtil.longShow(splash, msg);
        }

    };

    //防止用户返回键退出APP
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
