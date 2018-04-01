package com.fangxuele.ocs.inter.coms.service;


import com.fangxuele.ocs.common.util.CommonPage;

import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public interface SystemLogInfoService {
    /**
     * 查询列表
     *
     * @param map
     * @param page
     * @return
     */
    CommonPage getLogInfoList(Map<String, Object> map, CommonPage page);

    /**
     * 搜索参数（下拉列表参数）
     *
     * @return
     */
    Map<String, Object> getSearchParaMap();
}
