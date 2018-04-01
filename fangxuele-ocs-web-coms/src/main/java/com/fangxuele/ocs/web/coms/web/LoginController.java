package com.fangxuele.ocs.web.coms.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rememberber(https://github.com/rememberber)
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String get() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String post(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, HttpServletRequest request, Model model) throws Exception {

        String msg = parseException(request);
        model.addAttribute("msg", msg);
        model.addAttribute("username", username);

        // 此方法不处理登录成功,由shiro进行处理.
        return "login";
    }

    /**
     * 解析SHIRO认证后返回的异常信息，将异常翻译为提示信息并返回
     *
     * @param request
     * @return
     */
    private String parseException(HttpServletRequest request) {
        String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);

        String msg = null;
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                msg = "账号不存在！";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "密码不正确！";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                msg = "验证码错误！";
            } else if (DisabledAccountException.class.getName().equals(exception)) {
                msg = "账户被禁用！";
            } else {
                msg = exception;
            }
        }
        return msg;
    }
}
