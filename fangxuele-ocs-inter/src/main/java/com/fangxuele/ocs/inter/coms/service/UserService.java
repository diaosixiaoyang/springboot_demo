package com.fangxuele.ocs.inter.coms.service;

import com.fangxuele.ocs.mapper.domain.TSysUser;

import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public interface UserService {

    public TSysUser getSysUserInfoByUserName(String userName);

    public Map<String, Object> register(Map<String, Object> map);
}
