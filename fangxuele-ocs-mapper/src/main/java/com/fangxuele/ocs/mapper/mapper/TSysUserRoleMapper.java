package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSysUserRole;
import com.fangxuele.ocs.mapper.domain.TSysUserRoleKey;

public interface TSysUserRoleMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(TSysUserRole record);

    int insertSelective(TSysUserRole record);

    TSysUserRole selectByPrimaryKey(TSysUserRoleKey key);

    int updateByPrimaryKeySelective(TSysUserRole record);

    int updateByPrimaryKey(TSysUserRole record);
}