package com.haolaike.hotlikescan.data;

import android.support.annotation.NonNull;

import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.db.PartsDaoUtils;
import com.haolaike.hotlikescan.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by holike on 2017/12/21.
 * 部件数据
 */

public class PartsData {
    private static PartsData scanData;
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
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
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
                    }
                })
                .subscribe();

    }

    /***
     * 添加入库/出库列表
     * @param bean
     */
    private void addInfoMap(PartsBean bean) {
        String key = getKey(bean);
        if (bean.getType() == PartsBean.TYPE_IN) {//入库
            inInfoMap.put(bean.getBztm(), bean);
            if (inMap.containsKey(key)) {//存在部件列表
                inMap.get(key).add(bean);
            } else {//还没存在部件列表
                List<PartsBean> beanList = new ArrayList<>();
                beanList.add(bean);
                inMap.put(key, beanList);
            }
        } else if (bean.getType() == PartsBean.TYPE_OUT) {//出库
            outInfoMap.put(bean.getBztm(), bean);
            if (outMap.containsKey(key)) {//存在部件列表
                outMap.get(key).add(bean);
            } else {//还没存在部件列表
                List<PartsBean> beanList = new ArrayList<>();
                beanList.add(bean);
                outMap.put(key, beanList);
            }
        }
    }

    /**
     * 是否已经入库扫描
     *
     * @param code
     * @return
     */
    public boolean hasScanIn(String code) {
        if (inInfoMap == null) {
            return false;
        } else {
            if (inInfoMap.containsKey(code)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 根据包装条码获取所在入库部件的所有零件
     *
     * @param code
     * @return
     */
    public List<PartsBean> getInListByCode(String code) {
        PartsBean bean = inInfoMap.get(code);
        return inMap.get(getKey(bean));
    }

    /**
     * 根据key获取入库部件的所有零件
     *
     * @param key
     * @return
     */
    public List<PartsBean> getInListByKey(String key) {
        return inMap.get(key);
    }

    /**
     * 添加入库列表
     *
     * @param list
     */
    public void addInList(List<PartsBean> list) {
        for (PartsBean bean : list) {
            addInfoMap(bean);
        }
        partsDaoUtils.insert(list);
    }

    /**
     * 更新入库扫描状态
     *
     * @param code
     */
    public void updateInScan(String code) {
        inInfoMap.get(code).setIsScan(true);
        partsDaoUtils.update(inInfoMap.get(code));
    }

    /**
     * 删除单个入库
     *
     * @param bean
     */
    public void deleteIn(PartsBean bean) {
        inInfoMap.remove(bean.getBztm());
        String key = getKey(bean);
        if (inMap.get(key).size() > 1) {
            inMap.get(key).remove(bean);
        } else {
            inMap.remove(key);
        }
        partsDaoUtils.delete(bean);
    }

    /**
     * 删除入库列表
     *
     * @param list
     */
    public void deleteIn(List<PartsBean> list) {
        for (PartsBean bean : list) {
            inInfoMap.remove(bean.getBztm());
            String key = getKey(bean);
            if (inMap.containsKey(key)) {
                inMap.remove(key);
            }
        }
        partsDaoUtils.delete(list);
    }

    /**
     * 是否已经出库扫描
     *
     * @param code
     * @return
     */
    public boolean hasScanOut(String code) {
        if (outInfoMap == null) {
            return false;
        } else {
            if (outInfoMap.containsKey(code)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 根据包装条码获取所在出库部件的所有零件
     *
     * @param code
     * @return
     */
    public List<PartsBean> getOutListByCode(String code) {
        PartsBean bean = outInfoMap.get(code);
        return outMap.get(getKey(bean));
    }

    /**
     * 根据key获取出库部件的所有零件
     *
     * @param key
     * @return
     */
    public List<PartsBean> getOutListByKey(String key) {
        return outMap.get(key);
    }

    /**
     * 获取所有出库零件
     *
     * @return
     */
    public List<PartsBean> getAllOutList() {
        List<PartsBean> list = new ArrayList<>();
        Set<String> keys = outMap.keySet();
        for (String key : keys) {
            list.addAll(outMap.get(key));
        }
        return list;
    }

    /**
     * 添加出库列表
     *
     * @param list
     */
    public void addOutList(List<PartsBean> list) {
        for (PartsBean bean : list) {
            addInfoMap(bean);
        }
        partsDaoUtils.insert(list);
    }

    /**
     * 更新出库扫描状态
     *
     * @param code
     */
    public void updateOutScan(String code) {
        outInfoMap.get(code).setIsScan(true);
        partsDaoUtils.update(outInfoMap.get(code));
    }

    /**
     * 删除单个出库
     */
    public void deleteOut(PartsBean bean) {
        outInfoMap.remove(bean.getBztm());
        String key = getKey(bean);
        if (outMap.get(key).size() > 1) {
            outMap.get(key).remove(bean);
        } else {
            outMap.remove(key);
        }
        partsDaoUtils.delete(bean);
    }

    /**
     * 删除出库列表
     *
     * @param list
     */
    public void deleteOut(List<PartsBean> list) {
        for (PartsBean bean : list) {
            outInfoMap.remove(bean.getBztm());
            String key = getKey(bean);
            if (outMap.containsKey(key)) {
                outMap.remove(key);
            }
        }
        partsDaoUtils.delete(list);
    }

    @NonNull
    private String getKey(PartsBean bean) {
        String key = bean.getBjbz() + "+" + bean.getSoOrderNo() + "+" + bean.getChlidOrderNo();
        return key;
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
