package com.fangxuele.ocs.web.coms.log.impl;


import com.fangxuele.ocs.common.constant.SecurityConstant;
import com.fangxuele.ocs.web.coms.log.LogMessageObject;

import javax.servlet.http.HttpServletRequest;


/**
 * 将request放入ThreadLocal用于LOG_ARGUMENTS注入。
 */

public abstract class LogUitls {
    // 用于存储每个线程的request请求
    private static final ThreadLocal<HttpServletRequest> LOCAL_REQUEST = new ThreadLocal<HttpServletRequest>();

    public static void putRequest(HttpServletRequest request) {
        LOCAL_REQUEST.set(request);
    }

    public static HttpServletRequest getRequest() {
        return LOCAL_REQUEST.get();
    }

    public static void removeRequest() {
        LOCAL_REQUEST.remove();
    }

    /**
     * 将LogMessageObject放入LOG_ARGUMENTS。 描述
     *
     * @param logMessageObject
     */
    public static void putArgs(LogMessageObject logMessageObject) {
        HttpServletRequest request = getRequest();
        request.setAttribute(SecurityConstant.LOG_ARGUMENTS, logMessageObject);
    }

    /**
     * 得到LogMessageObject。 描述
     */
    public static LogMessageObject getArgs() {
        HttpServletRequest request = getRequest();
        return (LogMessageObject) request.getAttribute(SecurityConstant.LOG_ARGUMENTS);
    }
}
