package com.fangxuele.ocs.web.coms.log.impl;

import com.fangxuele.ocs.web.coms.log.LogAPI;
import com.fangxuele.ocs.web.coms.log.LogLevel;

import java.util.HashMap;
import java.util.Map;


public class LogAdapter implements LogAPI {

    /**
     * @param message
     * @param logLevel
     */
    @Override
    public void log(int module, String message, LogLevel logLevel) {
        log(module, message, null, logLevel);
    }

    /**
     * @param message
     * @param objects
     * @param logLevel
     */
    @Override
    public void log(int module, String message, Object[] objects, LogLevel logLevel) {

    }

    /**
     * @return
     */
    @Override
    public LogLevel getRootLogLevel() {
        return LogLevel.ERROR;
    }

    /**
     * @return
     */
    @Override
    public Map<String, LogLevel> getCustomLogLevel() {
        return new HashMap<String, LogLevel>();
    }
}
