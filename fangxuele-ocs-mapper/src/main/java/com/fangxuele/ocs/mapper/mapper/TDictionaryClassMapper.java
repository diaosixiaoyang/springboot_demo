package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TDictionaryClass;

public interface TDictionaryClassMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TDictionaryClass record);

    int insertSelective(TDictionaryClass record);

    TDictionaryClass selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDictionaryClass record);

    int updateByPrimaryKey(TDictionaryClass record);
}