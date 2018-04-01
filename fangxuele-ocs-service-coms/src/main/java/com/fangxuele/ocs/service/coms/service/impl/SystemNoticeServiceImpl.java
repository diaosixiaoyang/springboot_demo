package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.dictionary.SystemDictionary;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.exception.ServiceException;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.SystemNoticeService;
import com.fangxuele.ocs.mapper.domain.TSysNotice;
import com.fangxuele.ocs.mapper.mapper.TSysNoticeMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.base.ExceptionUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rememberber(https://github.com/rememberber)
 */
@Service(version = "1.0.0", interfaceClass = SystemNoticeService.class, timeout = 10000, cluster = "failfast")
public class SystemNoticeServiceImpl implements SystemNoticeService {

    private static final Logger logger = LoggerFactory.getLogger(SystemNoticeServiceImpl.class);

    @Autowired
    TSysNoticeMapper sysNoticeMapper;

    @Override
    public Map<String, Map<String, String>> getSearchParaMap() {
        Map<String, Map<String, String>> searchParaMap = new HashMap<>();

        Map<String, String> creatorMap = new LinkedHashMap<>();
        List<Map<String, Object>> resultList = sysNoticeMapper.selectAllCreator();
        for (Map resultMap : resultList) {
            creatorMap.put(resultMap.get("create_user").toString(), resultMap.get("nick_name").toString());
        }

        searchParaMap.put("creators", creatorMap);

        return searchParaMap;
    }

    /**
     * 查询公告列表
     *
     * @param valueMap
     * @param page
     * @return
     */
    @Override
    public CommonPage getNoticeList(Map<String, Object> valueMap, CommonPage page) {
        Map<String, Object> paraMap = new HashMap<>();
        try {
            if (valueMap.containsKey("title") && StringUtils.isNotBlank(valueMap.get("title").toString())) {
                paraMap.put("title", "%" + valueMap.get("title").toString() + "%");
            }
            if (valueMap.containsKey("content") && StringUtils.isNotBlank(valueMap.get("content").toString())) {
                paraMap.put("content", "%" + valueMap.get("content").toString() + "%");
            }
            if (valueMap.containsKey("level") && StringUtils.isNotBlank(valueMap.get("level").toString())) {
                paraMap.put("level", valueMap.get("level").toString());
            }
            if (valueMap.containsKey("createUser") && StringUtils.isNotBlank(valueMap.get("createUser").toString())) {
                paraMap.put("createUser", Integer.parseInt(valueMap.get("createUser").toString()));
            }
            if (valueMap.containsKey("dateFrom") && StringUtils.isNotBlank(valueMap.get("dateFrom").toString())) {
                paraMap.put("dateFrom", DateUtils.parseDate(valueMap.get("dateFrom").toString(), "yyyy-MM-dd"));
            }
            if (valueMap.containsKey("dateTo") && StringUtils.isNotBlank(valueMap.get("dateTo").toString())) {
                // 选择的截止日期加一天，保证了查询条件的完整性，解决了时分秒的问题。
                paraMap.put("dateTo", DateUtils.ceiling(
                        DateUtils.parseDate(valueMap.get("dateTo").toString(), "yyyy-MM-dd"), Calendar.DAY_OF_MONTH));
            }
            if (valueMap.containsKey("status") && StringUtils.isNotBlank(valueMap.get("status").toString())) {
                paraMap.put("status", Integer.parseInt(valueMap.get("status").toString()));
            }
            paraMap.put("orderBy", "tsn.create_time desc");
        } catch (Exception e) {
            logger.error(ExceptionUtil.stackTraceText(e));
            throw new ServiceException("生成查询条件时出错！");
        }

        PageHelper.offsetPage(page.getOffset(), page.getPageSize());
        List<Map<String, Object>> list = sysNoticeMapper.getNoticeList(paraMap);
        page.setResult(list);
        page.setTotalCount(((Page) list).getTotal());

        Date date;
        for (Map<String, Object> amap : list) {
            if (amap.get("status") != null && StringUtils.isNotBlank(amap.get("status").toString())) {
                amap.put("statusString", DictionaryUtil.getString(DictionaryUtil.SYS_NOTICE_STATUS,
                        (Integer) amap.get("status")));
            }
            if (amap.get("level") != null && StringUtils.isNotBlank(amap.get("level").toString())) {
                switch (amap.get("level").toString()) {
                    case "success-element":
                        amap.put("levelString", "绿色");
                        break;
                    case "warning-element":
                        amap.put("levelString", "橙色");
                        break;
                    case "info-element":
                        amap.put("levelString", "蓝色");
                        break;
                    case "danger-element":
                        amap.put("levelString", "红色");
                        break;
                }
            }
            if (amap.get("create_time") != null && StringUtils.isNotBlank(amap.get("create_time").toString())) {
                date = (Date) amap.get("create_time");
                amap.put("create_time", DateFormatUtils.format(date, "yyyy-MM-dd"));
            }
        }

        return page;
    }

    /**
     * 禁用公告
     *
     * @param noticeId
     * @return
     */
    @Override
    public ResponseDTO disableNotice(Long noticeId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysNotice tSysNotice = new TSysNotice();
        tSysNotice.setId(noticeId);
        tSysNotice.setStatus(SystemDictionary.SYS_NOTICE_DISABLE_STATUS);
        tSysNotice.setUpdateTime(new Date());
        sysNoticeMapper.updateByPrimaryKeySelective(tSysNotice);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("禁用公告成功！");

        return responseDTO;
    }

    /**
     * 启用公告
     *
     * @param noticeId
     * @return
     */
    @Override
    public ResponseDTO enableNotice(Long noticeId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysNotice tSysNotice = new TSysNotice();
        tSysNotice.setId(noticeId);
        tSysNotice.setStatus(SystemDictionary.SYS_NOTICE_ENABLE_STATUS);
        tSysNotice.setUpdateTime(new Date());
        sysNoticeMapper.updateByPrimaryKeySelective(tSysNotice);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("启用公告成功！");

        return responseDTO;
    }

    /**
     * 删除公告
     *
     * @param noticeId
     * @return
     */
    @Override
    public ResponseDTO deleteNotice(Long noticeId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysNotice tSysNotice = new TSysNotice();
        tSysNotice.setId(noticeId);
        tSysNotice.setStatus(SystemDictionary.SYS_NOTICE_DELETE_STATUS);
        tSysNotice.setUpdateTime(new Date());
        sysNoticeMapper.updateByPrimaryKeySelective(tSysNotice);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("删除公告成功！");

        return responseDTO;
    }

    /**
     * 获取公告详情
     *
     * @param noticeId
     * @return
     */
    @Override
    public ResponseDTO getNoticeDetail(Long noticeId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysNotice tSysNotice = sysNoticeMapper.selectByPrimaryKey(noticeId);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("sysNotice", tSysNotice);
        responseDTO.setDataMap(dataMap);
        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("获取公告详情成功!");

        return responseDTO;
    }

    /**
     * 编辑公告保存
     *
     * @param paraMap
     * @return
     */
    @Override
    public ResponseDTO editNoticeSave(Map<String, Object> paraMap) {
        ResponseDTO responseDTO = new ResponseDTO();
        Date now = new Date();

        TSysNotice tSysNotice = new TSysNotice();
        tSysNotice.setId(Long.parseLong(paraMap.get("noticeId").toString()));
        tSysNotice.setTitle(paraMap.get("title").toString());
        tSysNotice.setContent(paraMap.get("content").toString());
        tSysNotice.setLevel(paraMap.get("level").toString());
        tSysNotice.setContentColor(paraMap.get("contentColor").toString());
        tSysNotice.setBackgroundColor(paraMap.get("backgroundColor").toString());
        tSysNotice.setUpdateTime(now);

        sysNoticeMapper.updateByPrimaryKeySelective(tSysNotice);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("保存成功!");
        return responseDTO;
    }

    /**
     * 添加新公告保存
     *
     * @param paraMap
     * @return
     */
    @Override
    public ResponseDTO addNoticeSave(Map<String, Object> paraMap) {
        ResponseDTO responseDTO = new ResponseDTO();
        Date now = new Date();

        TSysNotice tSysNotice = new TSysNotice();
        tSysNotice.setCreateUser(Long.parseLong(paraMap.get("userId").toString()));
        tSysNotice.setTitle(paraMap.get("title").toString());
        tSysNotice.setContent(paraMap.get("content").toString());
        tSysNotice.setLevel(paraMap.get("level").toString());
        tSysNotice.setContentColor(paraMap.get("contentColor").toString());
        tSysNotice.setBackgroundColor(paraMap.get("backgroundColor").toString());
        tSysNotice.setStatus(SystemDictionary.SYS_NOTICE_EDITING_STATUS);
        tSysNotice.setCreateTime(now);

        sysNoticeMapper.insert(tSysNotice);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("添加成功!");
        return responseDTO;
    }

}
