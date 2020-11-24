package com.wsm.order.server;

import java.util.Dictionary;

/**
 * 订单
 */
public interface Order {

    /**
     * 获取订单ID
     * @return
     */
    Long getOrderId();

    /**
     * 状态工厂
     *
     * @return
     */
    Dictionary<String, StateFactory> getStateFactories();

    /**
     * 初始状态
     *
     * @return
     */
    String getInitialState();

    /**
     * 获取当前状态
     *
     * @return
     */
    String getCurrentState();

    /**
     * 推动订单继续运行
     *
     * @return 是否成功运行
     */
    boolean process();

    /**
     * 发送消息
     *
     * @param event      消息
     * @param extraInfos 额外参数
     * @return 是否成功运行
     */
    boolean sendEvent(String event, Dictionary<String, Object> extraInfos);

}
