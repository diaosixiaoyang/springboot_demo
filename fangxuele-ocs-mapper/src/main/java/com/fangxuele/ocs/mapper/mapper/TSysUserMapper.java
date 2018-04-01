package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSysUser;

import java.util.List;
import java.util.Map;

public interface TSysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysUser record);

    int insertSelective(TSysUser record);

    TSysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysUser record);

    int updateByPrimaryKey(TSysUser record);

    TSysUser selectByUserName(String userName);

    List<Map<String, Object>> getMemberList(Map<String, Object> paraMap);

    List<TSysUser> getAllEnabled();

    TSysUser selectRobotUser();
}