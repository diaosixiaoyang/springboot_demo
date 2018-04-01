package com.fangxuele.ocs.web.coms.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.DictionaryLomsService;
import com.fangxuele.ocs.inter.coms.service.SystemNoticeService;
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
 * 系统公告管理
 *
 * @author rememberber(https://github.com/rememberber)
 */
@Controller
@RequestMapping("/system/notice")
public class SystemNoticeController {

    private static final Logger logger = LoggerFactory.getLogger(SystemNoticeController.class);

    @Reference(version = "1.0.0")
    private SystemNoticeService systemNoticeService;

    @Reference(version = "1.0.0")
    private DictionaryLomsService dictionaryLomsService;

    @GetMapping("/init")
    @RequiresPermissions("systemNotice:view")//权限管理;
    public String init(Model model) {

        // 搜索参数（下拉列表参数）
        Map<String, Map<String, String>> searchParaMap = systemNoticeService.getSearchParaMap();
        model.addAttribute("statusMap", dictionaryLomsService.getStatueMap(DictionaryUtil.SYS_NOTICE_STATUS));
        model.addAllAttributes(searchParaMap);

        return "lol/system/notice";
    }

    /**
     * 查询公告列表
     *
     * @return 返回结果json
     */
    @ResponseBody
    @RequestMapping(value = "/getNoticeList", method = RequestMethod.POST)
    public Map<String, Object> getNoticeList(HttpServletRequest request) {
        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);

        // 将grid中的页大小、起始记录数赋值到CommonPage对象中
        CommonPage page = new CommonPage();
        page.setOffset(Integer.valueOf(map.get("start").toString()));
        page.setPageSize(Integer.valueOf(map.get("length").toString()));

        page = systemNoticeService.getNoticeList(map, page);

        // 操作信息的map，将map通过json方式返回给页面
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("draw", map.get("draw").toString());
        returnMap.put("recordsTotal", page.getTotalCount());
        returnMap.put("recordsFiltered", page.getTotalCount());
        returnMap.put("data", page.getResult());

        return returnMap;
    }

    /**
     * 禁用公告
     *
     * @param noticeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/disableNotice/{noticeId}", method = RequestMethod.POST)
    public ResponseDTO disableNotice(@PathVariable Long noticeId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemNoticeService.disableNotice(noticeId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg("禁用公告失败！");
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 启用公告
     *
     * @param noticeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/enableNotice/{noticeId}", method = RequestMethod.POST)
    public ResponseDTO enableNotice(@PathVariable Long noticeId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemNoticeService.enableNotice(noticeId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("启用公告失败！").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }
        return responseDTO;
    }

    /**
     * 删除公告
     *
     * @param noticeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteNotice/{noticeId}", method = RequestMethod.POST)
    public ResponseDTO deleteNotice(@PathVariable Long noticeId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemNoticeService.deleteNotice(noticeId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("删除公告失败！").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }
        return responseDTO;
    }

    /**
     * 获取公告详情
     *
     * @param noticeId
     * @return
     */
    @PostMapping("/getNoticeDetail/{noticeId}")
    @ResponseBody
    public ResponseDTO getNoticeDetail(@PathVariable Long noticeId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = systemNoticeService.getNoticeDetail(noticeId);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("获取公告详情失败!<br/>").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 编辑公告保存
     *
     * @param request
     * @return
     */
    @PostMapping("/editNoticeSave")
    @ResponseBody
    public ResponseDTO editNoticeSave(HttpServletRequest request) {

        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            responseDTO = systemNoticeService.editNoticeSave(map);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("保存失败!\n").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

    /**
     * 添加新公告保存
     *
     * @param request
     * @return
     */
    @PostMapping("/addNoticeSave")
    @ResponseBody
    public ResponseDTO addNoticeSave(HttpServletRequest request) {

        // 将request对象中的请求URL中的参数都放在Map中
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
        ResponseDTO responseDTO = new ResponseDTO();
        LolShiroRealm.ShiroUser shiroUser = (LolShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        try {
            map.put("userId", shiroUser.getId());
            responseDTO = systemNoticeService.addNoticeSave(map);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setStatusCode(101);
            responseDTO.setMsg(new StringBuilder("添加新公告失败!\n").append(e.getMessage()).toString());
            logger.error(ExceptionUtil.stackTraceText(e));
        }

        return responseDTO;
    }

}
