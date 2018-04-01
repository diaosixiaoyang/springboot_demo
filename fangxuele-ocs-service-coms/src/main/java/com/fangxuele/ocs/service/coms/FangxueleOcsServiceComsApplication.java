package com.fangxuele.ocs.service.coms;

import com.fangxuele.ocs.service.coms.service.impl.ConfigurationLomsServiceImpl;
import com.fangxuele.ocs.service.coms.service.impl.DictionaryLomsServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.fangxuele.ocs.mapper.mapper")
@EnableScheduling
public class FangxueleOcsServiceComsApplication {

    private static final Logger logger = LoggerFactory.getLogger(FangxueleOcsServiceComsApplication.class);

    public static ConfigurableApplicationContext configurableApplicationContext;

    public static void main(String[] args) {
        configurableApplicationContext = SpringApplication.run(FangxueleOcsServiceComsApplication.class, args);

        ConfigurationLomsServiceImpl configurationMService = configurableApplicationContext.getBean(ConfigurationLomsServiceImpl.class);
        // 从缓存加载配置到内存
        configurationMService.reloadConfiguration();
        logger.warn("从缓存加载配置到内存完成！");
        DictionaryLomsServiceImpl dictionaryLomsService = configurableApplicationContext.getBean(DictionaryLomsServiceImpl.class);
        dictionaryLomsService.initDictionary();
        logger.warn("数据字典缓存初始化完成！");
        logger.warn("service-loms 启动完毕！");

    }
}
