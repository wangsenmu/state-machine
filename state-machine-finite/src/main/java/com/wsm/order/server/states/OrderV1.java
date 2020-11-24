package com.wsm.order.server.states;

import com.wsm.order.server.OrderImpl;
import com.wsm.order.server.StateFactory;
import org.springframework.context.ApplicationContext;

import java.util.Dictionary;
import java.util.Hashtable;

public class OrderV1 extends OrderImpl {

    public OrderV1(Long orderId, ApplicationContext applicationContext) {
        super(orderId, applicationContext);
    }

    @Override
    public Dictionary<String, StateFactory> getStateFactories() {
        Dictionary<String, StateFactory> factories = new Hashtable<>();
//
//        factories.put(OrderV1States.Created, this.getApplicationContext().getBean(CreatedStateFactory.class));
//        factories.put(OrderV1States.Submitted, this.getApplicationContext().getBean(SubmittedStateFactory.class));
//        factories.put(OrderV1States.InPay, this.getApplicationContext().getBean(InPayStateFactory.class));
//        factories.put(OrderV1States.Payed, this.getApplicationContext().getBean(PayedStateFactory.class));
//        factories.put(OrderV1States.Failed, this.getApplicationContext().getBean(FailedStateFactory.class));

        return factories;
    }

    @Override
    public String getInitialState() {
        return OrderV1States.Created;
    }
}
