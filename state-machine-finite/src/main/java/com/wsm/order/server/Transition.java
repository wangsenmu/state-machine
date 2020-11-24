package com.wsm.order.server;

import java.util.Dictionary;

/**
 * 状态迁移
 */
public interface Transition {

    /**
     * 是否为自动流转，无需事件触发
     *
     * @return
     */
    boolean isAutoFlow();

    /**
     * 侦听的事件
     *
     * @return 事件
     */
    String getEvent();

    /**
     * 评估条件
     *
     * @param extraInfos 额外信息
     * @return 是否满足迁移条件
     */
    boolean evalCondition(Dictionary<String, Object> extraInfos);

    /**
     * 获取目标状态
     *
     * @return 目标状态
     */
    String getTargetState();

}
