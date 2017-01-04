package ping.Lib.RetrofitProgress.okhttp;


import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ping.Lib.Utils.NetUtil;

public class OnOffLineCachedInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(!NetUtil.networkEnable()){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if(NetUtil.networkEnable()){
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }else{
            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale="+maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}