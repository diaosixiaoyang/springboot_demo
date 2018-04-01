package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.DictionaryLomsService;
import com.fangxuele.ocs.inter.coms.service.SystemRoleService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;
import org.springside.modules.utils.base.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统角色管理
 *
 * @author rememberber(https://github.com/rememberber)
 */
@Controller
@RequestMapping("/system/role")
public class SystemRoleController {

    private static final Logger logger = LoggerFactory.getLogger(SystemRoleController.class);

    @Reference(version = "1.0.0")
    private SystemRoleService systemRoleService;

    @Reference(version = "1.0.0")
    private DictionaryLomsService dictionaryLomsService;

    @GetMapping("/init")
    @RequiresPermissions("systemRole:view")//权限管理;
    public String init(Model model) {

        // 搜索参数（下拉列表参数）
        Map<String, Map<String, String>> searchParaMap = systemRoleService.getSearchParaMap();
        model.addAttribute("statusMap", dictionaryLomsService.getStatueMap(DictionaryUtil.SYS_USER_STATUS));
        model.addAllAttributes(searchParaMap);

        return "lol/system/role";
    }

    /**
     * 查询角色列表
     *
     * @return 返回结果json
     */
    @ResponseBody
    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    public Map<String, Object> getRoleList(HttpServletRequest request) {
        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);

        // 将grid中的页大小、起始记录数赋值到CommonPage对象中
        CommonPage page = new CommonPage();
        page.setOffset(Integer.valueOf(map.get("start").toString()));
        page.setPageSize(Integer.valueOf(map.get("length").toString()));

        page = systemRoleService.getRoleList(map, page);

        // 操作信息的map，将map通过json方式返回给页面
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("draw", map.get("draw").toString());
        returnMap.put("recordsTotal", page.getTotalCount());
        returnMap.put("recordsFiltered", page.getTotalCount());
        returnMap.put("data", page.getResult());

        return returnMap;
    }

    /**
     * 禁用角色
     *
     * @param roleId
     * @return
     */
    @Log(message = "用户[{0}]禁用ID为[{1}]的角色。", module = 20)
    @ResponseBody
    @RequestMapping(value = "/disableRole/{roleId}", method = RequestMethod.POST)
    public ResponseDTO disableRole(@PathVariable Long roleId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemRoleService.disableRole(roleId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg("禁用角色失败！");
            logger.error(ExceptionUtil.stackTraceText(e));
        }
        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName(), roleId}));
        return responseDTO;
    }

    /**
     * 启用角色
     *
     * @param roleId
     * @return
     */
    @Log(message = "用户[{0}]启用ID为[{1}]的角色。", module = 20)
    @ResponseBody
    @RequestMapping(value = "/enableRole/{roleId}", method = RequestMethod.POST)
    public ResponseDTO enableRole(@PathVariable Long roleId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemRoleService.enableRole(roleId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("启用角色失败！").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }
        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName(), roleId}));
        return responseDTO;
    }

    /**
     * 获取角色详情
     *
     * @param roleId
     * @return
     */
    @PostMapping("/getRoleDetail/{roleId}")
    @ResponseBody
    public ResponseDTO getRoleDetail(@PathVariable Long roleId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemRoleService.getRoleDetail(roleId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("获取角色详情失败!<br/>").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 编辑角色保存
     *
     * @param request
     * @return
     */
    @PostMapping("/editRoleSave")
    @ResponseBody
    public ResponseDTO editRoleSave(HttpServletRequest request) {

        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            responseDTO = systemRoleService.editRoleSave(map);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("保存失败!\n").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 获取角色权限
     *
     * @param roleId
     * @return
     */
    @PostMapping("/getRolePermissionDetail/{roleId}")
    @ResponseBody
    public ResponseDTO getRolePermissionDetail(@PathVariable Long roleId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemRoleService.getRolePermissionDetail(roleId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("获取角色权限失败!<br/>").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 分配角色权限保存
     *
     * @param request
     * @return
     */
    @PostMapping("/editRolePermissionSave")
    @ResponseBody
    public ResponseDTO editRolePermissionSave(HttpServletRequest request) {
        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            responseDTO = systemRoleService.editRolePermissionSave(map);

        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("保存失败!\n").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 创建新角色保存
     *
     * @param request
     * @return
     */
    @PostMapping("/addRoleSave")
    @ResponseBody
    public ResponseDTO addRoleSave(HttpServletRequest request) {

        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            responseDTO = systemRoleService.addRoleSave(map);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("创建新角色失败!\n").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

}
