package ping.Lib.GlideHelper;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * @author 温华平
 * @describe glide自定义glidemodule
 * @time 2016-3-18
 */
public class GlideConfiguration implements GlideModule {
  
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setMemoryCache(new LruResourceCache(GlideCatchConfig.MemoryCacheSize(context)));//内存缓存大小
        builder.setBitmapPool(new LruBitmapPool(GlideCatchConfig.BitmapPoolSize(context)));//bitmap 的缓存池大小
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,GlideCatchConfig.GLIDE_CARCH_DIR,150000000));//设置硬盘缓存大小
    }
  
    @Override
    public void registerComponents(Context context, Glide glide) {
//        glide.register(String.class, InputStream.class, new ProgressModelLoader.Factory());
    }
}