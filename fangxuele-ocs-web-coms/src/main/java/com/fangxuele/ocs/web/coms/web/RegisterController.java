package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.inter.coms.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author rememberber(https://github.com/rememberber)
 */
@Controller
public class RegisterController {

    @Reference(version = "1.0.0")
    private UserService userService;

    @GetMapping("/register")
    public String get() {
        return "register";
    }

    @PostMapping("/register/save")
    public String post(HttpServletRequest request, Model model) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);

        map = userService.register(map);
        if (map.containsKey("msg")) {
            model.addAllAttributes(map);
            return "register";
        }
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(map.get("userName").toString());
        token.setPassword(map.get("password").toString().toCharArray());
        SecurityUtils.getSubject().login(token);
        return "redirect:/";
    }

}
