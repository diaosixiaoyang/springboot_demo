package com.fangxuele.ocs.inter.coms.service;


import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.mapper.domain.TSysUser;

import java.util.List;
import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public interface SystemMemberService {
    /**
     * 查询成员列表
     *
     * @param map
     * @param page
     * @return
     */
    CommonPage getMemberList(Map<String, Object> map, CommonPage page);

    /**
     * 搜索参数（下拉列表参数）
     *
     * @return
     */
    Map<String, Map<String, String>> getSearchParaMap();

    /**
     * 禁用成员
     *
     * @param userId
     * @return
     */
    ResponseDTO disableUser(Long userId);

    /**
     * 启用成员
     *
     * @param userId
     * @return
     */
    ResponseDTO enableUser(Long userId);

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    ResponseDTO getUserRoleDetail(Long userId);

    /**
     * 分配用户角色保存
     *
     * @param map
     * @return
     */
    ResponseDTO editUserRoleSave(Map<String, Object> map);

    /**
     * 获取成员详情
     *
     * @param userId
     * @return
     */
    ResponseDTO getUserDetail(Long userId);

    /**
     * 编辑成员保存
     *
     * @param map
     * @return
     */
    ResponseDTO editUserSave(Map<String, Object> map);

    /**
     * @return
     */
    List<TSysUser> getAllEnabled();
}
