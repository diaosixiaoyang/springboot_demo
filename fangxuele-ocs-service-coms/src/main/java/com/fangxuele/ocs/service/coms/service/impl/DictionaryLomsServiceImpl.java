package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.DictionaryLomsService;
import com.fangxuele.ocs.mapper.domain.TDictionary;
import com.fangxuele.ocs.mapper.mapper.TDictionaryMapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author rememberber(https://github.com/rememberber)
 */
@Service(version = "1.0.0", interfaceClass = DictionaryLomsService.class, timeout = 10000, cluster = "failfast")
public class DictionaryLomsServiceImpl implements DictionaryLomsService {

    @Autowired
    private TDictionaryMapper dictionaryMapper;

    /**
     * 初始化字典表
     */
    @Override
    public void initDictionary() {
        Map<String, Map<String, Map<Integer, String>>> dictionaryCalssMap = Maps.newHashMap();
        List<TDictionary> list = dictionaryMapper.findAllEnabled();
        Map<String, Map<Integer, String>> dictionaryMap = null;
        Map<Integer, String> dictionaryCnMap = null;
        Map<Integer, String> dictionaryEnMap = null;
        String tempCode = "";

        Map<String, Map<String, String>> nameCodeMap = Maps.newHashMap();
        Map<String, String> nameCodeSubMap = null;

        for (TDictionary tDictionary : list) {
            if (!tDictionary.getClassCode().equals(tempCode)) {
                dictionaryCnMap = Maps.newTreeMap();
                dictionaryEnMap = Maps.newTreeMap();
                dictionaryMap = Maps.newHashMap();
                nameCodeSubMap = Maps.newTreeMap();

                dictionaryMap.put("zh_CN", dictionaryCnMap);
                dictionaryMap.put("en_US", dictionaryEnMap);
                dictionaryCalssMap.put(tDictionary.getClassCode(), dictionaryMap);

                nameCodeMap.put(tDictionary.getClassCode(), nameCodeSubMap);
            }
            dictionaryCnMap.put(tDictionary.getValue(), tDictionary.getName());
            dictionaryEnMap.put(tDictionary.getValue(), tDictionary.getCode());
            if (StringUtils.isNotBlank(tDictionary.getCode())) {
                nameCodeSubMap.put(tDictionary.getCode(), tDictionary.getName());
            }
            tempCode = tDictionary.getClassCode();
        }
        if (!CollectionUtils.isEmpty(list)) {
            DictionaryUtil.statusClassMap = dictionaryCalssMap;
            DictionaryUtil.nameCodeMap = nameCodeMap;
        }

    }

    @Override
    public Map<Integer, String> getStatueMap(String classCode) {
        return DictionaryUtil.getStatueMap(classCode);
    }
}
