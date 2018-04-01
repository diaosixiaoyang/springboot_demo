package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSysRolePermission;
import com.fangxuele.ocs.mapper.domain.TSysRolePermissionKey;

public interface TSysRolePermissionMapper {
    int deleteByPrimaryKey(TSysRolePermissionKey key);

    int insert(TSysRolePermission record);

    int insertSelective(TSysRolePermission record);

    TSysRolePermission selectByPrimaryKey(TSysRolePermissionKey key);

    int updateByPrimaryKeySelective(TSysRolePermission record);

    int updateByPrimaryKey(TSysRolePermission record);
}