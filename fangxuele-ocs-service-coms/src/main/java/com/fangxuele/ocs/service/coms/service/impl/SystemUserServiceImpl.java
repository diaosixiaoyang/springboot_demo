package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.constant.SecurityConstant;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.EntryptUtil;
import com.fangxuele.ocs.inter.coms.service.SystemUserService;
import com.fangxuele.ocs.mapper.domain.TSysUser;
import com.fangxuele.ocs.mapper.mapper.TSysUserMapper;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.text.EncodeUtil;

import java.util.Date;
import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
@Service(version = "1.0.0", interfaceClass = SystemUserService.class, timeout = 10000, cluster = "failfast")
@Transactional
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    TSysUserMapper sysUserMapper;

    @Override
    public void saveAvatar(Long id, String avatarUrl) throws Exception {
        TSysUser tSysUser = new TSysUser();
        tSysUser.setId(id);
        tSysUser.setHeadImage(avatarUrl);

        sysUserMapper.updateByPrimaryKeySelective(tSysUser);
    }

    @Override
    public ResponseDTO modifyPassword(Map<String, Object> map) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();

        String id = map.get("id").toString();
        String beforePassword = map.get("beforePassword").toString();
        String password = map.get("password").toString();
        String passwordConfirm = map.get("passwordConfirm").toString();

        TSysUser tSysUser = sysUserMapper.selectByPrimaryKey(Long.parseLong(id));

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SecurityConstant.ALGORITHM);
        matcher.setHashIterations(SecurityConstant.INTERATIONS);
        UsernamePasswordToken token = new UsernamePasswordToken(tSysUser.getUserName(), beforePassword);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(tSysUser, tSysUser.getPassword(),
                ByteSource.Util.bytes(EncodeUtil.decodeHex(tSysUser.getSalt())), tSysUser.getUserName());
        if (!matcher.doCredentialsMatch(token, info)) {
            responseDTO.setSuccess(false);
            responseDTO.setMsg("原密码不正确！");
            return responseDTO;
        }

        if (password.length() < 6) {
            responseDTO.setSuccess(false);
            responseDTO.setMsg("请输入至少6位密码！");
            return responseDTO;
        }

        if (!password.equals(passwordConfirm)) {
            responseDTO.setSuccess(false);
            responseDTO.setMsg("两次新密码输入的不一致！");
            return responseDTO;
        }

        tSysUser.setId(Long.parseLong(id));
        EntryptUtil.EncodePassWord encodePassWord = EntryptUtil.entryptPassword(password);
        tSysUser.setSalt(encodePassWord.getSalt());
        tSysUser.setPassword(encodePassWord.getEncodedPassword());
        tSysUser.setUpdateTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(tSysUser);

        responseDTO.setSuccess(true);
        responseDTO.setMsg("修改密码成功！");

        return responseDTO;
    }

    @Override
    public String changeTheme(Long sysUserId) {
        TSysUser tSysUser = sysUserMapper.selectByPrimaryKey(sysUserId);
        tSysUser.setTheme(tSysUser.getTheme() == null ? "md-skin" : null);
        sysUserMapper.updateByPrimaryKey(tSysUser);

        return tSysUser.getTheme();
    }
}
