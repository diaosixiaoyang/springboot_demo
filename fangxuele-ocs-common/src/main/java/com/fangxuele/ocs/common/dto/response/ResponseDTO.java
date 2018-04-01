package com.fangxuele.ocs.common.dto.response;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;


public class ResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success = false;

    private int statusCode = 0;

    private String msg = StringUtils.EMPTY;

    private Map<String, Object> dataMap = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}