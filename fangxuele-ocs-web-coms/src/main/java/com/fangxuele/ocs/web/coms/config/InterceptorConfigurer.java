package com.fangxuele.ocs.web.coms.config;

import com.fangxuele.ocs.web.coms.log.impl.Log4JDBCImpl;
import com.fangxuele.ocs.web.coms.log.spring.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zhouy on 2017/5/2.
 */
@Configuration
public class InterceptorConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private LogInterceptor logInterceptor;

    @Autowired
    private Log4JDBCImpl log4JDBC;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 多个拦截器组成一个拦截器链

        // addPathPatterns 用于添加拦截规则

        // excludePathPatterns 用户排除拦截

        logInterceptor.setLogAPI(log4JDBC);
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");

        super.addInterceptors(registry);

    }
}
