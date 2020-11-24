package com.wsm.order.server;

import java.util.List;

/**
 * 订单步骤
 */
public interface State {

    /**
     * 获取订单步骤编号
     * @return
     */
    Long getOrderStateId();

    /**
     * 初始化步骤
     */
    void init();

    /**
     * 运行步骤
     */
    void action();

    /**
     * 清理步骤
     */
    void done(String nextState);

    /**
     * @return
     */
    List<Transition> getTransitions();

    /**
     * 获取运行状态
     *
     * @return
     */
    StateStatus getStatus();
}
