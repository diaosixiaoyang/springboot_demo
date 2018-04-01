package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.common.dictionary.SystemDictionary;
import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;
import com.fangxuele.ocs.common.util.DictionaryUtil;
import com.fangxuele.ocs.inter.coms.service.SystemPermissionService;
import com.fangxuele.ocs.mapper.domain.TSysPermission;
import com.fangxuele.ocs.mapper.mapper.TSysPermissionMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rememberber(https://github.com/rememberber)
 */
@Service(version = "1.0.0", interfaceClass = SystemPermissionService.class, timeout = 10000, cluster = "failfast")
public class SystemPermissionServiceImpl implements SystemPermissionService {

    private static final Logger logger = LoggerFactory.getLogger(SystemPermissionServiceImpl.class);

    @Autowired
    TSysPermissionMapper sysPermissionMapper;

    /**
     * 查询权限列表
     *
     * @param valueMap
     * @param page
     * @return
     */
    @Override
    public CommonPage getPermissionList(Map<String, Object> valueMap, CommonPage page) {
        Map<String, Object> paraMap = new HashMap<>();

        PageHelper.offsetPage(page.getOffset(), page.getPageSize());
        List<Map<String, Object>> list = sysPermissionMapper.getPermissionList(paraMap);
        page.setResult(list);
        page.setTotalCount(((Page) list).getTotal());

        Date date;
        for (Map<String, Object> amap : list) {

            if (amap.get("status") != null && StringUtils.isNotBlank(amap.get("status").toString())) {
                amap.put("statusString", DictionaryUtil.getString(DictionaryUtil.SYS_USER_STATUS,
                        (Integer) amap.get("status")));
            }
            if (amap.get("icon_class") == null) {
                amap.put("icon_class", "");
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

        return searchParaMap;
    }

    /**
     * 禁用权限
     *
     * @param permissionId
     * @return
     */
    @Override
    public ResponseDTO disablePermission(Long permissionId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysPermission tSysPermission = new TSysPermission();
        tSysPermission.setId(permissionId);
        tSysPermission.setStatus(SystemDictionary.SYS_USER_DISABLE_STATUS);
        tSysPermission.setUpdateTime(new Date());
        sysPermissionMapper.updateByPrimaryKeySelective(tSysPermission);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("禁用权限成功！");

        return responseDTO;
    }

    /**
     * 启用权限
     *
     * @param permissionId
     * @return
     */
    @Override
    public ResponseDTO enablePermission(Long permissionId) {
        ResponseDTO responseDTO = new ResponseDTO();

        TSysPermission tSysPermission = new TSysPermission();
        tSysPermission.setId(permissionId);
        tSysPermission.setStatus(SystemDictionary.SYS_USER_ENABLE_STATUS);
        tSysPermission.setUpdateTime(new Date());
        sysPermissionMapper.updateByPrimaryKeySelective(tSysPermission);

        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg("启用权限成功！");

        return responseDTO;
    }

}
