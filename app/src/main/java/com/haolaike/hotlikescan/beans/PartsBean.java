package com.haolaike.hotlikescan.beans;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/12/21 0021.
 * 部件bean
 */

@Entity
public class PartsBean implements Serializable {
    public static final int TYPE_IN = 1;
    public static final int TYPE_OUT = 2;
    private static final long serialVersionUID = -209495374519783770L;
    @Id(autoincrement = true)
    private Long _id;
    private String bztm;
    private String vbeln;
    private String posnr;
    private String bjbz;
    private String packageNo;
    private String orderNo;
    private String rowNo;
    private String soOrderNo;
    private String chlidOrderNo;
    private boolean isScan;
    private int type;
    private long time;
    
    private String targetBztm;

    @Generated(hash = 1086776634)
    public PartsBean(Long _id, String bztm, String vbeln, String posnr, String bjbz,
            String packageNo, String orderNo, String rowNo, String soOrderNo,
            String chlidOrderNo, boolean isScan, int type, long time,
            String targetBztm) {
        this._id = _id;
        this.bztm = bztm;
        this.vbeln = vbeln;
        this.posnr = posnr;
        this.bjbz = bjbz;
        this.packageNo = packageNo;
        this.orderNo = orderNo;
        this.rowNo = rowNo;
        this.soOrderNo = soOrderNo;
        this.chlidOrderNo = chlidOrderNo;
        this.isScan = isScan;
        this.type = type;
        this.time = time;
        this.targetBztm = targetBztm;
    }

    @Generated(hash = 1642527405)
    public PartsBean() {
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof PartsBean) {
            return TextUtils.equals(bztm, ((PartsBean) obj).bztm);
        } else {
            return false;
        }
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getBztm() {
        return this.bztm;
    }

    public void setBztm(String bztm) {
        this.bztm = bztm;
    }

    public String getVbeln() {
        return this.vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }

    public String getPosnr() {
        return this.posnr;
    }

    public void setPosnr(String posnr) {
        this.posnr = posnr;
    }

    public String getBjbz() {
        return this.bjbz;
    }

    public void setBjbz(String bjbz) {
        this.bjbz = bjbz;
    }

    public String getPackageNo() {
        return this.packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRowNo() {
        return this.rowNo;
    }

    public void setRowNo(String rowNo) {
        this.rowNo = rowNo;
    }

    public String getSoOrderNo() {
        return this.soOrderNo;
    }

    public void setSoOrderNo(String soOrderNo) {
        this.soOrderNo = soOrderNo;
    }

    public String getChlidOrderNo() {
        return this.chlidOrderNo;
    }

    public void setChlidOrderNo(String chlidOrderNo) {
        this.chlidOrderNo = chlidOrderNo;
    }

    public boolean getIsScan() {
        return this.isScan;
    }

    public void setIsScan(boolean isScan) {
        this.isScan = isScan;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTargetBztm() {
        return this.targetBztm;
    }

    public void setTargetBztm(String targetBztm) {
        this.targetBztm = targetBztm;
    }
}
