package com.haolaike.hotlikescan.beans;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pony on 2021/4/7.
 * 上架扫描数据
 */
public final class OnShelfDataBean extends BaseResultBean {
    @Expose
    public boolean isSelect = true; //是否选中
    @SerializedName("t_BZTM")
    List<InnerBean> dataList;
    @SerializedName("bztm")
    public String bztm; //包装条码

    public List<InnerBean> getDataList() {
        return dataList == null ? new ArrayList<>() : dataList;
    }

    public boolean isSimple() {
        return !TextUtils.isEmpty(bztm) && (bztm.startsWith("H") || bztm.startsWith("h"));
    }

    public boolean hasData() {
        return !getDataList().isEmpty();
    }

    public String getCRMOrderNumber() {
        return getDataByType(1);
    }

    public String getChildOrderNumber() {
        return getDataByType(2);
    }

    public String getLogo() {
        return getDataByType(3);
    }

    public String getSalesOrderNumber() {
        return getDataByType(4);
    }

    private String getDataByType(int type) {
        if (hasData()) {
            if (type == 1) {
                return getDataList().get(0).bstnk;
            } else if (type == 2) {
                return getDataList().get(0).zorder;
            } else if (type == 3) {
                return getDataList().get(0).bjbz;
            } else if (type == 4) {
                return getDataList().get(0).vbeln;
            }
        }
        return "";
    }

    public int getItemType() {
        if (isSimple()) return 0;
        return 1;
    }

    public static final class InnerBean {
        public String zorder; //生产子订单
        public String bstnk; //CRM订单号
        public String vbeln; //销售订单号
        public String bjbz; //部件标识
        public String zbzsx; //包装顺序
        public String bztm; //包装条码
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof OnShelfDataBean) {
            return TextUtils.equals(bztm, ((OnShelfDataBean) obj).bztm);
        } else {
            return false;
        }
    }
}
