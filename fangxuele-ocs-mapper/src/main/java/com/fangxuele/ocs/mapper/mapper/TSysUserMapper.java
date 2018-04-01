package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSysUser;

public interface TSysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysUser record);

    int insertSelective(TSysUser record);

    TSysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysUser record);

    int updateByPrimaryKey(TSysUser record);
}