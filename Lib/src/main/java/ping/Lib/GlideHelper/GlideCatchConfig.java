package ping.Lib.GlideHelper;

import android.content.Context;

import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;

/**
 * Glide缓存配置文件
 */

public class GlideCatchConfig {

    public static int BitmapPoolSize(Context context){
        MemorySizeCalculator sizeCalculator = new MemorySizeCalculator(context);
        int bitmapPoolSize = sizeCalculator.getBitmapPoolSize();
        return bitmapPoolSize;
    };
    public static int MemoryCacheSize(Context context){
        MemorySizeCalculator sizeCalculator = new MemorySizeCalculator(context);
        int memoryCacheSize = sizeCalculator.getMemoryCacheSize();
        return memoryCacheSize;
    };

    // 图片缓存子目录
    public static final String GLIDE_CARCH_DIR = "cookbook_glide";

}