package com.haolaike.hotlikescan.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by holike on 2017/12/15.
 * 出库bean
 */

public class OutScanBean implements Serializable {

    /**
     * gvZbztm : 19000075210B0131
     * gtReturn : [{"bztm":"19000075210B0131","vbeln":"0190000752","posnr":"000010","bjbz":"B01"},{"bztm":"19000075210B0132","vbeln":"0190000752","posnr":"000010","bjbz":"B01"},{"bztm":"19000075210B0133","vbeln":"0190000752","posnr":"000010","bjbz":"B01"}]
     * gtReturnExt : [{"bjbz":"B01","bztm":"19000075210B0131","packageNo":"3","orderNo":"0190000752","rowNo":"000010"},{"bjbz":"B01","bztm":"19000075210B0132","packageNo":"3","orderNo":"0190000752","rowNo":"000010"},{"bjbz":"B01","bztm":"19000075210B0133","packageNo":"3","orderNo":"0190000752","rowNo":"000010"}]
     * emessage : 扫描成功！
     * esubrc : S
     */

    private String gvZbztm;
    private String emessage;
    private String esubrc;
    private List<GtReturnBean> gtReturn;
    private List<PartsBean> gtReturnExt;

    public String getGvZbztm() {
        return gvZbztm;
    }

    public void setGvZbztm(String gvZbztm) {
        this.gvZbztm = gvZbztm;
    }

    public String getEmessage() {
        return emessage;
    }

    public void setEmessage(String emessage) {
        this.emessage = emessage;
    }

    public String getEsubrc() {
        return esubrc;
    }

    public void setEsubrc(String esubrc) {
        this.esubrc = esubrc;
    }

    public List<GtReturnBean> getGtReturn() {
        return gtReturn;
    }

    public void setGtReturn(List<GtReturnBean> gtReturn) {
        this.gtReturn = gtReturn;
    }

    public List<PartsBean> getGtReturnExt() {
        return gtReturnExt;
    }

    public void setGtReturnExt(List<PartsBean> gtReturnExt) {
        this.gtReturnExt = gtReturnExt;
    }

    public static class GtReturnBean implements Serializable {
        /**
         * bztm : 19000075210B0131
         * vbeln : 0190000752
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
