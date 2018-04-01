package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSms;

public interface TSmsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSms record);

    int insertSelective(TSms record);

    TSms selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSms record);

    int updateByPrimaryKey(TSms record);
}