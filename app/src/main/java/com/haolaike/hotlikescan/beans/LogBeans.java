package com.haolaike.hotlikescan.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LogBeans {

    @Id(autoincrement = true)
    private Long _id;
    private String orderCode; //订单号
    private String callStart;
    private String dnsStart;
    private String dnsEnd;
    private String connectStart;
    private String connectEnd;
    private String connectionAcquired;
    private String requestHeadersStart;
    private String requestHeadersEnd;
    private String responseHeadersStart;
    private String responseHeadersEnd;
    private String responseBodyStart;
    private String responseBodyEnd;
    private String connectionReleased;
    private String callEnd;
    private String callFailed;
    private Date createTime;   //创建时间
    @Generated(hash = 1961203737)
    public LogBeans(Long _id, String orderCode, String callStart, String dnsStart,
            String dnsEnd, String connectStart, String connectEnd,
            String connectionAcquired, String requestHeadersStart,
            String requestHeadersEnd, String responseHeadersStart,
            String responseHeadersEnd, String responseBodyStart,
            String responseBodyEnd, String connectionReleased, String callEnd,
            String callFailed, Date createTime) {
        this._id = _id;
        this.orderCode = orderCode;
        this.callStart = callStart;
        this.dnsStart = dnsStart;
        this.dnsEnd = dnsEnd;
        this.connectStart = connectStart;
        this.connectEnd = connectEnd;
        this.connectionAcquired = connectionAcquired;
        this.requestHeadersStart = requestHeadersStart;
        this.requestHeadersEnd = requestHeadersEnd;
        this.responseHeadersStart = responseHeadersStart;
        this.responseHeadersEnd = responseHeadersEnd;
        this.responseBodyStart = responseBodyStart;
        this.responseBodyEnd = responseBodyEnd;
        this.connectionReleased = connectionReleased;
        this.callEnd = callEnd;
        this.callFailed = callFailed;
        this.createTime = createTime;
    }
    @Generated(hash = 1017828122)
    public LogBeans() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getOrderCode() {
        return this.orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public String getCallStart() {
        return this.callStart;
    }
    public void setCallStart(String callStart) {
        this.callStart = callStart;
    }
    public String getDnsStart() {
        return this.dnsStart;
    }
    public void setDnsStart(String dnsStart) {
        this.dnsStart = dnsStart;
    }
    public String getDnsEnd() {
        return this.dnsEnd;
    }
    public void setDnsEnd(String dnsEnd) {
        this.dnsEnd = dnsEnd;
    }
    public String getConnectStart() {
        return this.connectStart;
    }
    public void setConnectStart(String connectStart) {
        this.connectStart = connectStart;
    }
    public String getConnectEnd() {
        return this.connectEnd;
    }
    public void setConnectEnd(String connectEnd) {
        this.connectEnd = connectEnd;
    }
    public String getConnectionAcquired() {
        return this.connectionAcquired;
    }
    public void setConnectionAcquired(String connectionAcquired) {
        this.connectionAcquired = connectionAcquired;
    }
    public String getRequestHeadersStart() {
        return this.requestHeadersStart;
    }
    public void setRequestHeadersStart(String requestHeadersStart) {
        this.requestHeadersStart = requestHeadersStart;
    }
    public String getRequestHeadersEnd() {
        return this.requestHeadersEnd;
    }
    public void setRequestHeadersEnd(String requestHeadersEnd) {
        this.requestHeadersEnd = requestHeadersEnd;
    }
    public String getResponseHeadersStart() {
        return this.responseHeadersStart;
    }
    public void setResponseHeadersStart(String responseHeadersStart) {
        this.responseHeadersStart = responseHeadersStart;
    }
    public String getResponseHeadersEnd() {
        return this.responseHeadersEnd;
    }
    public void setResponseHeadersEnd(String responseHeadersEnd) {
        this.responseHeadersEnd = responseHeadersEnd;
    }
    public String getResponseBodyStart() {
        return this.responseBodyStart;
    }
    public void setResponseBodyStart(String responseBodyStart) {
        this.responseBodyStart = responseBodyStart;
    }
    public String getResponseBodyEnd() {
        return this.responseBodyEnd;
    }
    public void setResponseBodyEnd(String responseBodyEnd) {
        this.responseBodyEnd = responseBodyEnd;
    }
    public String getConnectionReleased() {
        return this.connectionReleased;
    }
    public void setConnectionReleased(String connectionReleased) {
        this.connectionReleased = connectionReleased;
    }
    public String getCallEnd() {
        return this.callEnd;
    }
    public void setCallEnd(String callEnd) {
        this.callEnd = callEnd;
    }
    public String getCallFailed() {
        return this.callFailed;
    }
    public void setCallFailed(String callFailed) {
        this.callFailed = callFailed;
    }
    public Date getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
