package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.exception.ServiceException;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.SystemLogInfoService;
import com.fangxuele.ocs.mapper.mapper.TLogInfoMapper;
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
 * @author rememberber(https : / / github.com / rememberber)
 */
@Service(version = "1.0.0", interfaceClass = SystemLogInfoService.class, timeout = 10000, cluster = "failfast")
public class SystemLogInfoServiceImpl implements SystemLogInfoService {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogInfoServiceImpl.class);

    @Autowired
    TLogInfoMapper logInfoMapper;

    /**
     * 查询权限列表
     *
     * @param valueMap
     * @param page
     * @return
     */
    @Override
    public CommonPage getLogInfoList(Map<String, Object> valueMap, CommonPage page) {
        Map<String, Object> paraMap = new HashMap<>();
        try {
            if (valueMap.containsKey("createUser") && StringUtils.isNotBlank(valueMap.get("createUser").toString())) {
                paraMap.put("createUser", Integer.parseInt(valueMap.get("createUser").toString()));
            }
            if (valueMap.containsKey("module") && StringUtils.isNotBlank(valueMap.get("module").toString())) {
                paraMap.put("module", Integer.parseInt(valueMap.get("module").toString()));
            }
            if (valueMap.containsKey("message") && StringUtils.isNotBlank(valueMap.get("message").toString())) {
                paraMap.put("message", "%" + valueMap.get("message") + "%");
            }
            if (valueMap.containsKey("dateFrom") && StringUtils.isNotBlank(valueMap.get("dateFrom").toString())) {
                paraMap.put("dateFrom", DateUtils.parseDate(valueMap.get("dateFrom").toString(), "yyyy-MM-dd"));
            }
            if (valueMap.containsKey("dateTo") && StringUtils.isNotBlank(valueMap.get("dateTo").toString())) {
                // 选择的截止日期加一天，保证了查询条件的完整性，解决了时分秒的问题。
                paraMap.put("dateTo", DateUtils.ceiling(
                        DateUtils.parseDate(valueMap.get("dateTo").toString(), "yyyy-MM-dd"), Calendar.DAY_OF_MONTH));
            }
            paraMap.put("orderBy", "create_time desc");
        } catch (Exception e) {
            logger.error(ExceptionUtil.stackTraceText(e));
            throw new ServiceException("生成查询条件时出错！");
        }

        PageHelper.offsetPage(page.getOffset(), page.getPageSize());
        List<Map<String, Object>> list = logInfoMapper.getLogInfoList(paraMap);
        for (Map<String, Object> map : list) {
            map.put("module", DictionaryUtil.getString(DictionaryUtil.OPERATE_LOG_MODULE, (Integer) map.get("module")));
        }
        page.setResult(list);
        page.setTotalCount(((Page) list).getTotal());

        Date date;
        for (Map<String, Object> amap : list) {
            if (amap.get("create_time") != null && StringUtils.isNotBlank(amap.get("create_time").toString())) {
                date = (Date) amap.get("create_time");
                amap.put("create_time", DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
            }
        }

        return page;
    }

    /**
     * 搜索参数（下拉列表参数）
     *
     * @return
     */
    @Override
    public Map<String, Object> getSearchParaMap() {
        Map<String, Object> searchParaMap = new HashMap<>();

        Map<String, String> creatorMap = new LinkedHashMap<>();
        List<Map<String, Object>> resultList = logInfoMapper.selectAllCreator();
        for (Map resultMap : resultList) {
            creatorMap.put(resultMap.get("user_id").toString(), resultMap.get("username").toString());
        }
        searchParaMap.put("creators", creatorMap);

        Map<Integer, String> logModule = DictionaryUtil.getStatueMap(DictionaryUtil.OPERATE_LOG_MODULE);
        searchParaMap.put("logModules", logModule);

        return searchParaMap;
    }

}
