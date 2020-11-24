package com.wsm.order.dao.mapper;

import com.wsm.order.dao.model.OrderDO;

public interface OrderDOMapper {
    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrderDO record);

    OrderDO getByOrderIdWithLock(Long orderId);
}