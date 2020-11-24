package com.wsm.order.server.states.submitted;

import com.wsm.order.server.State;
import com.wsm.order.server.StateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SubmittedStateFactory implements StateFactory {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public State loadState(Long orderStateId) {
        return new SubmittedState(orderStateId, applicationContext);
    }
}
