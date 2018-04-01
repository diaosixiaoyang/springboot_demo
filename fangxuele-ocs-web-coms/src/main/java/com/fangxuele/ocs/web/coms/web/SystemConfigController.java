package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.common.constant.RedisCacheConstant;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.inter.cache.service.RedisCacheService;
import com.fangxuele.ocs.inter.coms.service.ConfigurationLomsService;
import com.fangxuele.ocs.inter.m.service.ConfigurationMService;
import com.fangxuele.ocs.inter.wechat.service.ConfigurationWechatService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;
import org.springside.modules.utils.base.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 全局参数配置
 *
 * @author rememberber(https : / / github.com / rememberber)
 */
@Controller
@RequestMapping("/system/globalConfig")
public class SystemConfigController {

    private static final Logger logger = LoggerFactory.getLogger(SystemConfigController.class);

    @Reference(version = "1.0.0")
    private RedisCacheService redisCacheService;

    /**
     * dubbo广播调用所有负载均衡上的服务
     */
    @Reference(version = "1.0.0", cluster = "broadcast")
    private ConfigurationWechatService configurationWechatService;

    /**
     * dubbo广播调用所有负载均衡上的服务
     */
    @Reference(version = "1.0.0", cluster = "broadcast")
    private ConfigurationMService configurationMService;

    /**
     * dubbo广播调用所有负载均衡上的服务
     */
    @Reference(version = "1.0.0", cluster = "broadcast")
    private ConfigurationLomsService configurationLomsService;

    @GetMapping("/init")
    @RequiresPermissions("systemConfig:view")//权限管理;
    public String init(Model model) {

        Map<String, String> configMap = redisCacheService.getAllValueByHash(RedisCacheConstant.CONFIG_PROP_KEY);
        Map<String, String> sortedConfigMap = new TreeMap<>();
        sortedConfigMap.putAll(configMap);

        model.addAttribute("configMap", sortedConfigMap);
        return "lol/system/global_config";
    }

    /**
     * 保存配置参数
     *
     * @param request
     * @return
     */
    @Log(message = "用户[{0}]保存配置参数。", module = 20)
    @ResponseBody
    @RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
    public ResponseDTO saveConfig(HttpServletRequest request) {
        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            String configKey = map.get("configKey").toString();
            String configValue = map.get("configValue").toString();
            redisCacheService.putValueByHash(RedisCacheConstant.CONFIG_PROP_KEY, configKey, configValue);

            responseDTO.setSuccess(true);
            responseDTO.setStatusCode(0);
            responseDTO.setMsg("保存成功！");
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("保存配置参数失败！<br/>").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }
        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName()}));
        return responseDTO;
    }

    /**
     * 同步配置参数
     *
     * @param request
     * @return
     */
    @Log(message = "用户[{0}]同步配置参数。", module = 20)
    @ResponseBody
    @RequestMapping(value = "/refreshConfig", method = RequestMethod.GET)
    public ResponseDTO refreshConfig(HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        Map<String, Object> dataMap = new HashMap<>();

        List<Map<String, String>> resultList = new ArrayList<>();
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap = new HashMap<>();
            configurationWechatService.reloadConfiguration();
            resultMap.put("success", "true");
            resultMap.put("msg", "Service-Wechat 同步成功！");
        } catch (Exception e) {
            resultMap = new HashMap<>();
            resultMap.put("success", "false");
            resultMap.put("msg", "Service-Wechat 同步失败！<br/>" + e.getMessage());
            logger.error(new StringBuilder("同步全局参数失败失败！<br/>").append(ExceptionUtil.stackTraceText(e)).toString());
        } finally {
            resultList.add(resultMap);
        }

        try {
            resultMap = new HashMap<>();
            configurationMService.reloadConfiguration();
            resultMap.put("success", "true");
            resultMap.put("msg", "Service-M 同步成功！");
        } catch (Exception e) {
            resultMap = new HashMap<>();
            resultMap.put("success", "false");
            resultMap.put("msg", "Service-M 同步失败！<br/>" + e.getMessage());
            logger.error(new StringBuilder("同步全局参数失败失败！<br/>").append(ExceptionUtil.stackTraceText(e)).toString());
        } finally {
            resultList.add(resultMap);
        }

        try {
            resultMap = new HashMap<>();
            configurationLomsService.reloadConfiguration();
            resultMap.put("success", "true");
            resultMap.put("msg", "Service-COMS 同步成功！");
        } catch (Exception e) {
            resultMap = new HashMap<>();
            resultMap.put("success", "false");
            resultMap.put("msg", "Service-COMS 同步失败！<br/>" + e.getMessage());
            logger.error(new StringBuilder("同步全局参数失败失败！<br/>").append(ExceptionUtil.stackTraceText(e)).toString());
        } finally {
            resultList.add(resultMap);
        }

        dataMap.put("resultList", resultList);
        responseDTO.setDataMap(dataMap);
        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("同步成功！");

        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getName()}));
        return responseDTO;
    }

}
