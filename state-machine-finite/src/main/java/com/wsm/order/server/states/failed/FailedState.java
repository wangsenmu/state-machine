package com.wsm.order.server.states.failed;

import com.wsm.order.server.StateImpl;
import com.wsm.order.server.Transition;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class FailedState extends StateImpl {
    /**
     * 构造方法
     *
     * @param orderStateId
     * @param applicationContext
     */
    protected FailedState(Long orderStateId, ApplicationContext applicationContext) {
        super(orderStateId, applicationContext);
    }

    @Override
    public void doInit() {

    }

    @Override
    public boolean doAction() {
        return true;
    }

    @Override
    public void doDone(String nextState) {

    }

    @Override
    public List<Transition> getTransitions() {
        return null;
    }
}
