package cn.xyy.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
/**
 * 当数据源只有一个时，只需要通过配置文件application.properties来进行设置相关属性即可
 * spring.datasource.driver-class-name=com.mysql.jdbc.Driver
 * spring.datasource.url=jdbc\:mysql\://localhost\:3306/
 * spring.datasource.username=root
 * spring.datasource.password=root
 */

/**
 * 如果数据源不止一个时，需要通过此类来进行配置，并设置主数据源和次数据源
 * @author Sun
 */
@Configuration
public class DataSourceConfig {
	
	/**
	 * 设置第一个数据源
	 * spring.datasource.primary.driver-class-name=com.mysql.jdbc.Driver
	 * spring.datasource.primary.url=jdbc\:mysql\://localhost\:3306/
	 * spring.datasource.primary.username=root
	 * spring.datasource.primary.password=root
	 * @return
	 */
	@Primary	//将此数据源设置为主要的数据源
	@Bean(name = "primaryDataSource")
	@Qualifier("primaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.primary")	//配置文件属性前缀
	public DataSource primaryDataSource(){
		return new DruidDataSource();	//此处因为application.properties使用的是阿里的DruidDataSource
	}
	
	/**
	 * 设置第二个数据源
	 * @return
	 */
	@Bean(name = "secondaryDataSource")
	@Qualifier("secondaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.secondary")
	public DataSource secondaryDataSource(){
		return new DruidDataSource();
	}
}
