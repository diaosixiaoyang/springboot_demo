package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSearchLog;

public interface TSearchLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSearchLog record);

    int insertSelective(TSearchLog record);

    TSearchLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSearchLog record);

    int updateByPrimaryKey(TSearchLog record);
}