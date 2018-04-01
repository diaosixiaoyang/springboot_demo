package com.fangxuele.ocs.mapper.mapper;

import com.fangxuele.ocs.mapper.domain.TWxPay;

public interface TWxPayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPay record);

    int insertSelective(TWxPay record);

    TWxPay selectByPrimaryKey(Long id);

    TWxPay selectByOrderId(Long orderId);

    int updateByPrimaryKeySelective(TWxPay record);

    int updateByPrimaryKey(TWxPay record);
}