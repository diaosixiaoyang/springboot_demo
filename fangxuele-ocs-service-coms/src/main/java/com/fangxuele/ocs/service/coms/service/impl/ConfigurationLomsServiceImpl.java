package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.constant.RedisCacheConstant;
import com.fangxuele.ocs.common.util.ConfigurationUtil;
import com.fangxuele.ocs.inter.cache.service.RedisCacheService;
import com.fangxuele.ocs.inter.coms.service.ConfigurationLomsService;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
@Service(version = "1.0.0", interfaceClass = ConfigurationLomsService.class, timeout = 10000, cluster = "failfast")
public class ConfigurationLomsServiceImpl implements ConfigurationLomsService {

    @Reference(version = "1.0.0")
    private RedisCacheService redisCacheService;

    @Override
    public void reloadConfiguration() {
        ConfigurationUtil.propertiesMap = redisCacheService.getAllValueByHash(RedisCacheConstant.CONFIG_PROP_KEY);
    }
}
