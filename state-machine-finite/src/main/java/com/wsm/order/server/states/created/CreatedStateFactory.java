package com.wsm.order.server.states.created;

import com.wsm.order.server.State;
import com.wsm.order.server.StateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CreatedStateFactory implements StateFactory {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public State loadState(Long orderStateId) {
        return new CreatedState(orderStateId, applicationContext);
    }
}
