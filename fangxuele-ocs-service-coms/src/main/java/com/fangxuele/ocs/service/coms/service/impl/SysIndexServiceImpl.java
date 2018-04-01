package com.fangxuele.ocs.service.coms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxuele.ocs.inter.coms.service.SysIndexService;
import com.fangxuele.ocs.mapper.domain.TSysNotice;
import com.fangxuele.ocs.mapper.mapper.TSysNoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
@Service(version = "1.0.0", interfaceClass = SysIndexService.class, timeout = 10000, cluster = "failfast")
public class SysIndexServiceImpl implements SysIndexService {

    private static final Logger logger = LoggerFactory.getLogger(SysIndexServiceImpl.class);

    @Autowired
    private TSysNoticeMapper sysNoticeMapper;

    /**
     * 获取启用状态的公告列表
     *
     * @return
     */
    @Override
    public List<TSysNotice> getAllEnabledNotice() {
        return sysNoticeMapper.selectAllEnabled();
    }
}
