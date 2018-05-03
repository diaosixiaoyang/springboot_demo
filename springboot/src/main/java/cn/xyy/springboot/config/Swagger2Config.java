package cn.xyy.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * springboot结合swagger2生成api文档信息
 * 访问http://localhost:8080/swagger-ui.html来进行接口文档查看与测试
 * @author Sun
 *
 */
@Configuration
@EnableSwagger2	//开启swagger2显示接口文档
public class Swagger2Config {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("cn.xyy.springboot.springboot.controller"))
				//设置接口类的包路径
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("swagger2接口文档")
				.description("springboot结合swagger2构建接口文件")
				.termsOfServiceUrl("http://localhost:8080/api")
				.contact("swagger2接口案例解析")
				.version("1.0.0")
				.build();
	}
}