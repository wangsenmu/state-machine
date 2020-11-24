package com.wsm.order.server;

/**
 * 订单工厂
 */
public interface OrderFactory {

    /**
     * 加载订单
     * @param orderId 订单编号
     * @return 订单
     */
    Order loadOrder(Long orderId);
}
