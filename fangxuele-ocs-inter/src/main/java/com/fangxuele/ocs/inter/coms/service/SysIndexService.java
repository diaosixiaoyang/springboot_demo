package com.fangxuele.ocs.inter.coms.service;


import com.fangxuele.ocs.mapper.domain.TSysNotice;

import java.util.List;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public interface SysIndexService {

    /**
     * 获取启用状态的公告列表
     *
     * @return
     */
    List<TSysNotice> getAllEnabledNotice();
}
