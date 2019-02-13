package com.haolaike.hotlikescan.beans;

import java.io.Serializable;

/**
 * Created by holike on 2017/12/22.
 */

public class OutInfoBean implements Serializable {
    private String zcyf;//承运方
    private String zthfs;//提货方式
    private String zcph;//车牌号
    private String zzcbz;//装车班组

    public OutInfoBean(String zcyf, String zthfs, String zcph, String zzcbz) {
        this.zcyf = zcyf;
        this.zthfs = zthfs;
        this.zcph = zcph;
        this.zzcbz = zzcbz;
    }

    public String getZcyf() {
        return zcyf;
    }

    public void setZcyf(String zcyf) {
        this.zcyf = zcyf;
    }

    public String getZthfs() {
        return zthfs;
    }

    public void setZthfs(String zthfs) {
        this.zthfs = zthfs;
    }

    public String getZcph() {
        return zcph;
    }

    public void setZcph(String zcph) {
        this.zcph = zcph;
    }

    public String getZzcbz() {
        return zzcbz;
    }

    public void setZzcbz(String zzcbz) {
        this.zzcbz = zzcbz;
    }

    @Override
    public String toString() {
        return "{" +
                "zcyf='" + zcyf + '\'' +
                ", zthfs='" + zthfs + '\'' +
                ", zcph='" + zcph + '\'' +
                ", zzcbz='" + zzcbz + '\'' +
                '}';
    }
}
