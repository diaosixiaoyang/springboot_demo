package com.fangxuele.ocs.web.coms.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author rememberber(https://github.com/rememberber)
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value={org.apache.shiro.authz.UnauthorizedException.class})
    public String errorHandlerOverJson(org.apache.shiro.authz.UnauthorizedException exception) {
        return "unauth_page";
    }
}
