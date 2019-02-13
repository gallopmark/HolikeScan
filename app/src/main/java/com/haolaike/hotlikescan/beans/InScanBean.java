package com.haolaike.hotlikescan.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by holike on 2017/12/15.
 * 入库bean
 */

public class InScanBean implements Serializable {
    private static final long serialVersionUID = -5296592189188145464L;
    /**
     * eMessage : 扫描成功！
     * eSubrc : S
     * gvBztm : 19000028210B01001
     * gvEvent :
     * ztmmRkcxPda : [{"bztm":"19000028210B01004","vbeln":"0190000282","posnr":"000010","bjbz":"B01"},{"bztm":"19000028210B01005","vbeln":"0190000282","posnr":"000010","bjbz":"B01"},{"bztm":"19000028210B01001","vbeln":"0190000282","posnr":"000010","bjbz":"B01"},{"bztm":"19000028210B01002","vbeln":"0190000282","posnr":"000010","bjbz":"B01"},{"bztm":"19000028210B01003","vbeln":"0190000282","posnr":"000010","bjbz":"B01"}]
     * ztmmRkcxPdaExt : [{"bztm":"19000028210B01004","vbeln":"0190000282","posnr":"000010","bjbz":"B01","packageNo":"1","orderNo":"BZ13300000-171207003","rowNo":"10"},{"bztm":"19000028210B01005","vbeln":"0190000282","posnr":"000010","bjbz":"B01","packageNo":"1","orderNo":"BZ13300000-171207003","rowNo":"10"},{"bztm":"19000028210B01001","vbeln":"0190000282","posnr":"000010","bjbz":"B01","packageNo":"1","orderNo":"BZ13300000-171207003","rowNo":"10"},{"bztm":"19000028210B01002","vbeln":"0190000282","posnr":"000010","bjbz":"B01","packageNo":"1","orderNo":"BZ13300000-171207003","rowNo":"10"},{"bztm":"19000028210B01003","vbeln":"0190000282","posnr":"000010","bjbz":"B01","packageNo":"1","orderNo":"BZ13300000-171207003","rowNo":"10"}]
     */

    private String eMessage;
    private String eSubrc;
    private String gvBztm;
    private String gvEvent;
    private List<ZtmmRkcxPdaBean> ztmmRkcxPda;
    private List<PartsBean> ztmmRkcxPdaExt;

    public String getEMessage() {
        return eMessage;
    }

    public void setEMessage(String eMessage) {
        this.eMessage = eMessage;
    }

    public String getESubrc() {
        return eSubrc;
    }

    public void setESubrc(String eSubrc) {
        this.eSubrc = eSubrc;
    }

    public String getGvBztm() {
        return gvBztm;
    }

    public void setGvBztm(String gvBztm) {
        this.gvBztm = gvBztm;
    }

    public String getGvEvent() {
        return gvEvent;
    }

    public void setGvEvent(String gvEvent) {
        this.gvEvent = gvEvent;
    }

    public List<ZtmmRkcxPdaBean> getZtmmRkcxPda() {
        return ztmmRkcxPda;
    }

    public void setZtmmRkcxPda(List<ZtmmRkcxPdaBean> ztmmRkcxPda) {
        this.ztmmRkcxPda = ztmmRkcxPda;
    }

    public List<PartsBean> getZtmmRkcxPdaExt() {
        return ztmmRkcxPdaExt;
    }

    public void setZtmmRkcxPdaExt(List<PartsBean> ztmmRkcxPdaExt) {
        this.ztmmRkcxPdaExt = ztmmRkcxPdaExt;
    }

    public static class ZtmmRkcxPdaBean implements Serializable {
        private static final long serialVersionUID = 6408711093311953312L;
        /**
         * bztm : 19000028210B01004
         * vbeln : 0190000282
         * posnr : 000010
         * bjbz : B01
         */

        private String bztm;
        private String vbeln;
        private String posnr;
        private String bjbz;

        public String getBztm() {
            return bztm;
        }

        public void setBztm(String bztm) {
            this.bztm = bztm;
        }

        public String getVbeln() {
            return vbeln;
        }

        public void setVbeln(String vbeln) {
            this.vbeln = vbeln;
        }

        public String getPosnr() {
            return posnr;
        }

        public void setPosnr(String posnr) {
            this.posnr = posnr;
        }

        public String getBjbz() {
            return bjbz;
        }

        public void setBjbz(String bjbz) {
            this.bjbz = bjbz;
        }
    }

}
