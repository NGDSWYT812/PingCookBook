package ping.Lib.Utils;

import android.os.Environment;

import java.io.File;

/**
 * 本地存储器文件路径管理器
 */
public class FilePathUtils {

    /**
     * 备份地址
     */
    public static String getBackUpPath() {
        String filePath = getAppRootPath() + "/backup/";
        File file = new File(filePath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = null;
        return filePath;
    }

    /**
     * cache地址
     */
    public static String getCachePath() {
        String filePath = getAppRootPath() + "/cache/";
        File file = new File(filePath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = null;
        return filePath;
    }

    /**
     * temp地址
     */
    public static String getTempPath() {
        String filePath = getAppRootPath() + "/temp/";
        File file = new File(filePath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = null;
        return filePath;
    }

    /**
     * 下载到SD卡地址
     */
    public static String getUpgradePath() {
        String filePath = getAppRootPath() + "/upgrade/";
        File file = new File(filePath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = null;
        return filePath;
    }

    /**
     * 下载到SD卡地址
     */
    public static String getDownloadPath() {
        String filePath = getAppRootPath() + "/download/";
        File file = new File(filePath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = null;
        return filePath;
    }

    /**
     * 获取根路径
     *
     * @return
     */
    public static String getAppRootPath() {
        String filePath = "/fairycartoon";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = Environment.getExternalStorageDirectory() + filePath;
        } else {
            filePath = CommonUtil.getApplicationContext().getFilesDir().toString();
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return filePath;
    }

}
