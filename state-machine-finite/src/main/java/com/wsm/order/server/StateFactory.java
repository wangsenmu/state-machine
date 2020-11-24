package com.wsm.order.server;

/**
 * 状态工厂
 */
public interface StateFactory {

    /**
     * 加载状态
     *
     * @param orderStateId 状态实例编号
     * @return 状态
     */
    State loadState(Long orderStateId);
}
