package com.haolaike.hotlikescan.data;

import android.text.TextUtils;

import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.db.PartsDaoUtils;
import com.haolaike.hotlikescan.http.LogCat;
import com.haolaike.hotlikescan.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by holike on 2017/12/21.
 * 部件数据
 */

public class PartsData {
    private volatile static PartsData scanData;
    private PartsDaoUtils partsDaoUtils;
    private Map<String, PartsBean> inInfoMap = new HashMap<>();//入库条码和零件信息
    private Map<String, List<PartsBean>> inMap = new HashMap<>();//部件和零件信息

    private Map<String, PartsBean> outInfoMap = new HashMap<>();//零件条码和所在部件
    private Map<String, List<PartsBean>> outMap = new HashMap<>();//部件和零件

    private PartsData() {
        partsDaoUtils = new PartsDaoUtils();
    }

    public static PartsData getInstance() {
        if (scanData == null) {
            synchronized (PartsData.class) {
                if (scanData == null) {
                    scanData = new PartsData();
                }
            }
        }
        return scanData;
    }

    /**
     * 初始化
     */
    public void init() {
        Observable.just("").subscribeOn(Schedulers.io())
                .doOnNext(s -> {
                    List<PartsBean> list = partsDaoUtils.selectAll();
                    List<PartsBean> expiredBeans = new ArrayList<>();//清除列表
                    for (PartsBean bean : list) {
                        long now = System.currentTimeMillis();
                        long old = bean.getTime();
                        if (now - old > Constants.CLEAR_TIME) {//超过缓存时间
                            expiredBeans.add(bean);
                        } else {
                            addInfoMap(bean);
                        }
                    }
                    partsDaoUtils.delete(expiredBeans);//清除
                })
                .subscribe();

    }

    /***
     * 添加入库/出库列表
     */
    private void addInfoMap(PartsBean bean) {
        String key = getKey(bean);
        if (bean.getType() == PartsBean.TYPE_IN) {//入库
            inInfoMap.put(bean.getBztm(), bean);
            List<PartsBean> list = inMap.get(key);
            if (list == null) list = new ArrayList<>();
            list.add(bean);
            inMap.put(key, list);
//            if (inMap.containsKey(key)) {//存在部件列表
//                inMap.get(key).add(bean);
//            } else {//还没存在部件列表
//                List<PartsBean> beanList = new ArrayList<>();
//                beanList.add(bean);
//                inMap.put(key, beanList);
//            }
        } else if (bean.getType() == PartsBean.TYPE_OUT) {//出库
            outInfoMap.put(bean.getBztm(), bean);
            List<PartsBean> list = outMap.get(key);
            if (list == null) list = new ArrayList<>();
            if (!list.contains(bean)) {
                list.add(bean);
            }
            outMap.put(key, list);
//            if (outMap.containsKey(key)) {//存在部件列表
//                outMap.get(key).add(bean);
//            } else {//还没存在部件列表
//                List<PartsBean> beanList = new ArrayList<>();
//                beanList.add(bean);
//                outMap.put(key, beanList);
//            }
        }
    }

    /**
     * 是否已经入库扫描
     */
    public boolean hasScanIn(String code) {
        if (inInfoMap == null) {
            return false;
        } else {
            return inInfoMap.containsKey(code);
        }
    }

    /**
     * 根据包装条码获取所在入库部件的所有零件
     */
    public List<PartsBean> getInListByCode(String code) {
        PartsBean bean = inInfoMap.get(code);
        if (bean == null) return new ArrayList<>();
        return inMap.get(getKey(bean));
    }

    /**
     * 根据key获取入库部件的所有零件
     */
    public List<PartsBean> getInListByKey(String key) {
        return inMap.get(key);
    }

    /**
     * 添加入库列表
     */
    public void addInList(List<PartsBean> list) {
        for (PartsBean bean : list) {
            addInfoMap(bean);
        }
        partsDaoUtils.insert(list);
    }

    /**
     * 更新入库扫描状态
     */
    public void updateInScan(String code) {
        PartsBean bean = inInfoMap.get(code);
        if (bean != null) {
            bean.setIsScan(true);
            inInfoMap.put(code, bean);
        }
//        inInfoMap.get(code).setIsScan(true);
        partsDaoUtils.update(inInfoMap.get(code));
    }

    /*
     * 删除单个入库
     */
//    public void deleteIn(PartsBean bean) {
//        inInfoMap.remove(bean.getBztm());
//        String key = getKey(bean);
//        if (inMap.get(key).size() > 1) {
//            inMap.get(key).remove(bean);
//        } else {
//            inMap.remove(key);
//        }
//        partsDaoUtils.delete(bean);
//    }

    /**
     * 删除入库列表
     */
    public void deleteIn(List<PartsBean> list) {
        for (PartsBean bean : list) {
            inInfoMap.remove(bean.getBztm());
            String key = getKey(bean);
//            if (inMap.containsKey(key)) {
//                inMap.remove(key);
//            }
            inMap.remove(key);
        }
        partsDaoUtils.delete(list);
    }

    /**
     * 是否已经出库扫描
     */
    public boolean hasScanOut(String code) {
        if (outInfoMap == null) {
            return false;
        } else {
            return outInfoMap.containsKey(code);
        }
    }

    /**
     * 根据包装条码获取所在出库部件的所有零件
     */
    public List<PartsBean> getOutListByCode(String code) {
        PartsBean bean = outInfoMap.get(code);
        if (bean == null) return new ArrayList<>();
        String key = getKey(bean);
        LogCat.e("key", key);
        List<PartsBean> list = outMap.get(key);
        return list;
    }

    /**
     * 根据key获取出库部件的所有零件
     */
    public List<PartsBean> getOutListByKey(String key) {
        return outMap.get(key);
    }

    /**
     * 获取所有出库零件
     */
//    public List<PartsBean> getAllOutList() {
//        List<PartsBean> list = new ArrayList<>();
//        Set<String> keys = outMap.keySet();
//        for (String key : keys) {
//            List<PartsBean> beans = outMap.get(key);
//            if (beans != null) {
//                list.addAll(beans);
//            }
////            list.addAll(outMap.get(key));
//        }
//        return list;
//    }

    /*获取所有已扫完的出库部件*/
    public List<PartsBean> getAllScanOutList() {
        List<PartsBean> outList = new ArrayList<>();
        Set<String> keys = outMap.keySet();
        for (String key : keys) {
            List<PartsBean> dataList = outMap.get(key);
            if (dataList != null && isAllScan(dataList)) {
                outList.addAll(dataList);
            }
        }
        return outList;
    }

    private static boolean isAllScan(List<PartsBean> list) {
        if (list == null || list.isEmpty()) return true;
        for (PartsBean bean : list) {
            if (!bean.getIsScan()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 添加出库列表
     */
    public void addOutList(List<PartsBean> list) {
        for (PartsBean bean : list) {
            addInfoMap(bean);
        }
        partsDaoUtils.insert(list);
    }

    /**
     * 更新出库扫描状态
     */
    public void updateOutScan(String code) {
        PartsBean bean = outInfoMap.get(code);
        if (bean != null) {
            bean.setIsScan(true);
            outInfoMap.put(code, bean);
            partsDaoUtils.update(bean);
        }
//        outInfoMap.get(code).setIsScan(true);
    }

    /*
     * 删除单个出库
     */
//    public void deleteOut(PartsBean bean) {
//        outInfoMap.remove(bean.getBztm());
//        String key = getKey(bean);
//        if (outMap.get(key).size() > 1) {
//            outMap.get(key).remove(bean);
//        } else {
//            outMap.remove(key);
//        }
//        partsDaoUtils.delete(bean);
//    }

    /**
     * 删除出库列表
     */
    public void deleteOut(List<PartsBean> list) {
        for (PartsBean bean : list) {
            outInfoMap.remove(bean.getBztm());
            String key = getKey(bean);
//            if (outMap.containsKey(key)) {
//                outMap.remove(key);
//            }
            outMap.remove(key);
        }
        partsDaoUtils.delete(list);
    }

    private String getKey(PartsBean bean) {
//        return bean.getBjbz() + "+" + bean.getSoOrderNo() + "+" + bean.getChlidOrderNo();
//        return bean.getBjbz() + "+" + bean.getSoOrderNo();
        if (TextUtils.isEmpty(bean.getTargetBztm())) {
            return "_" + bean.getSoOrderNo();
        }
        return bean.getTargetBztm();
    }

    public Map<String, PartsBean> getInInfoMap() {
        return inInfoMap;
    }

    public Map<String, List<PartsBean>> getInMap() {
        return inMap;
    }

    public Map<String, PartsBean> getOutInfoMap() {
        return outInfoMap;
    }

    public Map<String, List<PartsBean>> getOutMap() {
        return outMap;
    }
}
