package com.wsm.order.dao.mapper;

import com.wsm.order.dao.model.OrderStateDO;

public interface OrderStateDOMapper {
    int insertSelective(OrderStateDO record);

    OrderStateDO selectByPrimaryKey(Long orderStateId);

    OrderStateDO getByOrderStateIdWithLock(Long orderStateId);

    int updateByPrimaryKeySelective(OrderStateDO record);
}