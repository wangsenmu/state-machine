package com.wsm.order.server.states.inpay;

import com.wsm.order.server.StateImpl;
import com.wsm.order.server.Transition;
import com.wsm.order.server.states.OrderV1States;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class InPayState extends StateImpl {
    /**
     * 构造方法
     *
     * @param orderStateId
     * @param applicationContext
     */
    protected InPayState(Long orderStateId, ApplicationContext applicationContext) {
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
        ArrayList<Transition> transitions = new ArrayList<>();
        transitions.add(new Transition() {
            @Override
            public boolean isAutoFlow() {
                return true;
            }

            @Override
            public String getEvent() {
                return null;
            }

            @Override
            public boolean evalCondition(Dictionary<String, Object> extraInfos) {
                return true;
            }

            @Override
            public String getTargetState() {
                return OrderV1States.Payed;
            }
        });
        return transitions;
    }
}
