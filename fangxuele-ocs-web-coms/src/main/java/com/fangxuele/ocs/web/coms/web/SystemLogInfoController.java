package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.inter.coms.service.SystemLogInfoService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作记录管理
 *
 * @author rememberber(https://github.com/rememberber)
 */
@Controller
@RequestMapping("/system/logInfo")
public class SystemLogInfoController {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogInfoController.class);

    @Reference(version = "1.0.0")
    private SystemLogInfoService systemLogInfoService;

    @GetMapping("/init")
    @RequiresPermissions("systemLogInfo:view")//权限管理;
    public String init(Model model) {


        // 搜索参数（下拉列表参数）
        Map<String, Object> searchParaMap = systemLogInfoService.getSearchParaMap();
        model.addAllAttributes(searchParaMap);

        return "lol/system/log_info";
    }

    /**
     * 查询列表
     *
     * @return 返回结果json
     */
    @ResponseBody
    @RequestMapping(value = "/getLogInfoList", method = RequestMethod.POST)
    public Map<String, Object> getLogInfoList(HttpServletRequest request) {
        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);

        // 将grid中的页大小、起始记录数赋值到CommonPage对象中
        CommonPage page = new CommonPage();
        page.setOffset(Integer.valueOf(map.get("start").toString()));
        page.setPageSize(Integer.valueOf(map.get("length").toString()));

        page = systemLogInfoService.getLogInfoList(map, page);

        // 操作信息的map，将map通过json方式返回给页面
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("draw", map.get("draw").toString());
        returnMap.put("recordsTotal", page.getTotalCount());
        returnMap.put("recordsFiltered", page.getTotalCount());
        returnMap.put("data", page.getResult());

        return returnMap;
    }

}
