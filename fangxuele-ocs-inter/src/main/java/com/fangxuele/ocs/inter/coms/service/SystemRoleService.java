package com.fangxuele.ocs.inter.coms.service;

import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;

import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public interface SystemRoleService {
    /**
     * 查询角色列表
     *
     * @param map
     * @param page
     * @return
     */
    CommonPage getRoleList(Map<String, Object> map, CommonPage page);

    /**
     * 搜索参数（下拉列表参数）
     *
     * @return
     */
    Map<String, Map<String, String>> getSearchParaMap();

    /**
     * 禁用角色
     *
     * @param roleId
     * @return
     */
    ResponseDTO disableRole(Long roleId);

    /**
     * 启用角色
     *
     * @param roleId
     * @return
     */
    ResponseDTO enableRole(Long roleId);

    /**
     * 获取角色详情
     *
     * @param roleId
     * @return
     */
    ResponseDTO getRoleDetail(Long roleId);

    /**
     * 编辑角色保存
     *
     * @param map
     * @return
     */
    ResponseDTO editRoleSave(Map<String, Object> map);

    /**
     * 获取角色权限
     *
     * @param roleId
     * @return
     */
    ResponseDTO getRolePermissionDetail(Long roleId);

    /**
     * 分配角色权限保存
     *
     * @param map
     * @return
     */
    ResponseDTO editRolePermissionSave(Map<String, Object> map);

    /**
     * 创建新角色保存
     *
     * @param map
     * @return
     */
    ResponseDTO addRoleSave(Map<String, Object> map);
}
