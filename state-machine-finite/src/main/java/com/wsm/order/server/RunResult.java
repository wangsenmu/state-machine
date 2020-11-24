package com.wsm.order.server;

/**
 * 运行结果
 */
public enum RunResult {
    /**
     * 订单在锁定中，无法运行
     */
    InBusy,

    /**
     * 订单在运行中，但没有明确结果
     */
    InProcessing,

    /**
     * 订单已经安静，可以接收事件或已运行结束
     */
    CalmDown
}
