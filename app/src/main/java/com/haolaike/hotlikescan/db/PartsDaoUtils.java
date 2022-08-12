package com.haolaike.hotlikescan.db;

import com.haolaike.hotlikescan.MyApplication;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.dao.PartsBeanDao;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class PartsDaoUtils {
    private final PartsBeanDao dao;

    public PartsDaoUtils() {
        DaoManager manager = DaoManager.getInstance();
        manager.init(MyApplication.getInstance());
        dao = manager.getDaoSession().getPartsBeanDao();
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
