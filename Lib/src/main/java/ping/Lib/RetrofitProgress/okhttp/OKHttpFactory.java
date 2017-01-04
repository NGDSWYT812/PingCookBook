package ping.Lib.RetrofitProgress.okhttp;


import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ping.Lib.Utils.CommonUtil;


public class OKHttpFactory {
    private static OKHttpFactory INSTANCE;
    private final OkHttpClient okHttpClient;
    private static final int TIMEOUT_READ = 25;
    private static final int TIMEOUT_CONNECTION = 25;

    public static OKHttpFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (OKHttpFactory.class) {
                if (INSTANCE == null)
                    INSTANCE = new OKHttpFactory();
            }
        }
        return INSTANCE;
    }

    private OKHttpFactory() {
        //打印请求Log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //缓存目录
        Cache cache = new Cache(CommonUtil.getApplicationContext().getCacheDir(), 100 * 1024 * 1024);

        okHttpClient = new OkHttpClient.Builder()
                //打印请求log
                .addInterceptor(interceptor)

                //必须是设置Cache目录
                .cache(cache)

                //走缓存，两个都要设置
                .addInterceptor(new OnOffLineCachedInterceptor())
                .addNetworkInterceptor(new OnOffLineCachedInterceptor())

                //失败重连
                .retryOnConnectionFailure(true)

                //time out
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)

                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}