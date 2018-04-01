package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSysNotice;

import java.util.List;
import java.util.Map;

public interface TSysNoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysNotice record);

    int insertSelective(TSysNotice record);

    TSysNotice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysNotice record);

    int updateByPrimaryKey(TSysNotice record);

    List<TSysNotice> selectAllEnabled();

    List<Map<String, Object>> getNoticeList(Map<String, Object> paraMap);

    List<Map<String, Object>> selectAllCreator();
}