package com.haolaike.hotlikescan.dao;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.haolaike.hotlikescan.http.LogCat;

import java.io.File;
import java.io.IOException;

/*修改greenDao数据库存放目录*/
public class GreenDaoContext extends ContextWrapper {

    private Context mContext;

    public GreenDaoContext(Context context) {
        super(context);
        this.mContext = context;
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象
     */
    @Override
    public File getDatabasePath(String dbName) {
        File file = mContext.getExternalFilesDir(null);
        if (file == null) return super.getDatabasePath(dbName);
        String dbDir = file.getAbsolutePath();
        File baseFile = new File(dbDir);
        // 目录不存在则自动创建目录
        boolean isBaseCreated;
        if (!baseFile.exists()) {
            isBaseCreated = baseFile.mkdirs();
        } else {
            isBaseCreated = true;
        }
        if (!isBaseCreated) {
            return super.getDatabasePath(dbName);
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(baseFile.getPath());
        buffer.append(File.separator);
        buffer.append("greenDao");
        dbDir = buffer.toString();// 数据库所在目录
        buffer.append(File.separator);
        buffer.append(dbName);
        String dbPath = buffer.toString();// 数据库路径
        // 判断目录是否存在，不存在则创建该目录
        File dirFile = new File(dbDir);
        boolean isDirFileCreated;
        if (!dirFile.exists()) {
            isDirFileCreated = dirFile.mkdirs();
        } else {
            isDirFileCreated = true;
        }
        if (!isDirFileCreated) return super.getDatabasePath(dbName);
        // 数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        // 判断文件是否存在，不存在则创建该文件
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();// 创建文件
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            isFileCreateSuccess = true;
        // 返回数据库文件对象
        if (isFileCreateSuccess) {
            LogCat.e("database", dbFile.getAbsolutePath());
            return dbFile;
        } else
            return super.getDatabasePath(dbName);
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String, int,
     * android.database.sqlite.SQLiteDatabase.CursorFactory,
     * android.database.DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
    }
}
