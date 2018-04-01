package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSysRole;

import java.util.List;
import java.util.Map;

public interface TSysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysRole record);

    int insertSelective(TSysRole record);

    TSysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysRole record);

    int updateByPrimaryKey(TSysRole record);

    List<TSysRole> selectByUserId(Long userId);

    TSysRole selectByRoleName(String roleName);

    List<TSysRole> selectAllEnabled();

    List<Map<String, Object>> getRoleList(Map<String, Object> paraMap);
}