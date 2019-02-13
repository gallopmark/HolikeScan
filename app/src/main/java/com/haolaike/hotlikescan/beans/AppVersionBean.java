package com.haolaike.hotlikescan.beans;

import java.io.Serializable;

/**
 * Created by wqj on 2018/1/1.
 * app版本信息bean
 */

public class AppVersionBean implements Serializable {
    /**
     * publishDate : 2017-08-18
     * version : 1
     * info : 初次版本
     */

    private String publishDate;
    private String version;
    private String info;

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
