package com.fangxuele.ocs.inter.coms.service;


import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;

import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public interface SystemPermissionService {
    /**
     * 查询权限列表
     *
     * @param map
     * @param page
     * @return
     */
    CommonPage getPermissionList(Map<String, Object> map, CommonPage page);

    /**
     * 搜索参数（下拉列表参数）
     *
     * @return
     */
    Map<String, Map<String, String>> getSearchParaMap();

    /**
     * 禁用权限
     *
     * @param permissionId
     * @return
     */
    ResponseDTO disablePermission(Long permissionId);

    /**
     * 启用权限
     *
     * @param permissionId
     * @return
     */
    ResponseDTO enablePermission(Long permissionId);
}
