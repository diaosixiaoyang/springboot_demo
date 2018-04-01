package com.fangxuele.ocs.mapper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TWxPay implements Serializable {
    private Long id;

    private Long orderId;

    private Integer orderType;

    private Long customerId;

    private Integer clientType;

    private Integer inOut;

    private String currencyType;

    private BigDecimal payAmount;

    private Date prePayTime;

    private String ppRequestInfo;

    private String ppResponseInfo;

    private Date confirmPayTime;

    private String cpRequestInfo;

    private String cpResponseInfo;

    private String transactionId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getInOut() {
        return inOut;
    }

    public void setInOut(Integer inOut) {
        this.inOut = inOut;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType == null ? null : currencyType.trim();
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Date getPrePayTime() {
        return prePayTime;
    }

    public void setPrePayTime(Date prePayTime) {
        this.prePayTime = prePayTime;
    }

    public String getPpRequestInfo() {
        return ppRequestInfo;
    }

    public void setPpRequestInfo(String ppRequestInfo) {
        this.ppRequestInfo = ppRequestInfo == null ? null : ppRequestInfo.trim();
    }

    public String getPpResponseInfo() {
        return ppResponseInfo;
    }

    public void setPpResponseInfo(String ppResponseInfo) {
        this.ppResponseInfo = ppResponseInfo == null ? null : ppResponseInfo.trim();
    }

    public Date getConfirmPayTime() {
        return confirmPayTime;
    }

    public void setConfirmPayTime(Date confirmPayTime) {
        this.confirmPayTime = confirmPayTime;
    }

    public String getCpRequestInfo() {
        return cpRequestInfo;
    }

    public void setCpRequestInfo(String cpRequestInfo) {
        this.cpRequestInfo = cpRequestInfo == null ? null : cpRequestInfo.trim();
    }

    public String getCpResponseInfo() {
        return cpResponseInfo;
    }

    public void setCpResponseInfo(String cpResponseInfo) {
        this.cpResponseInfo = cpResponseInfo == null ? null : cpResponseInfo.trim();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}