package com.haolaike.hotlikescan.db;

import com.haolaike.hotlikescan.MyApplication;
import com.haolaike.hotlikescan.beans.LogBeans;
import com.haolaike.hotlikescan.dao.LogBeansDao;
import com.haolaike.hotlikescan.http.LogCat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogDaoUtils {

    private final LogBeansDao mLogBeansDao;

    public LogDaoUtils() {
        DaoManager mManager = DaoManager.getInstance();
        mManager.init(MyApplication.getInstance());
        mLogBeansDao = mManager.getDaoSession().getLogBeansDao();
    }

    public void insert(LogBeans bean) {
        try {
            mLogBeansDao.insertOrReplace(bean);
        } catch (Exception e) {
            LogCat.e(e);
        }
    }

    public void insert(List<LogBeans> list) {
        try {
            mLogBeansDao.insertOrReplaceInTx(list);
        } catch (Exception e) {
            LogCat.e(e);
        }
    }

    public void update(LogBeans bean) {
        try {
            mLogBeansDao.update(bean);
        } catch (Exception e) {
            LogCat.e(e);
        }
    }

    public void delete(LogBeans bean) {
        try {
            mLogBeansDao.delete(bean);
        } catch (Exception e) {
            LogCat.e(e);
        }
    }

    public void delete(List<LogBeans> list) {
        try {
            mLogBeansDao.deleteInTx(list);
        } catch (Exception e) {
            LogCat.e(e);
        }
    }

    public List<LogBeans> selectAll() {
        try {
            return mLogBeansDao.loadAll();
        } catch (Exception e) {
            LogCat.e(e);
            return new ArrayList<>();
        }
    }

    public List<LogBeans> selectAll(Date startTime, Date endTime) {
        try {
            return mLogBeansDao.queryBuilder().where(LogBeansDao.Properties.CreateTime.between(startTime, endTime)).build().list();
//            return mLogBeansDao.queryRaw("CREATE_TIME", "CREATE_TIME between '" + start + "' and '" + end + "'");
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /*清空数据表*/
    public void clear() {
        try {
            mLogBeansDao.deleteAll();
        } catch (Exception e) {
            LogCat.e(e);
        }
    }
}
