package com.fangxuele.ocs.common.constant;

public class SecurityConstant {

	// 加密迭代次数
	public static final int INTERATIONS = 1024;
	// 加密盐的位数
	public static final int SALT_SIZE = 8;
	// 加密算法
	public static final String ALGORITHM = "SHA-1";

	/**
	 * 登录用户
	 */
	String LOGIN_USER = "login_user";

	/**
	 * 验证码
	 */
	public final static String CAPTCHA_KEY = "captcha_key";

	/**
	 * 日志参数
	 */
	public final static String LOG_ARGUMENTS = "log_arguments";

	/**
	 * 动态查询，参数前缀
	 */
	public final static String SEARCH_PREFIX = "search_";

	/**
	 * 内部动态查询参数常量
	 */
	public final static String NEST_DYNAMIC_SEARCH = "$nest_dynamic_search$";

	/**
	 * 内部动态查询参数常量，logical
	 */
	public final static String NEST_DYNAMIC_SEARCH_LOGICAL = "$nest_dynamic_search_logical$";
}