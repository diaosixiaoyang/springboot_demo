package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.dictionary.SystemDictionary;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.exception.ServiceException;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.SystemRoleService;
import com.fangxuele.ocs.mapper.domain.TSysPermission;
import com.fangxuele.ocs.mapper.domain.TSysRole;
import com.fangxuele.ocs.mapper.domain.TSysRolePermission;
import com.fangxuele.ocs.mapper.mapper.TSysPermissionMapper;
import com.fangxuele.ocs.mapper.mapper.TSysRoleMapper;
import com.fangxuele.ocs.mapper.mapper.TSysRolePermissionMapper;
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
@Service(version = "1.0.0", interfaceClass = SystemRoleService.class, timeout = 10000, cluster = "failfast")
public class SystemRoleServiceImpl implements SystemRoleService {

    private static final Logger logger = LoggerFactory.getLogger(SystemRoleServiceImpl.class);

    @Autowired
    TSysRoleMapper sysRoleMapper;

    @Autowired
    TSysPermissionMapper sysPermissionMapper;

    @Autowired
    TSysRolePermissionMapper sysRolePermissionMapper;

    private static final byte STATUS_ENABLED = 1;

    /**
     * 查询角色列表
     *
     * @param valueMap
     * @param page
     * @return
     */
    @Override
    public CommonPage getRoleList(Map<String, Object> valueMap, CommonPage page) {
        Map<String, Object> paraMap = new HashMap<>();
        try {
            if (valueMap.containsKey("role") && StringUtils.isNotBlank(valueMap.get("role").toString())) {
                paraMap.put("role", "%" + valueMap.get("role").toString() + "%");
            }
            if (valueMap.containsKey("code") && StringUtils.isNotBlank(valueMap.get("code").toString())) {
                paraMap.put("code", valueMap.get("code").toString());
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
            paraMap.put("orderBy", "id");
        } catch (Exception e) {
            logger.error(ExceptionUtil.stackTraceText(e));
            throw new ServiceException("生成查询条件时出错！");
        }

        PageHelper.offsetPage(page.getOffset(), page.getPageSize());
        List<Map<String, Object>> list = sysRoleMapper.getRoleList(paraMap);
        page.setResult(list);
        page.setTotalCount(((Page) list).getTotal());

        Date date;
        for (Map<String, Object> amap : list) {

            if (amap.get("status") != null && StringUtils.isNotBlank(amap.get("status").toString())) {
                amap.put("statusString", DictionaryUtil.getString(DictionaryUtil.SYS_USER_STATUS,
                        (Integer) amap.get("status")));
            }
            if (amap.get("code") == null) {
                amap.put("code", "");
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
     * 禁用角色
     *
     * @param roleId
     * @return
     */
    @Override
    public ResponseDTO disableRole(Long roleId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysRole tSysRole = new TSysRole();
        tSysRole.setId(roleId);
        tSysRole.setStatus(SystemDictionary.SYS_USER_DISABLE_STATUS);
        tSysRole.setUpdateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(tSysRole);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("禁用角色成功！");

        return responseDTO;
    }

    /**
     * 启用角色
     *
     * @param roleId
     * @return
     */
    @Override
    public ResponseDTO enableRole(Long roleId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysRole tSysRole = new TSysRole();
        tSysRole.setId(roleId);
        tSysRole.setStatus(SystemDictionary.SYS_USER_ENABLE_STATUS);
        tSysRole.setUpdateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(tSysRole);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("启用角色成功！");

        return responseDTO;
    }

    /**
     * 获取角色详情
     *
     * @param roleId
     * @return
     */
    @Override
    public ResponseDTO getRoleDetail(Long roleId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysRole tSysRole = sysRoleMapper.selectByPrimaryKey(roleId);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("sysRole", tSysRole);
        responseDTO.setDataMap(dataMap);
        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("获取角色详情成功!");

        return responseDTO;
    }

    /**
     * 编辑角色保存
     *
     * @param paraMap
     * @return
     */
    @Override
    public ResponseDTO editRoleSave(Map<String, Object> paraMap) {
        ResponseDTO responseDTO = new ResponseDTO();
        Date now = new Date();

        TSysRole tSysRole = new TSysRole();
        tSysRole.setId(Long.parseLong(paraMap.get("roleId").toString()));
        tSysRole.setRole(paraMap.get("role").toString());
        tSysRole.setDescription(paraMap.get("description").toString());
        tSysRole.setCode(paraMap.get("code").toString());
        tSysRole.setUpdateTime(now);

        sysRoleMapper.updateByPrimaryKeySelective(tSysRole);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("保存成功!");
        return responseDTO;
    }

    /**
     * 获取角色权限
     *
     * @param roleId
     * @return
     */
    @Override
    public ResponseDTO getRolePermissionDetail(Long roleId) {
        ResponseDTO responseDTO = new ResponseDTO();

        // 所有权限
        List<TSysPermission> permissionList = sysPermissionMapper.selectAllEnabled();
        // 角色权限
        List<TSysRole> rolePermissionList = sysRolePermissionMapper.selectByRoleId(roleId);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("permissionList", permissionList);
        dataMap.put("rolePermissionList", rolePermissionList);
        responseDTO.setDataMap(dataMap);
        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("获取角色权限成功！");

        return responseDTO;
    }

    /**
     * 分配角色权限保存
     *
     * @param map
     * @return
     */
    @Override
    public ResponseDTO editRolePermissionSave(Map<String, Object> map) {
        ResponseDTO responseDTO = new ResponseDTO();
        String[] permissionArray;
        if (map.get("permissionSelect") instanceof String) {
            permissionArray = new String[1];
            permissionArray[0] = map.get("permissionSelect").toString();
        } else {
            permissionArray = (String[]) map.get("permissionSelect");
        }
        Long roleId = Long.parseLong(map.get("roleId").toString());
        // 删除角色原来的权限
        sysRolePermissionMapper.deleteByPrimaryKey(roleId);

        // 为角色分配新权限
        TSysRolePermission tSysRolePermission;
        if (permissionArray != null) {
            for (String permissionId : permissionArray) {
                tSysRolePermission = new TSysRolePermission();
                tSysRolePermission.setRoleId(roleId);
                tSysRolePermission.setPermissionId(Long.parseLong(permissionId));
                tSysRolePermission.setCreateTime(new Date());
                sysRolePermissionMapper.insertSelective(tSysRolePermission);
            }
        }

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("保存成功！");

        return responseDTO;
    }

    /**
     * 创建新角色保存
     *
     * @param paraMap
     * @return
     */
    @Override
    public ResponseDTO addRoleSave(Map<String, Object> paraMap) {
        ResponseDTO responseDTO = new ResponseDTO();
        Date now = new Date();

        TSysRole tSysRole = new TSysRole();
        tSysRole.setRole(paraMap.get("role").toString());
        tSysRole.setDescription(paraMap.get("description").toString());
        tSysRole.setCode(paraMap.get("code").toString());
        tSysRole.setStatus(STATUS_ENABLED);
        tSysRole.setCreateTime(now);

        sysRoleMapper.insert(tSysRole);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("创建成功!");
        return responseDTO;
    }

}
