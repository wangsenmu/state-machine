package com.wsm.order.server.states.submitted;

import com.wsm.order.server.StateImpl;
import com.wsm.order.server.Transition;
import com.wsm.order.server.states.OrderV1States;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class SubmittedState extends StateImpl {
    /**
     * 构造方法
     *
     * @param orderStateId
     * @param applicationContext
     */
    public SubmittedState(Long orderStateId, ApplicationContext applicationContext) {
        super(orderStateId, applicationContext);
    }

    @Override
    public void doInit() {

    }

    @Override
    public boolean doAction() {
        System.out.println("锁定资源");
        return true;
    }

    @Override
    public void doDone(String nextState) {

    }

    @Override
    public List<Transition> getTransitions() {
        ArrayList<Transition> result = new ArrayList<>();
        result.add(new Transition() {
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
//                OrderStateDataAccess orderStateDataAccess = getApplicationContext().getBean(OrderStateDataAccess.class);
//                OrderStateEntity orderStateEntity = orderStateDataAccess.getByOrderStateId(getOrderStateId());
                return true;
            }

            @Override
            public String getTargetState() {
                return OrderV1States.InPay;
            }
        });
        result.add(new Transition() {
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
                return false;
            }

            @Override
            public String getTargetState() {
                return OrderV1States.Failed;
            }
        });
        return result;
    }
}
