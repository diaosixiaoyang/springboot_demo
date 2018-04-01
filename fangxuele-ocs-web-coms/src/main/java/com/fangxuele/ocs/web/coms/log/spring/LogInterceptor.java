package com.fangxuele.ocs.web.coms.log.spring;

import com.fangxuele.ocs.web.coms.log.Log;
import com.fangxuele.ocs.web.coms.log.LogAPI;
import com.fangxuele.ocs.web.coms.log.LogLevel;
import com.fangxuele.ocs.web.coms.log.LogMessageObject;
import com.fangxuele.ocs.web.coms.log.impl.LogUitls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springside.modules.utils.base.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;


@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    private LogAPI logAPI;

    /**
     * 将request存入LogUitl中的LOCAL_REQUEST。
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     * @see HandlerInterceptorAdapter#preHandle(HttpServletRequest,
     * HttpServletResponse, Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LogUitls.putRequest(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        final Log log = method.getAnnotation(Log.class);
        if (log != null) {
            // 得到LogMessageObject
            final LogMessageObject logMessageObject = LogUitls.getArgs();
            // 另起线程异步操作
            new Thread(() -> {
                try {
                    LogLevel lastLogLevel = logAPI.getRootLogLevel();

                    // 先对自定义包等级做判断
                    Map<String, LogLevel> customLogLevel = logAPI.getCustomLogLevel();
                    if (!customLogLevel.isEmpty()) {
                        Class<?> clazz = handlerMethod.getBean().getClass();
                        String packageName = clazz.getPackage().getName();

                        Set<String> keys = customLogLevel.keySet();
                        for (String key : keys) {
                            if (packageName.startsWith(key)) {
                                lastLogLevel = customLogLevel.get(key);
                                break;
                            }
                        }
                    }

                    LogMessageObject defaultLogMessageObject = logMessageObject;
                    if (defaultLogMessageObject == null) {
                        defaultLogMessageObject = LogMessageObject.newWrite();
                    }

                    if (defaultLogMessageObject.isWritten()) { // 判断是否写入log
                        // 覆盖，直接写入日志
                        if (log.override()) {
                            logAPI.log(log.module(), log.message(), defaultLogMessageObject.getObjects(),
                                    log.level());
                        } else {
                            // 不覆盖，参考方法的日志等级是否大于等于最终的日志等级
                            if (!log.override() && log.level().compareTo(lastLogLevel) >= 0) {
                                logAPI.log(log.module(), log.message(), defaultLogMessageObject.getObjects(),
                                        log.level());
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(ExceptionUtil.stackTraceText(e));
                }
            }
            ).start();
        }
    }

    /**
     * 清除LogUitl中的LOCAL_REQUEST。
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     * @see HandlerInterceptorAdapter#afterCompletion(HttpServletRequest,
     * HttpServletResponse, Object,
     * Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LogUitls.removeRequest();
    }

    public void setLogAPI(LogAPI logAPI) {
        this.logAPI = logAPI;
    }
}
