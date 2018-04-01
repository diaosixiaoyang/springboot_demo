package com.fangxuele.ocs.inter.coms.service;


import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.CommonPage;

import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public interface SystemNoticeService {

    /**
     * 查询公告列表
     *
     * @param map
     * @param page
     * @return
     */
    CommonPage getNoticeList(Map<String, Object> map, CommonPage page);

    /**
     * 搜索参数
     *
     * @return
     */
    Map<String, Map<String, String>> getSearchParaMap();

    /**
     * 禁用公告
     *
     * @param noticeId
     * @return
     */
    ResponseDTO disableNotice(Long noticeId);

    /**
     * 启用公告
     *
     * @param noticeId
     * @return
     */
    ResponseDTO enableNotice(Long noticeId);

    /**
     * 删除公告
     *
     * @param noticeId
     * @return
     */
    ResponseDTO deleteNotice(Long noticeId);

    /**
     * 获取公告详情
     *
     * @param noticeId
     * @return
     */
    ResponseDTO getNoticeDetail(Long noticeId);

    /**
     * 编辑公告保存
     *
     * @param map
     * @return
     */
    ResponseDTO editNoticeSave(Map<String, Object> map);

    /**
     * 添加新公告保存
     *
     * @param map
     * @return
     */
    ResponseDTO addNoticeSave(Map<String, Object> map);
}
