package ping.Lib.Utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * @describe <SDCard 操作辅助类>
 * @author 温华平
 * @time 2014-2-12
 */
public class SDCardUtil {

	/**
	 * /sdcard/Android/data/应用包/files(或者cache)/ 下创建文件夹
	 * 
	 * @param context
	 * @param fileName
	 *            (要创建的文件夹名称)
	 * @param fileOrCache
	 *            true:选择files，false:选择Cache
	 * @return
	 */
	public static File createDirInPacket(Context context, String fileName, boolean fileOrCache) {
		File file = null;
		if (fileOrCache) {
			file = getExternalFilesDir(context);
		} else {
			file = getExternalCacheDir(context);
		}

		File dir = null;
		if (null == fileName || "".equals(fileName)) {
			dir = file;
		} else {
			dir = new File(file + "/" + fileName);
			dir.mkdirs();
		}
		return dir;
	}

	public static File getDirInPacket(Context context, String fileName, boolean fileOrCache) {
		return createDirInPacket(context, fileName, fileOrCache);
	}
	
	public static File getFileInPacket(Context context, String fileName, String directory, boolean fileOrCache){
		File fileDir = getDirInPacket(context, directory, fileOrCache);
		return new File(fileDir + "/" + fileName);
	}

	/**
	 * 得到sdcard中应用包下的files文件夹 /sdcard/Android/data/应用包/files
	 * 
	 * @param context
	 * @return
	 */
	public static File getExternalFilesDir(Context context) {
		return context.getExternalFilesDir(null);
	}

	/**
	 * 得到sdcard中应用包下的cache文件夹 /sdcard/Android/data/应用包/cache
	 * 
	 * @param context
	 * @return
	 */
	public static File getExternalCacheDir(Context context) {
		return context.getExternalCacheDir();
	}

	/**
	 * /sdcard
	 * 
	 * @return
	 */
	public static File getSDCard() {
		return Environment.getExternalStorageDirectory();
	}

	/**
	 * 检测是否存在SD卡
	 * 
	 * @return 存在 则返回true
	 */
	public static Boolean isSDCardExist() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createSDFile(String fileName) throws IOException {
		File file = new File(getSDCard() + "/" + fileName);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 * @return
	 */
	public static File createSDDir(String dirName) {
		File dir = new File(getSDCard() + "/" + dirName);
		dir.mkdirs();
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(getSDCard() + "/" + fileName);
		return file.exists();
	}

	/**
	 * 判断SD卡应用包上的文件夹是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExistInPacket(Context context, String fileName, String directory, boolean fileOrCache) {
		// 存放图片的文件夹路径
		File fileDir = getDirInPacket(context, directory, fileOrCache);
		File file = new File(fileDir + "/" + fileName);
		return file.exists();
	}
}
