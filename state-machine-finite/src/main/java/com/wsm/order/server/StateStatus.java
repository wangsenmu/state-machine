package com.wsm.order.server;

/**
 * 结点状态
 */
public enum StateStatus {
    /**
     * 已创建
     */
    Created,

    /**
     * 已初始化
     */
    Initialized,

    /**
     * 在处理中
     */
    InProcessing,

    /**
     * 已处理完成
     */
    Processed,

    /**
     * 已结束
     */
    Finished
}
