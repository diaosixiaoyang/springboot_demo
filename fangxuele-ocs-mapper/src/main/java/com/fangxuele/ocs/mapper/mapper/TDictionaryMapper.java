package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TDictionary;

import java.util.List;
import java.util.Map;

public interface TDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TDictionary record);

    int insertSelective(TDictionary record);

    TDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDictionary record);

    int updateByPrimaryKey(TDictionary record);

    List<TDictionary> findAllEnabled();

    List<Map<String,String>> findByClassCode(String classCode);

    List<Map<String,Object>> getDictionaryList(Map<String, Object> paraMap);
}