package com.fangxuele.ocs.inter.coms.service;

import com.fangxuele.ocs.common.dto.response.ResponseDTO;

import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public interface SystemUserService {
    void saveAvatar(Long id, String avatarUrl) throws Exception;

    ResponseDTO modifyPassword(Map<String, Object> map) throws Exception;

    String changeTheme(Long id);
}
