package com.fangxuele.ocs.web.coms.service;

import com.fangxuele.ocs.common.bean.loms.SysMenu;
import com.fangxuele.ocs.mapper.domain.TSysRole;
import com.fangxuele.ocs.mapper.domain.TSysUser;
import com.fangxuele.ocs.mapper.mapper.TSysPermissionMapper;
import com.fangxuele.ocs.mapper.mapper.TSysRoleMapper;
import com.fangxuele.ocs.mapper.mapper.TSysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author rememberber(https://github.com/rememberber)
 */
@Service
public class SysUserService {

    @Autowired
    TSysUserMapper sysUserMapper;

    @Autowired
    TSysRoleMapper sysRoleMapper;

    @Autowired
    TSysPermissionMapper sysPermissionMapper;

    public TSysUser getSysUserInfoByUserName(String userName) {

        return sysUserMapper.selectByUserName(userName);
    }

    public List<TSysRole> getSysRoleByUserId(Long userId) {

        return sysRoleMapper.selectByUserId(userId);
    }

    public List<String> getAllPermissionsByUserId(Long id) {

        return sysPermissionMapper.getAllPermissionsByUserId(id);
    }

    public List<SysMenu> getMainMenu(Long userId) {
        List<SysMenu> mainMenuList = new ArrayList<>();
        List<Map<String, Object>> resultList = sysPermissionMapper.getMenu(userId, 0L);
        SysMenu sysMenu;
        SysMenu sysMenu2;
        List<SysMenu> childMenuList;
        for (Map<String, Object> stringObjectMap : resultList) {
            sysMenu = new SysMenu();
            sysMenu.setTitle(stringObjectMap.get("name").toString());
            sysMenu.setCssId(stringObjectMap.get("css_id").toString());
            sysMenu.setIconClass(stringObjectMap.get("icon_class").toString());
            sysMenu.setUrl(stringObjectMap.get("url").toString());

            List<Map<String, Object>> resultList2 = sysPermissionMapper.getMenu(userId, Long.parseLong(stringObjectMap.get("id").toString()));
            childMenuList = new ArrayList<>();
            for (Map<String, Object> objectMap : resultList2) {
                sysMenu2 = new SysMenu();
                sysMenu2.setTitle(objectMap.get("name").toString());
                sysMenu2.setCssId(objectMap.get("css_id").toString());
                sysMenu2.setUrl(objectMap.get("url").toString());
                childMenuList.add(sysMenu2);
            }
            sysMenu.setChildMenu(childMenuList);

            mainMenuList.add(sysMenu);
        }
        return mainMenuList;
    }
}
