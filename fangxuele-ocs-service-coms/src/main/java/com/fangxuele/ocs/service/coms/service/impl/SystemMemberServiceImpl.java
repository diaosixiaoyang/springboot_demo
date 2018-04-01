package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.dictionary.SystemDictionary;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.exception.ServiceException;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.SystemMemberService;
import com.fangxuele.ocs.mapper.domain.TSysRole;
import com.fangxuele.ocs.mapper.domain.TSysUser;
import com.fangxuele.ocs.mapper.domain.TSysUserRole;
import com.fangxuele.ocs.mapper.mapper.TSysRoleMapper;
import com.fangxuele.ocs.mapper.mapper.TSysUserMapper;
import com.fangxuele.ocs.mapper.mapper.TSysUserRoleMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.base.ExceptionUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
@Service(version = "1.0.0", interfaceClass = SystemMemberService.class, timeout = 10000, cluster = "failfast")
public class SystemMemberServiceImpl implements SystemMemberService {

    private static final Logger logger = LoggerFactory.getLogger(SystemMemberServiceImpl.class);

    @Autowired
    TSysUserMapper sysUserMapper;

    @Autowired
    TSysRoleMapper sysRoleMapper;

    @Autowired
    TSysUserRoleMapper sysUserRoleMapper;


    /**
     * 查询成员列表
     *
     * @param valueMap
     * @param page
     * @return
     */
    @Override
    public CommonPage getMemberList(Map<String, Object> valueMap, CommonPage page) {
        Map<String, Object> paraMap = new HashMap<>();
        try {
            if (valueMap.containsKey("userName") && StringUtils.isNotBlank(valueMap.get("userName").toString())) {
                paraMap.put("userName", "%" + valueMap.get("userName").toString() + "%");
            }
            if (valueMap.containsKey("nickName") && StringUtils.isNotBlank(valueMap.get("nickName").toString())) {
                paraMap.put("nickName", "%" + valueMap.get("nickName").toString() + "%");
            }
            if (valueMap.containsKey("realName") && StringUtils.isNotBlank(valueMap.get("realName").toString())) {
                paraMap.put("realName", "%" + valueMap.get("realName").toString() + "%");
            }
            if (valueMap.containsKey("mobile") && StringUtils.isNotBlank(valueMap.get("mobile").toString())) {
                paraMap.put("mobile", valueMap.get("mobile").toString());
            }
            if (valueMap.containsKey("email") && StringUtils.isNotBlank(valueMap.get("email").toString())) {
                paraMap.put("email", valueMap.get("email").toString());
            }
            if (valueMap.containsKey("role") && StringUtils.isNotBlank(valueMap.get("role").toString())) {
                paraMap.put("role", valueMap.get("role").toString());
            }
            if (valueMap.containsKey("dateFrom") && StringUtils.isNotBlank(valueMap.get("dateFrom").toString())) {
                paraMap.put("dateFrom", DateUtils.parseDate(valueMap.get("dateFrom").toString(), "yyyy-MM-dd"));
            }
            if (valueMap.containsKey("dateTo") && StringUtils.isNotBlank(valueMap.get("dateTo").toString())) {
                // 选择的截止日期加一天，保证了查询条件的完整性，解决了时分秒的问题。
                paraMap.put("dateTo", DateUtils.ceiling(
                        DateUtils.parseDate(valueMap.get("dateTo").toString(), "yyyy-MM-dd"), Calendar.DAY_OF_MONTH));
            }
            if (valueMap.containsKey("status") && StringUtils.isNotBlank(valueMap.get("status").toString())) {
                paraMap.put("status", Integer.parseInt(valueMap.get("status").toString()));
            }
            paraMap.put("orderBy", "tsu.status desc,tsu.create_time desc");
        } catch (Exception e) {
            logger.error(ExceptionUtil.stackTraceText(e));
            throw new ServiceException("生成查询条件时出错！");
        }

        PageHelper.offsetPage(page.getOffset(), page.getPageSize());
        List<Map<String, Object>> list = sysUserMapper.getMemberList(paraMap);
        page.setResult(list);
        page.setTotalCount(((Page) list).getTotal());

        Date date;
        for (Map<String, Object> amap : list) {

            // 用户角色
            List<TSysRole> tSysRoleList = sysRoleMapper.selectByUserId(Long.parseLong(amap.get("id").toString()));
            StringBuilder roleNameBuilder = new StringBuilder();
            int sysRoleListSize = tSysRoleList.size();
            for (int i = 0; i < sysRoleListSize; i++) {
                roleNameBuilder.append(tSysRoleList.get(i).getRole());
                if (i < sysRoleListSize - 1) {
                    roleNameBuilder.append("/");
                }
            }
            amap.put("roleName", roleNameBuilder.toString());

            if (amap.get("status") != null && StringUtils.isNotBlank(amap.get("status").toString())) {
                amap.put("statusString", DictionaryUtil.getString(DictionaryUtil.SYS_USER_STATUS,
                        (Integer) amap.get("status")));
            }
            if (amap.get("real_name") == null || StringUtils.isEmpty(amap.get("real_name").toString())) {
                amap.put("real_name", "");
            }
            if (amap.get("mobile") == null || StringUtils.isEmpty(amap.get("mobile").toString())) {
                amap.put("mobile", "");
            }
            if (amap.get("email") == null || StringUtils.isEmpty(amap.get("email").toString())) {
                amap.put("email", "");
            }
            if (amap.get("create_time") != null && StringUtils.isNotBlank(amap.get("create_time").toString())) {
                date = (Date) amap.get("create_time");
                amap.put("create_time", DateFormatUtils.format(date, "yyyy-MM-dd"));
            }
        }

        return page;
    }

    /**
     * 搜索参数（下拉列表参数）
     *
     * @return
     */
    @Override
    public Map<String, Map<String, String>> getSearchParaMap() {
        Map<String, Map<String, String>> searchParaMap = new HashMap<>();

        Map<String, String> classMap = new LinkedHashMap<>();
        List<TSysRole> sysRoleList = sysRoleMapper.selectAllEnabled();
        for (TSysRole tSysRole : sysRoleList) {
            classMap.put(tSysRole.getId().toString(), tSysRole.getRole());
        }
        searchParaMap.put("roleList", classMap);
        return searchParaMap;
    }

    /**
     * 禁用成员
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseDTO disableUser(Long userId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysUser tSysUser = new TSysUser();
        tSysUser.setId(userId);
        tSysUser.setStatus(SystemDictionary.SYS_USER_DISABLE_STATUS);
        tSysUser.setUpdateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(tSysUser);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("禁用成员成功！");

        return responseDTO;
    }

    /**
     * 启用成员
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseDTO enableUser(Long userId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysUser tSysUser = new TSysUser();
        tSysUser.setId(userId);
        tSysUser.setStatus(SystemDictionary.SYS_USER_ENABLE_STATUS);
        tSysUser.setUpdateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(tSysUser);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("启用成员成功！");

        return responseDTO;
    }

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseDTO getUserRoleDetail(Long userId) {
        ResponseDTO responseDTO = new ResponseDTO();

        // 所有角色
        List<TSysRole> roleList = sysRoleMapper.selectAllEnabled();
        // 用户角色
        List<TSysRole> userRoleList = sysRoleMapper.selectByUserId(userId);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("roleList", roleList);
        dataMap.put("userRoleList", userRoleList);
        responseDTO.setDataMap(dataMap);
        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("获取用户角色成功！");

        return responseDTO;
    }

    /**
     * 分配用户角色保存
     *
     * @param map
     * @return
     */
    @Override
    public ResponseDTO editUserRoleSave(Map<String, Object> map) {
        ResponseDTO responseDTO = new ResponseDTO();
        String[] roleArray;
        if (map.get("roleSelect") instanceof String) {
            roleArray = new String[1];
            roleArray[0] = map.get("roleSelect").toString();
        } else {
            roleArray = (String[]) map.get("roleSelect");
        }
        Long userId = Long.parseLong(map.get("userId").toString());
        // 删除用户原来的角色
        sysUserRoleMapper.deleteByPrimaryKey(userId);

        // 为用户分配新角色
        TSysUserRole tSysUserRole;
        for (String roleId : roleArray) {
            tSysUserRole = new TSysUserRole();
            tSysUserRole.setUserId(userId);
            tSysUserRole.setRoleId(Long.parseLong(roleId));
            tSysUserRole.setCreateTime(new Date());
            sysUserRoleMapper.insertSelective(tSysUserRole);
        }

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("保存成功！");

        return responseDTO;
    }

    /**
     * 获取成员详情
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseDTO getUserDetail(Long userId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysUser tSysUser = sysUserMapper.selectByPrimaryKey(userId);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("sysUser", tSysUser);
        responseDTO.setDataMap(dataMap);
        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("获取成员详情成功!");

        return responseDTO;
    }

    /**
     * 编辑成员保存
     *
     * @param paraMap
     * @return
     */
    @Override
    public ResponseDTO editUserSave(Map<String, Object> paraMap) {
        ResponseDTO responseDTO = new ResponseDTO();
        Date now = new Date();

        TSysUser tSysUser = new TSysUser();
        tSysUser.setId(Long.parseLong(paraMap.get("userId").toString()));
        tSysUser.setUserName(paraMap.get("userName").toString());
        tSysUser.setNickName(paraMap.get("nickName").toString());
        tSysUser.setRealName(paraMap.get("realName").toString());
        tSysUser.setMobile(paraMap.get("mobile").toString());
        tSysUser.setEmail(paraMap.get("email").toString());
        tSysUser.setHeadImage(paraMap.get("image").toString());
        tSysUser.setUpdateTime(now);

        sysUserMapper.updateByPrimaryKeySelective(tSysUser);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("保存成功!");
        return responseDTO;
    }

    /**
     * @return
     */
    @Override
    public List<TSysUser> getAllEnabled() {
        return sysUserMapper.getAllEnabled();
    }

}
