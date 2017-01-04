package ping.Lib.Utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @describe <assets文件夹 操作辅助类>
 * @time 2016-3-18
 */
public class AssetsUtil {
	/**
	 * 获取Assets文件
	 * 
	 * @param path
	 *            只需输入asset下相对路径即可 ，file:///android_asset/weberror.html
	 *            则输入weberror.html
	 * @param context
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromAssets(String path, Context context) throws IOException {
		AssetManager assetManager = context.getAssets();
		InputStream inputStream = assetManager.open(path);
		return inputStream2String(inputStream);
	}

	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/**
	 * clear the cache before time numDays
	 * 
	 * @param dir：缓存目录，Activity.getCacheDir() 系统的
	 * @param numDays 要删除的文件时间戳 System.currentTimeMillis()现在之前的
	 * @return
	 */
	public static int clearCacheFolder(File dir, long numDays) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, numDays);
					}
					if (child.lastModified() < numDays) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}
}
