package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.inter.coms.service.SystemUserService;
import com.fangxuele.ocs.web.coms.log.Log;
import com.fangxuele.ocs.web.coms.log.LogMessageObject;
import com.fangxuele.ocs.web.coms.log.impl.LogUitls;
import com.fangxuele.ocs.web.coms.service.ImageService;
import com.fangxuele.ocs.web.coms.service.LolShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;
import org.springside.modules.utils.base.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 *
 * @author rememberber(https : / / github.com / rememberber)
 */
@Controller
@RequestMapping("/system/user")
public class SystemUserController {

    private static final Logger logger = LoggerFactory.getLogger(SystemUserController.class);

    @Autowired
    private ImageService imageService;

    @Reference(version = "1.0.0")
    private SystemUserService systemUserService;

    /**
     * 修改头像页
     *
     * @param model
     * @return
     */
    @GetMapping("/avatar/init")
    @RequiresPermissions("system:view")//权限管理;
    public String avatarInit(Model model) {

        return "lol/system/user/avatar";
    }

    /**
     * 获取当前头像
     *
     * @return
     */
    @PostMapping("/avatar/getCurrentAvatar")
    @ResponseBody
    public ResponseDTO getCurrentAvatar() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();

            responseDTO.setSuccess(true);
            responseDTO.setStatusCode(0);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("headImage", shiroUser.getHeadImage());
            responseDTO.setDataMap(dataMap);

        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("获取当前头像失败!<br/>").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 保存头像
     *
     * @return
     */
    @Log(message = "小编[{0}]保存图像为{1}。", module = 10)
    @PostMapping("/avatar/saveAvatar")
    @ResponseBody
    public ResponseDTO saveAvatar(HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        String avatarUrl = null;
        try {
            LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 获得文件：
            MultipartFile file = multipartRequest.getFile("file");
            // 获得文件名：
            String filename = file.getOriginalFilename();

            avatarUrl = imageService.uploadAvatar(file);
            systemUserService.saveAvatar(shiroUser.getId(), avatarUrl);
            shiroUser.setHeadImage(avatarUrl);
            responseDTO.setSuccess(true);
            responseDTO.setMsg("保存成功！");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("avatarUrl", avatarUrl);
            responseDTO.setDataMap(dataMap);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("保存头像失败！<br/>").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }
        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName(), avatarUrl}));
        return responseDTO;
    }

    /**
     * 修改密码页
     *
     * @param model
     * @return
     */
    @GetMapping("/password/init")
    @RequiresPermissions("system:view")//权限管理;
    public String passwordInit(Model model) {

        return "lol/system/user/password";
    }

    /**
     * 修改密码
     *
     * @return
     */
    @PostMapping("/password/modify")
    @ResponseBody
    public ResponseDTO modifyPassword(HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();

            Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
            map.put("id", shiroUser.getId());

            responseDTO = systemUserService.modifyPassword(map);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("修改密码失败！<br/>").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 主题样式页
     *
     * @param model
     * @return
     */
    @GetMapping("/theme/init")
    @RequiresPermissions("system:view")//权限管理;
    public String themeInit(Model model) {

        return "lol/system/user/theme";
    }

    /**
     * 换主题换肤
     *
     * @return
     */
    @RequestMapping(value = "theme/changeTheme", method = RequestMethod.POST)
    public String changeTheme() {
        try {
            LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
            String theme = systemUserService.changeTheme(shiroUser.getId());
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(true);
            session.setAttribute("theme", theme);
        } catch (Exception e) {
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return "lol/system/user/theme";
    }
}
