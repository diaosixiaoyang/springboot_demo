package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.inter.coms.service.SysIndexService;
import com.fangxuele.ocs.inter.coms.service.SystemMemberService;
import com.fangxuele.ocs.mapper.domain.TSysNotice;
import com.fangxuele.ocs.mapper.domain.TSysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Reference(version = "1.0.0")
    private SysIndexService sysIndexService;

    @Reference(version = "1.0.0")
    private SystemMemberService systemMemberService;

    @RequestMapping(value = {"/", "/index"})
    @RequiresPermissions("index:view")//权限管理;
    public String index(Model model) {
        List<TSysNotice> noticeList = sysIndexService.getAllEnabledNotice();
        List<TSysUser> memberList = systemMemberService.getAllEnabled();

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("memberList", memberList);
        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            return "redirect:/";
        }
        return "index";
    }

    @RequestMapping("/403")
    public String unauth(Model model) {
        return "unauth_page";
    }

}
