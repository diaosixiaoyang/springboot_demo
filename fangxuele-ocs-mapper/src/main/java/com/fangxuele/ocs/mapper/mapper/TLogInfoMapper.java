package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TLogInfo;

import java.util.List;
import java.util.Map;

public interface TLogInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TLogInfo record);

    int insertSelective(TLogInfo record);

    TLogInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TLogInfo record);

    int updateByPrimaryKey(TLogInfo record);

    List<Map<String, Object>> getLogInfoList(Map<String, Object> paraMap);

    List<Map<String, Object>> selectAllCreator();
}