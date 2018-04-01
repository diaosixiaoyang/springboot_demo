package com.fangxuele.ocs.web.coms.config;

import com.fangxuele.ocs.web.coms.log.LogLevel;
import com.fangxuele.ocs.web.coms.log.impl.Log4JDBCImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wfc on 2017/9/14.
 */
@Configuration
public class LogConfig {

    @Bean
    public Log4JDBCImpl log4JDBCImpl() {
        Log4JDBCImpl log4JDBC = new Log4JDBCImpl();
        // 设置全局日志级别
        log4JDBC.setRootLogLevel(LogLevel.ERROR);

        // 设置用户日志级别
        Map<String, LogLevel> customLogLevel = new HashMap<>();
        customLogLevel.put("com.fangxuele.lol.web.loms", LogLevel.TRACE);
        log4JDBC.setCustomLogLevel(customLogLevel);
        return log4JDBC;
    }

}
