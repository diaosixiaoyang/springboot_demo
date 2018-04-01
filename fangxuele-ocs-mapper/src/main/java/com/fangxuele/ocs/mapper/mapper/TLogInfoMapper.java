package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TLogInfo;

public interface TLogInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TLogInfo record);

    int insertSelective(TLogInfo record);

    TLogInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TLogInfo record);

    int updateByPrimaryKey(TLogInfo record);
}