package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.exception.ServiceException;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.SystemDictionaryService;
import com.fangxuele.ocs.mapper.domain.TDictionaryClass;
import com.fangxuele.ocs.mapper.mapper.TDictionaryClassMapper;
import com.fangxuele.ocs.mapper.mapper.TDictionaryMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.base.ExceptionUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
@Service(version = "1.0.0", interfaceClass = SystemDictionaryService.class, timeout = 10000, cluster = "failfast")
public class SystemDictionaryServiceImpl implements SystemDictionaryService {

    private static final Logger logger = LoggerFactory.getLogger(SystemDictionaryServiceImpl.class);

    @Autowired
    private TDictionaryMapper dictionaryMapper;

    @Autowired
    private TDictionaryClassMapper dictionaryClassMapper;

    /**
     * 查询字典列表
     *
     * @param valueMap
     * @param page
     * @return
     */
    @Override
    public CommonPage getDictionaryList(Map<String, Object> valueMap, CommonPage page) {
        Map<String, Object> paraMap = new HashMap<>();
        try {
            if (valueMap.containsKey("dictionaryName") && StringUtils.isNotBlank(valueMap.get("dictionaryName").toString())) {
                paraMap.put("dictionaryName", "%" + valueMap.get("dictionaryName").toString() + "%");
            }
            if (valueMap.containsKey("dictionaryValue") && StringUtils.isNotBlank(valueMap.get("dictionaryValue").toString())) {
                paraMap.put("dictionaryValue", valueMap.get("dictionaryValue").toString());
            }
            if (valueMap.containsKey("dictionaryClass") && StringUtils.isNotBlank(valueMap.get("dictionaryClass").toString())) {
                paraMap.put("dictionaryClass", valueMap.get("dictionaryClass").toString());
            }
            if (valueMap.containsKey("status") && StringUtils.isNotBlank(valueMap.get("status").toString())) {
                paraMap.put("status", Integer.parseInt(valueMap.get("status").toString()));
            }
            paraMap.put("orderBy", "td.class_code");
        } catch (Exception e) {
            logger.error(ExceptionUtil.stackTraceText(e));
            throw new ServiceException("生成查询条件时出错！");
        }

        PageHelper.offsetPage(page.getOffset(), page.getPageSize());
        List<Map<String, Object>> list = dictionaryMapper.getDictionaryList(paraMap);
        page.setResult(list);
        page.setTotalCount(((Page) list).getTotal());

        for (Map<String, Object> amap : list) {
            if (amap.get("status") != null && StringUtils.isNotBlank(amap.get("status").toString())) {
                amap.put("statusString", DictionaryUtil.getString(DictionaryUtil.ChannelStatus,
                        (Integer) amap.get("status")));
            }
            if (amap.get("code") == null) {
                amap.put("code", "");
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
    public Map<String, Map<String, String>> getSearchParaMap() {
        Map<String, Map<String, String>> searchParaMap = new HashMap<>();

        Map<String, String> classMap = new LinkedHashMap<>();
        List<TDictionaryClass> dictionaryClassList = dictionaryClassMapper.selectAll();
        for (TDictionaryClass tDictionaryClass : dictionaryClassList) {
            classMap.put(tDictionaryClass.getCode().toString(), tDictionaryClass.getName());
        }
        searchParaMap.put("dictionaryClassList", classMap);
        return searchParaMap;
    }
}
