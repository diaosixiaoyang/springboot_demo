package com.fangxuele.ocs.web.coms.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 接口管理
 *
 * @author rememberber(https://github.com/rememberber)
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("/wechat")
    @RequiresPermissions("apiWechat:view")//权限管理;
    public String wechat(Model model) {

        return "lol/api/wechat_api";
    }

    @GetMapping("/m")
    @RequiresPermissions("apiM:view")//权限管理;
    public String m(Model model) {

        return "lol/api/m_api";
    }

    @GetMapping("/loms")
    @RequiresPermissions("apiLoms:view")//权限管理;
    public String loms(Model model) {

        return "lol/api/loms_api";
    }
}
