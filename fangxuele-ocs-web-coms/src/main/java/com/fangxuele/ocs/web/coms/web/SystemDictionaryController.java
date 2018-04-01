package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.cache.service.RedisCacheService;
import com.fangxuele.ocs.inter.coms.service.DictionaryLomsService;
import com.fangxuele.ocs.inter.coms.service.SystemDictionaryService;
import com.fangxuele.ocs.inter.m.service.DictionaryMService;
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

/**
 * 全局参数配置
 *
 * @author rememberber(https : / / github.com / rememberber)
 */
@Controller
@RequestMapping("/system/dictionary")
public class SystemDictionaryController {

    private static final Logger logger = LoggerFactory.getLogger(SystemDictionaryController.class);

    @Reference(version = "1.0.0")
    private RedisCacheService redisCacheService;

    @Reference(version = "1.0.0")
    private SystemDictionaryService systemDictionaryService;

    /**
     * dubbo广播调用所有负载均衡上的服务
     */
    @Reference(version = "1.0.0", cluster = "broadcast")
    private DictionaryMService dictionaryMService;

    /**
     * dubbo广播调用所有负载均衡上的服务
     */
    @Reference(version = "1.0.0", cluster = "broadcast")
    private DictionaryLomsService dictionaryLomsService;

    @GetMapping("/init")
    @RequiresPermissions("systemDictionary:view")//权限管理;
    public String init(Model model) {
        // 搜索参数（下拉列表参数）
        Map<String, Map<String, String>> searchParaMap = systemDictionaryService.getSearchParaMap();
        model.addAttribute("statusMap", dictionaryLomsService.getStatueMap(DictionaryUtil.ChannelStatus));
        model.addAllAttributes(searchParaMap);

        return "lol/system/dictionary";
    }

    /**
     * 同步配置参数
     *
     * @param request
     * @return
     */
    @Log(message = "用户[{0}]同步字典配置参数。", module = 20)
    @ResponseBody
    @RequestMapping(value = "/refreshDictionary", method = RequestMethod.GET)
    public ResponseDTO refreshDictionary(HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        Map<String, Object> dataMap = new HashMap<>();

        List<Map<String, String>> resultList = new ArrayList<>();
        Map<String, String> resultMap = new HashMap<>();

        try {
            resultMap = new HashMap<>();
            dictionaryMService.initDictionary();
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
            dictionaryLomsService.initDictionary();
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

    /**
     * 查询字典列表
     *
     * @return 返回结果json
     */
    @ResponseBody
    @RequestMapping(value = "/getDictionaryList", method = RequestMethod.POST)
    public Map<String, Object> getDictionaryList(HttpServletRequest request) {
        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);

        // 将grid中的页大小、起始记录数赋值到CommonPage对象中
        CommonPage page = new CommonPage();
        page.setOffset(Integer.valueOf(map.get("start").toString()));
        page.setPageSize(Integer.valueOf(map.get("length").toString()));

        page = systemDictionaryService.getDictionaryList(map, page);

        // 操作信息的map，将map通过json方式返回给页面
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("draw", map.get("draw").toString());
        returnMap.put("recordsTotal", page.getTotalCount());
        returnMap.put("recordsFiltered", page.getTotalCount());
        returnMap.put("data", page.getResult());

        return returnMap;
    }
}
