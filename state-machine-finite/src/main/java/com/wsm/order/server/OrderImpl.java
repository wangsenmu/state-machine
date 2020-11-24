package com.wsm.order.server;

import com.wsm.order.dao.mapper.OrderDOMapper;
import com.wsm.order.dao.mapper.OrderStateDOMapper;
import com.wsm.order.dao.model.OrderDO;
import com.wsm.order.dao.model.OrderStateDO;
import com.wsm.order.server.enumerate.LockedEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;

/**
 * 订单基类
 */
public abstract class OrderImpl implements Order {

    //region constructor

    private final Long orderId;
    private final ApplicationContext applicationContext;

    private final OrderStateDOMapper orderStateDOMapper;
    private final OrderDOMapper orderDOMapper;

    private final PlatformTransactionManager transactionManager;

    /**
     * 订单编号
     *
     * @return
     */
    @Override
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * 容器上下文
     *
     * @return
     */
    protected ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    /**
     * 构造方法
     *
     * @param orderId            订单编号
     * @param applicationContext 容器上下文
     */
    protected OrderImpl(Long orderId, ApplicationContext applicationContext) {
        this.orderId = orderId;
        this.applicationContext = applicationContext;

        this.orderStateDOMapper = (OrderStateDOMapper) applicationContext.getBean("orderStateDOMapper");
        this.orderDOMapper = (OrderDOMapper) applicationContext.getBean("orderDOMapper");
        this.transactionManager = (PlatformTransactionManager) applicationContext.getBean("transactionManager");

    }

    //endregion

    //region state

    /**
     * 获取订单的状态工厂
     *
     * @return 状态名及状态工厂字典
     */
    @Override
    public abstract Dictionary<String, StateFactory> getStateFactories();

    /**
     * 获取初始状态名
     *
     * @return
     */
    @Override
    public abstract String getInitialState();

    /**
     * 获取当前状态名
     *
     * @return
     */
    @Override
    public String getCurrentState() {
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
        return orderDO.getCurrentOrderStateName();
    }

    //endregion

    //region lock order or unlock order

    /**
     * 生效状态
     */
    private static class Status {
        public boolean affected = false;
    }

    /**
     * 偿试锁定任务
     *
     * @return 是否锁定
     */
    private boolean tryLockOrder() {
        final Status lockStatus = new Status();
        lockStatus.affected = false;

        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Date now = new Date();
                OrderDO orderDO = orderDOMapper.getByOrderIdWithLock(orderId);
                if (LockedEnum.UNLOCK.getCode().equals(orderDO.getLocked()) || now.getTime() - orderDO.getLastProcessTime().getTime() > 1000 * 60 * 5L) {
                    orderDO.setLocked(LockedEnum.LOCKED.getCode());
                    orderDO.setLastProcessTime(now);
                    orderDOMapper.updateByPrimaryKeySelective(orderDO);
                    lockStatus.affected = true;
                }
            }
        });
        return lockStatus.affected;
    }

    /**
     * 偿试解锁任务
     *
     * @return 是否解锁
     */
    private boolean tryUnlockOrder() {

        final Status unLockStatus = new Status();
        unLockStatus.affected = false;

        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                OrderDO orderDO = orderDOMapper.getByOrderIdWithLock(orderId);
                if (LockedEnum.LOCKED.getCode().equals(orderDO.getLocked())) {
                    orderDO.setLocked(LockedEnum.UNLOCK.getCode());
                    orderDOMapper.updateByPrimaryKeySelective(orderDO);
                    unLockStatus.affected = true;
                }
            }
        });

        return unLockStatus.affected;
    }

    //endregion

    //region transition eval

    /**
     * 迁移评估结果
     */
    private static class EvalResult {
        /**
         * 是否找到匹配迁移
         */
        public boolean matched = false;

        /**
         * 找到的匹配迁移
         */
        public Transition transition;

        /**
         * 是否没有有效迁移
         */
        public boolean ended = false;
    }

    /**
     * 评估自动迁移
     *
     * @param state 状态
     * @return
     */
    private EvalResult evalTransitions(State state) {
        if (state == null)
            throw new IllegalArgumentException("state");

        if (state.getStatus() != StateStatus.Processed)
            throw new IllegalStateException("只有处理完成的步骤才能评估迁移！");

        EvalResult evalResult = new EvalResult();

        List<Transition> transitions = state.getTransitions();

        if (transitions == null || transitions.size() == 0) {
            //不存在迁移，则结束
            evalResult.matched = false;
            evalResult.ended = true;
        } else {
            for (Transition transition : transitions) {
                if (transition.isAutoFlow() && transition.evalCondition(null)) {
                    //找到匹配迁移
                    evalResult.matched = true;
                    evalResult.transition = transition;
                    evalResult.ended = false;
                    return evalResult;
                }
            }
            //没有匹配迁移
            evalResult.matched = false;
            evalResult.ended = false;
        }
        return evalResult;
    }

    /**
     * 评估事件迁移
     *
     * @param state      状态
     * @param event      事件
     * @param extraInfos 事件参数
     * @return
     */
    private EvalResult evalTransitions(State state, String event, Dictionary<String, Object> extraInfos) {
        if (state == null)
            throw new IllegalArgumentException("state");

        if (event == null)
            throw new IllegalArgumentException("event");

        if (state.getStatus() != StateStatus.Processed)
            throw new IllegalStateException("只有处理完成的步骤才能评估迁移！");

        EvalResult evalResult = new EvalResult();
        List<Transition> transitions = state.getTransitions();
        if (transitions == null || transitions.size() == 0) {
            //不存在迁移，则结束
            evalResult.matched = false;
            evalResult.ended = true;
        } else {
            for (Transition transition : transitions) {
                if (event.equals(transition.getEvent()) && transition.evalCondition(extraInfos)) {
                    //找到匹配迁移
                    evalResult.matched = true;
                    evalResult.transition = transition;
                    evalResult.ended = false;
                    return evalResult;
                }
            }
            //没有匹配迁移
            evalResult.matched = false;
            evalResult.ended = false;
        }
        return evalResult;
    }

    //endregion

    //region process order

    /**
     * 创建步骤
     *
     * @param prevStateId 上一步骤
     * @param state       本步骤
     */
    private void createState(Long prevStateId, String state) {

        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                Date now = new Date();
                String user = "system";

                OrderDO orderDO = orderDOMapper.getByOrderIdWithLock(orderId);

                OrderStateDO orderStateDO = new OrderStateDO();

                orderStateDO.setOrderId(orderDO.getOrderId());
                orderStateDO.setPrevOrderStateId(prevStateId);
                orderStateDO.setName(state);

                orderStateDO.setLastProcessTime(now);
                orderStateDO.setStatus(StateStatus.Created.ordinal());

                orderStateDO.setIsActive(true);
                orderStateDO.setCreator(user);
                orderStateDO.setInsertTime(now);
                orderStateDO.setModifier(user);
                orderStateDO.setUpdateTime(now);

                orderStateDOMapper.insertSelective(orderStateDO);

                orderDO.setCurrentOrderStateId(orderStateDO.getOrderStateId());
                orderDO.setCurrentOrderStateName(state);

                orderDOMapper.updateByPrimaryKeySelective(orderDO);

            }
        });

    }

    /**
     * 加载当前订单状态
     *
     * @return
     */
    protected State loadCurrentState() {

        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(this.getOrderId());
        if (orderDO.getCurrentOrderStateId() == null) {
            this.createState(null, this.getInitialState());
            orderDO = orderDOMapper.selectByPrimaryKey(this.getOrderId());
        }
        StateFactory loadStateFactory = this.getStateFactories().get(orderDO.getCurrentOrderStateName());
        return loadStateFactory.loadState(orderDO.getCurrentOrderStateId());
    }


    /**
     * 处理状态机
     */
    private void doProcess() {

        boolean processContinue;
        do {
            /**
             * 切换当前步骤
             */
            State currentState = this.loadCurrentState();

            /**
             * 已处理完成直接返回
             */
            if (currentState.getStatus() == StateStatus.Finished)
                return;

            /**
             * 若才创建，则初始化
             */
            if (currentState.getStatus() == StateStatus.Created) {
                currentState.init();
            }

            /**
             * 运行
             */
            if (currentState.getStatus() == StateStatus.Initialized || currentState.getStatus() == StateStatus.InProcessing) {
                currentState.action();
            }

            /**
             * 运行结果为未完成则不用继续处理
             */
            if (currentState.getStatus() == StateStatus.InProcessing) {
                processContinue = false;
            } else {
                /**
                 * 如果运行结果已完成则偿试自动迁移
                 */
                EvalResult evalResult = evalTransitions(currentState);
                if (evalResult.matched) {
                    /**
                     * 若匹配成功，对状态进行迁移，并继续后续步骤
                     */
                    String nextState = evalResult.transition.getTargetState();
                    currentState.done(nextState);
                    createState(currentState.getOrderStateId(), nextState);
                    processContinue = true;
                } else {
                    /**
                     * 若未匹配或结束，则退出处理
                     */
                    processContinue = false;

                    /**
                     * 若没有迁移状态，则结束
                     */
                    if (evalResult.ended) {
                        currentState.done(null);
                    }
                }
            }
        }
        while (processContinue);
    }

    /**
     * 运行订单
     *
     * @return 运行结果
     */
    @Override
    public boolean process() {
        if (this.tryLockOrder()) {
            try {
                this.doProcess();
                return true;
            } finally {
                this.tryUnlockOrder();
            }
        } else {
            return false;
        }
    }

    /**
     * 发送事件
     *
     * @param event      消息
     * @param extraInfos 额外参数
     * @return
     */
    @Override
    public boolean sendEvent(String event, Dictionary<String, Object> extraInfos) {
        if (this.tryLockOrder()) {
            try {
                this.doProcess();
                State currentState = this.loadCurrentState();
                if (currentState.getStatus() == StateStatus.Processed) {
                    EvalResult evalResult = evalTransitions(currentState, event, extraInfos);
                    if (evalResult.matched) {
                        /**
                         * 若匹配成功，对状态进行迁移，并继续后续步骤
                         */
                        String nextState = evalResult.transition.getTargetState();
                        currentState.done(nextState);
                        createState(currentState.getOrderStateId(), nextState);
                        this.doProcess();
                    }
                }
                return true;
            } finally {
                this.tryUnlockOrder();
            }
        } else {
            return false;
        }
    }
    //endregion

}
