package com.fangxuele.ocs.service.common;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.constant.RedisCacheConstant;
import com.fangxuele.ocs.common.util.ConfigurationUtil;
import com.fangxuele.ocs.inter.cache.service.RedisCacheService;
import com.fangxuele.ocs.inter.m.service.ConfigurationMService;

/**
 * @author rememberber(https://github.com/rememberber)
 */
@Service(version = "1.0.0", interfaceClass = ConfigurationMService.class, timeout = 10000, cluster = "failfast")
public class ConfigurationMServiceImpl implements ConfigurationMService {

    @Reference(version = "1.0.0")
    private RedisCacheService redisCacheService;

    @Override
    public void reloadConfiguration() {
        ConfigurationUtil.propertiesMap = redisCacheService.getAllValueByHash(RedisCacheConstant.CONFIG_PROP_KEY);
    }
}
