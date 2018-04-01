package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TDictionary;

public interface TDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TDictionary record);

    int insertSelective(TDictionary record);

    TDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDictionary record);

    int updateByPrimaryKey(TDictionary record);
}