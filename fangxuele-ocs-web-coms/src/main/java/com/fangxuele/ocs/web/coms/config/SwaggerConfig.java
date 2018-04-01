package com.fangxuele.ocs.web.coms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by zhouy on 2017/2/17.
 */
@Configuration
@EnableSwagger2
@Profile(value = {"dev", "test"})
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fangxuele.ocs.web.coms.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("亲子活动年卡-地球总部-LOMS")
                .description("亲子活动年卡，你的欢乐年华")
                .termsOfServiceUrl("http://m.fangxuele.com/t/#/home")
                .contact(new Contact("北京行知合一科技有限公司", "http://www.fangxuele.com/", ""))
                .version("1.0")
                .build();
    }
}
