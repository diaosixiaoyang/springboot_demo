package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TSysNotice;

public interface TSysNoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysNotice record);

    int insertSelective(TSysNotice record);

    TSysNotice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysNotice record);

    int updateByPrimaryKey(TSysNotice record);
}