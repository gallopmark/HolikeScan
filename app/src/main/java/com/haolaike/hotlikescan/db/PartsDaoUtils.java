package com.haolaike.hotlikescan.db;

import com.haolaike.hotlikescan.MyApplication;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.dao.PartsBeanDao;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class PartsDaoUtils {
    private DaoManager mManager;
    private PartsBeanDao dao;

    public PartsDaoUtils() {
        mManager = DaoManager.getInstance();
        mManager.init(MyApplication.getInstance());
        dao = mManager.getDaoSession().getPartsBeanDao();
    }

    public void insert(PartsBean bean) {
        dao.insertOrReplace(bean);
    }

    public void insert(List<PartsBean> list) {
        dao.insertOrReplaceInTx(list);
    }

    public void update(PartsBean bean) {
        dao.update(bean);
    }

    public void delete(PartsBean bean) {
        dao.delete(bean);
    }

    public void delete(List<PartsBean> list) {
        dao.deleteInTx(list);
    }

    public List<PartsBean> selectAll() {
        return dao.loadAll();
    }
}
