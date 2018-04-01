package com.fangxuele.ocs.web.coms.log.impl;

import com.fangxuele.ocs.mapper.domain.TLogInfo;
import com.fangxuele.ocs.web.coms.log.LogLevel;
import com.fangxuele.ocs.web.coms.service.LogInfoService;
import com.fangxuele.ocs.web.coms.service.LolShiroRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局日志等级<包日志等级<类和方法日志等级
 */
public class Log4JDBCImpl extends LogAdapter {

    private LogLevel rootLogLevel = LogLevel.ERROR;

    @Autowired
    private LogInfoService logInfoService;

    private Map<String, LogLevel> customLogLevel = new HashMap<>();

    /**
     * @param message
     * @param objects
     * @param logLevel
     */
    @Override
    public void log(int module, String message, Object[] objects, LogLevel logLevel) {
        MessageFormat mFormat = new MessageFormat(message);
        String result = mFormat.format(objects);

        if (StringUtils.isEmpty(result)) {
            return;
        }
        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();

        // result = shiroUser.toString() + ":" + result;

        TLogInfo logInfo = new TLogInfo();
        logInfo.setCreateTime(new Date());
        logInfo.setUserId(shiroUser.getId());
        logInfo.setUsername(shiroUser.getName());
        logInfo.setMessage(result);
        logInfo.setIpAddress(shiroUser.getIpAddress());
        logInfo.setLogLevel(logLevel.name());
        logInfo.setModule(module);

        logInfoService.save(logInfo);
    }

    public void setRootLogLevel(LogLevel rootLogLevel) {
        this.rootLogLevel = rootLogLevel;
    }

    /**
     * @return
     */
    @Override
    public LogLevel getRootLogLevel() {
        return rootLogLevel;
    }

    public void setCustomLogLevel(Map<String, LogLevel> customLogLevel) {
        this.customLogLevel = customLogLevel;
    }

    @Override
    public Map<String, LogLevel> getCustomLogLevel() {
        return customLogLevel;
    }

    public void setLogInfoService(LogInfoService logInfoService) {
        this.logInfoService = logInfoService;
    }

}