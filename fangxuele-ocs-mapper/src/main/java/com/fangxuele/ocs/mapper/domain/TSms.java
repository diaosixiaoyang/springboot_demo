package com.fangxuele.ocs.mapper.domain;

import java.io.Serializable;
import java.util.Date;

public class TSms implements Serializable {
    private Long id;

    private Date sendTime;

    private String sendPhone;

    private String smsContent;

    private Integer smsType;

    private Integer sendSuccess;

    private String sendResponse;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone == null ? null : sendPhone.trim();
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public Integer getSendSuccess() {
        return sendSuccess;
    }

    public void setSendSuccess(Integer sendSuccess) {
        this.sendSuccess = sendSuccess;
    }

    public String getSendResponse() {
        return sendResponse;
    }

    public void setSendResponse(String sendResponse) {
        this.sendResponse = sendResponse == null ? null : sendResponse.trim();
    }
}