package com.wsm.order.server.states.created;

import com.wsm.order.server.StateImpl;
import com.wsm.order.server.Transition;
import com.wsm.order.server.states.OrderV1States;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;


public class CreatedState extends StateImpl {
    /**
     * 构造方法
     *
     * @param orderStateId
     * @param applicationContext
     */
    public CreatedState(Long orderStateId, ApplicationContext applicationContext) {
        super(orderStateId, applicationContext);
    }

    @Override
    public void doInit() {
        System.out.println("流程进入已创建状态-初始化过程！");
    }

    @Override
    public boolean doAction() {
        System.out.println("流程进入已创建状态-运行核心逻辑！");
        return true;
    }

    @Override
    public void doDone(String nextState) {
        System.out.println("流程进入已创建状态-清理过程！，即将进入" + nextState);
    }

    @Override
    public List<Transition> getTransitions() {
        ArrayList<Transition> result = new ArrayList<>();
        result.add(new Transition() {
            @Override
            public boolean isAutoFlow() {
                return false;
            }

            @Override
            public String getEvent() {
                return "submit";
            }

            @Override
            public boolean evalCondition(Dictionary<String, Object> extraInfos) {
                return true;
            }

            @Override
            public String getTargetState() {
                return OrderV1States.Submitted;
            }
        });
        return result;
    }
}
