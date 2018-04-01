package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TWxPay;

public interface TWxPayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPay record);

    int insertSelective(TWxPay record);

    TWxPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPay record);

    int updateByPrimaryKey(TWxPay record);
}