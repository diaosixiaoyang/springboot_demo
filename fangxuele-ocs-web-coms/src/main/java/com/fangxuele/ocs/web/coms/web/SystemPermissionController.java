package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.DictionaryLomsService;
import com.fangxuele.ocs.inter.coms.service.SystemPermissionService;
import com.fangxuele.ocs.web.coms.log.Log;
import com.fangxuele.ocs.web.coms.log.LogMessageObject;
import com.fangxuele.ocs.web.coms.log.impl.LogUitls;
import com.fangxuele.ocs.web.coms.service.LolShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;
import org.springside.modules.utils.base.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统权限管理
 *
 * @author rememberber(https://github.com/rememberber)
 */
@Controller
@RequestMapping("/system/permission")
public class SystemPermissionController {

    private static final Logger logger = LoggerFactory.getLogger(SystemPermissionController.class);

    @Reference(version = "1.0.0")
    private SystemPermissionService systemPermissionService;

    @Reference(version = "1.0.0")
    private DictionaryLomsService dictionaryLomsService;

    @GetMapping("/init")
    @RequiresPermissions("systemPermission:view")//权限管理;
    public String init(Model model) {

        // 搜索参数（下拉列表参数）
        Map<String, Map<String, String>> searchParaMap = systemPermissionService.getSearchParaMap();
        model.addAttribute("statusMap", dictionaryLomsService.getStatueMap(DictionaryUtil.SYS_USER_STATUS));
        model.addAllAttributes(searchParaMap);

        return "lol/system/permission";
    }

    /**
     * 查询权限列表
     *
     * @return 返回结果json
     */
    @ResponseBody
    @RequestMapping(value = "/getPermissionList", method = RequestMethod.POST)
    public Map<String, Object> getPermissionList(HttpServletRequest request) {
        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);

        // 将grid中的页大小、起始记录数赋值到CommonPage对象中
        CommonPage page = new CommonPage();
        page.setOffset(Integer.valueOf(map.get("start").toString()));
        page.setPageSize(Integer.valueOf(map.get("length").toString()));

        page = systemPermissionService.getPermissionList(map, page);

        // 操作信息的map，将map通过json方式返回给页面
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("draw", map.get("draw").toString());
        returnMap.put("recordsTotal", page.getTotalCount());
        returnMap.put("recordsFiltered", page.getTotalCount());
        returnMap.put("data", page.getResult());

        return returnMap;
    }

    /**
     * 禁用权限
     *
     * @param permissionId
     * @return
     */
    @Log(message = "用户[{0}]禁用ID为[{1}]的权限。", module = 20)
    @ResponseBody
    @RequestMapping(value = "/disablePermission/{permissionId}", method = RequestMethod.POST)
    public ResponseDTO disablePermission(@PathVariable Long permissionId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemPermissionService.disablePermission(permissionId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg("禁用权限失败！");
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName(), permissionId}));
        return responseDTO;
    }

    /**
     * 启用权限
     *
     * @param permissionId
     * @return
     */
    @Log(message = "用户[{0}]启用ID为[{1}]的权限。", module = 20)
    @ResponseBody
    @RequestMapping(value = "/enablePermission/{permissionId}", method = RequestMethod.POST)
    public ResponseDTO enablePermission(@PathVariable Long permissionId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemPermissionService.enablePermission(permissionId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("启用权限失败！").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }
        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName(), permissionId}));
        return responseDTO;
    }
}
