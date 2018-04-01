package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.DictionaryLomsService;
import com.fangxuele.ocs.inter.coms.service.SystemMemberService;
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
 * 系统成员管理
 *
 * @author rememberber(https : / / github.com / rememberber)
 */
@Controller
@RequestMapping("/system/member")
public class SystemMemberController {

    private static final Logger logger = LoggerFactory.getLogger(SystemMemberController.class);

    @Reference(version = "1.0.0")
    private SystemMemberService systemMemberService;

    @Reference(version = "1.0.0")
    private DictionaryLomsService dictionaryLomsService;

    @GetMapping("/init")
    @RequiresPermissions("systemMember:view")//权限管理;
    public String init(Model model) {

        // 搜索参数（下拉列表参数）
        Map<String, Map<String, String>> searchParaMap = systemMemberService.getSearchParaMap();
        model.addAttribute("statusMap", dictionaryLomsService.getStatueMap(DictionaryUtil.SYS_USER_STATUS));
        model.addAllAttributes(searchParaMap);

        return "lol/system/member";
    }

    /**
     * 查询成员列表
     *
     * @return 返回结果json
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberList", method = RequestMethod.POST)
    public Map<String, Object> getMemberList(HttpServletRequest request) {
        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);

        // 将grid中的页大小、起始记录数赋值到CommonPage对象中
        CommonPage page = new CommonPage();
        page.setOffset(Integer.valueOf(map.get("start").toString()));
        page.setPageSize(Integer.valueOf(map.get("length").toString()));

        page = systemMemberService.getMemberList(map, page);

        // 操作信息的map，将map通过json方式返回给页面
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("draw", map.get("draw").toString());
        returnMap.put("recordsTotal", page.getTotalCount());
        returnMap.put("recordsFiltered", page.getTotalCount());
        returnMap.put("data", page.getResult());

        return returnMap;
    }

    /**
     * 禁用成员
     *
     * @param userId
     * @return
     */
    @Log(message = "用户[{0}]禁用ID为[{1}]的成员。", module = 20)
    @ResponseBody
    @RequestMapping(value = "/disableUser/{userId}", method = RequestMethod.POST)
    public ResponseDTO disableUser(@PathVariable Long userId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemMemberService.disableUser(userId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg("禁用成员失败！");
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName(), userId}));
        return responseDTO;
    }

    /**
     * 启用成员
     *
     * @param userId
     * @return
     */
    @Log(message = "用户[{0}]启用ID为[{1}]的成员。", module = 20)
    @ResponseBody
    @RequestMapping(value = "/enableUser/{userId}", method = RequestMethod.POST)
    public ResponseDTO enableUser(@PathVariable Long userId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemMemberService.enableUser(userId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("启用成员失败！").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }
        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName(), userId}));
        return responseDTO;
    }

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    @PostMapping("/getUserRoleDetail/{userId}")
    @ResponseBody
    public ResponseDTO getUserRoleDetail(@PathVariable Long userId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemMemberService.getUserRoleDetail(userId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("获取用户角色失败!<br/>").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 分配用户角色保存
     *
     * @param request
     * @return
     */
    @Log(message = "用户[{0}]为ID为[{1}]的成员分配角色。", module = 20)
    @PostMapping("/editUserRoleSave")
    @ResponseBody
    public ResponseDTO editUserRoleSave(HttpServletRequest request) {
        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            responseDTO = systemMemberService.editUserRoleSave(map);

        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("保存失败!\n").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName(), map.get("userId")}));
        return responseDTO;
    }

    /**
     * 获取成员详情
     *
     * @param userId
     * @return
     */
    @PostMapping("/getUserDetail/{userId}")
    @ResponseBody
    public ResponseDTO getUserDetail(@PathVariable Long userId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemMemberService.getUserDetail(userId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("获取成员详情失败!<br/>").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 编辑成员保存
     *
     * @param request
     * @return
     */
    @Log(message = "用户[{0}]编辑ID为[{1}]的成员详情。", module = 20)
    @PostMapping("/editUserSave")
    @ResponseBody
    public ResponseDTO editUserSave(HttpServletRequest request) {

        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            responseDTO = systemMemberService.editUserSave(map);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("保存失败!\n").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }
        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName(), map.get("userId")}));
        return responseDTO;
    }

}
