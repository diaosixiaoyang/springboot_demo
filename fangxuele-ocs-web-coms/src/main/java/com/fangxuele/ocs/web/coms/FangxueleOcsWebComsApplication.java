package com.fangxuele.ocs.web.coms;

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
public class FangxueleOcsWebComsApplication {

    private static final Logger logger = LoggerFactory.getLogger(FangxueleOcsWebComsApplication.class);

    public static ConfigurableApplicationContext configurableApplicationContext;

    public static void main(String[] args) {
        configurableApplicationContext = SpringApplication.run(FangxueleOcsWebComsApplication.class, args);

        logger.warn("web-coms 启动完毕！");
    }
}
