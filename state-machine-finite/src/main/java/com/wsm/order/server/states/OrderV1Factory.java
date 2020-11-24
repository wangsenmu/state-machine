package com.wsm.order.server.states;

import com.wsm.order.server.Order;
import com.wsm.order.server.OrderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("order_v1_factory")
public class OrderV1Factory implements OrderFactory {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public Order loadOrder(Long orderId) {
        return new OrderV1(orderId, applicationContext);
    }
}
