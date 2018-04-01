package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.util.ConfigurationUtil;
import com.fangxuele.ocs.common.util.EntryptUtil;
import com.fangxuele.ocs.inter.coms.service.UserService;
import com.fangxuele.ocs.mapper.domain.TSysRole;
import com.fangxuele.ocs.mapper.domain.TSysUser;
import com.fangxuele.ocs.mapper.domain.TSysUserRole;
import com.fangxuele.ocs.mapper.mapper.TSysRoleMapper;
import com.fangxuele.ocs.mapper.mapper.TSysUserMapper;
import com.fangxuele.ocs.mapper.mapper.TSysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.text.TextValidator;

import java.util.Date;
import java.util.Map;

/**
 * 系统用户服务
 *
 * @author rememberber(https : / / github.com / rememberber)
 */
@Service(version = "1.0.0", interfaceClass = UserService.class, timeout = 10000, cluster = "failfast")
public class UserServiceImpl implements UserService {

    @Autowired
    TSysUserMapper sysUserMapper;

    @Autowired
    TSysUserRoleMapper sysUserRoleMapper;

    @Autowired
    TSysRoleMapper sysRoleMapper;

    @Override
    public TSysUser getSysUserInfoByUserName(String userName) {

        return sysUserMapper.selectByUserName(userName);
    }

    @Override
    public Map<String, Object> register(Map<String, Object> map) {

        String userName = map.get("userName").toString();
        String nickName = map.get("nickName").toString();
        String email = map.get("email").toString();
        String password = map.get("password").toString();
        String passwordRepeat = map.get("passwordRepeat").toString();
        String inviteCode = map.get("inviteCode").toString();

        if (!password.equals(passwordRepeat)) {
            map.put("msg", "两次密码输入的不一致!");
            return map;
        }
        if (!ConfigurationUtil.propertiesMap.get(ConfigurationUtil.LOMS_INVITE_CODE).equals(inviteCode)) {
            map.put("msg", "邀请码不正确!");
            return map;
        }
        if (sysUserMapper.selectByUserName(userName) != null) {
            map.put("msg", "该用户名已经注册过了!");
            return map;
        }

        if (!TextValidator.isEmail(email)) {
            map.put("msg", "邮箱格式不正确!");
            return map;
        }

        if (password.length() < 6) {
            map.put("msg", "请输入至少6位密码!");
            return map;
        }

        TSysUser tSysUser = new TSysUser();
        tSysUser.setUserName(userName);
        tSysUser.setEmail(email);
        tSysUser.setHeadImage("http://logo.fangxuele.com/image/fxl/logoer.jpg");
        tSysUser.setCreateTime(new Date());
        tSysUser.setNickName(nickName);
        EntryptUtil.EncodePassWord encodePassWord = EntryptUtil.entryptPassword(password);
        tSysUser.setSalt(encodePassWord.getSalt());
        tSysUser.setPassword(encodePassWord.getEncodedPassword());
        tSysUser.setStatus((byte) 1);

        sysUserMapper.insert(tSysUser);

        // 设置默认角色
        TSysUserRole tSysUserRole = new TSysUserRole();
        tSysUserRole.setUserId(tSysUser.getId());
        TSysRole tSysRole = sysRoleMapper.selectByRoleName("访客");
        tSysUserRole.setRoleId(tSysRole.getId());
        tSysUserRole.setCreateTime(new Date());
        sysUserRoleMapper.insert(tSysUserRole);

        return map;
    }
}
