package com.haolaike.hotlikescan.beans;

import java.io.Serializable;

/**
 * Created by holike on 2017/12/15.
 * 用户bean
 */

public class UserBean implements Serializable {

    /**
     * expirationTime : 1514624059833
     * token : c4f51d7dcb684a1fa3f0d735c2184d8e
     */

    private long expirationTime;
    private String token;

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
