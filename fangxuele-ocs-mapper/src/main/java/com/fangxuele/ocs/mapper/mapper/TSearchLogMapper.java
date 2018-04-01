package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSearchLog;

import java.util.List;
import java.util.Map;

public interface TSearchLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSearchLog record);

    int insertSelective(TSearchLog record);

    TSearchLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSearchLog record);

    int updateByPrimaryKey(TSearchLog record);

    List<Map<String,Object>> getLogList(Map<String, Object> paraMap);

    List<Map<String,Object>> getHotSearchList(Map<String, Object> paraMap);
}