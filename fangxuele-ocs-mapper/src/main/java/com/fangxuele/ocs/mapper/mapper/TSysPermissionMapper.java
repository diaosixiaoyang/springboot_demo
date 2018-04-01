package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TSysPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysPermission record);

    int insertSelective(TSysPermission record);

    TSysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysPermission record);

    int updateByPrimaryKey(TSysPermission record);

    List<String> getAllPermissionsByUserId(Long id);

    List<Map<String, Object>> getPermissionList(Map<String, Object> paraMap);

    List<TSysPermission> selectAllEnabled();

    List<Map<String, Object>> getMenu(@Param("userId") Long userId, @Param("parentId") Long parentId);

}