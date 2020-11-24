package com.wsm.order.server;


import com.wsm.order.dao.mapper.OrderDOMapper;
import com.wsm.order.dao.mapper.OrderStateDOMapper;
import com.wsm.order.dao.model.OrderStateDO;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * 状态基类
 */
public abstract class StateImpl implements State {

    //region construct
    private Long orderStateId;
    private ApplicationContext applicationContext;

    private final OrderStateDOMapper orderStateDOMapper;
    private final OrderDOMapper orderDOMapper;

    private final PlatformTransactionManager transactionManager;

    /**
     * 订单状态编号
     *
     * @return
     */
    @Override
    public Long getOrderStateId() {
        return this.orderStateId;
    }

    /**
     * 容器
     *
     * @return
     */
    protected ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    /**
     * 构造方法
     *
     * @param orderStateId
     * @param applicationContext
     */
    protected StateImpl(Long orderStateId, ApplicationContext applicationContext) {
        this.orderStateId = orderStateId;
        this.applicationContext = applicationContext;
        this.orderStateDOMapper = (OrderStateDOMapper) applicationContext.getBean("orderStateDOMapper");
        this.orderDOMapper = (OrderDOMapper) applicationContext.getBean("orderDOMapper");
        this.transactionManager = (PlatformTransactionManager) applicationContext.getBean("transactionManager");

    }
    //endregion

    //region template method

    /**
     * 初始化
     */
    public abstract void doInit();

    @Override
    public void init() {
        doInit();
        this.setOrderStateStatus(StateStatus.Initialized);
    }

    /**
     * 运行逻辑
     *
     * @return 是否处理结束
     */
    public abstract boolean doAction();

    @Override
    public void action() {
        if (doAction()) {
            this.setOrderStateStatus(StateStatus.Processed);
        } else {
            this.setOrderStateStatus(StateStatus.InProcessing);
        }
    }

    /**
     * 清理
     */
    public abstract void doDone(String nextState);

    @Override
    public void done(String nextState) {
        doDone(nextState);
        this.setOrderStateStatus(StateStatus.Finished);
    }

    /**
     * 设置步骤状态
     *
     * @param stateStatus
     */
    private void setOrderStateStatus(StateStatus stateStatus) {

        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                OrderStateDO orderStateDO = orderStateDOMapper.getByOrderStateIdWithLock(orderStateId);
                orderStateDO.setStatus(stateStatus.ordinal());
                orderStateDOMapper.updateByPrimaryKeySelective(orderStateDO);
            }
        });
    }

    //endregion

    //region transitions

    /**
     * 获取迁移路径
     *
     * @return
     */
    @Override
    public abstract List<Transition> getTransitions();

    //endregion

    //region status

    /**
     * 获取当前状态
     *
     * @return
     */
    @Override
    public StateStatus getStatus() {
        OrderStateDO orderStateDO = orderStateDOMapper.selectByPrimaryKey(this.getOrderStateId());
        return StateStatus.values()[orderStateDO.getStatus()];
    }

    //endregion
}
