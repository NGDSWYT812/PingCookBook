package com.ping.cookbook.dbHelper;

import android.content.Context;

import com.ping.cookbook.dao.DaoMaster;
import com.ping.cookbook.dao.DaoSession;

import ping.Lib.Utils.CommonUtil;


/**
 * 数据库控制类
 */
public class DBHelper {
    private static DaoSession daoSessionEcmc;

    /**
     * 数据库名称:localdata
     */
    public static final String DATABASE_NAME = "cookbook.db";

    private static DaoMaster obtainMaster(Context context, String dbName) {
        return new DaoMaster(new DaoMaster.DevOpenHelper(context, dbName, null).getWritableDatabase());
    }

    private static DaoMaster getDaoMaster(Context context, String dbName) {
        if (dbName == null)
            return null;
        return obtainMaster(context, dbName);
    }

    /**
     * 默认操作localdata数据库
     */
    public static DaoSession getDaoSession() {

        if (daoSessionEcmc == null) {
            daoSessionEcmc = getDaoMaster(CommonUtil.getApplicationContext(), DATABASE_NAME).newSession();
        }
        return daoSessionEcmc;
    }
}