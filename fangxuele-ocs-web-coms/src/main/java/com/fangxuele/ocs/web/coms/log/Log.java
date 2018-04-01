package com.fangxuele.ocs.web.coms.log;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ METHOD })
@Retention(RUNTIME)
public @interface Log {

	/**
	 * 操作模块编号 登录登出(1) 我的信息(2) 首页(3) 展示板(4) 图书(5) 订单(6) 用户(7) 区域(8)
	 * 微信(9) 广告(10) 配送(11) 系统(20)
	 * 
	 * @return
	 */
	int module();

	/**
	 * 
	 * 日志信息
	 * 
	 * @return
	 */
	String message();

	/**
	 * 
	 * 日志记录等级
	 * 
	 * @return
	 */
	LogLevel level() default LogLevel.TRACE;

	/**
	 * 
	 * 是否覆盖包日志等级 1.为false不会参考level属性。 2.为true会参考level属性。
	 * 
	 * @return
	 */
	boolean override() default false;
}
