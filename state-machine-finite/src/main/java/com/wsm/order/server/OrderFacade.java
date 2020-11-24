//package com.wsm.order;
//
//import arch.framework.data.Database;
//import arch.framework.data.command.DbQueryCriteria;
//import arch.framework.data.command.DbQueryResult;
//import arch.order.domain.dataaccess.*;
//import arch.order.domain.entity.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.TransactionCallbackWithoutResult;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import java.sql.Timestamp;
//
//@Component
//public class OrderFacade {
//
//    //region data access
//    @Autowired
//    public Database database;
//
//    @Autowired
//    OrderDataAccess orderDataAccess;
//
//    @Autowired
//    OrderProductDataAccess orderProductDataAccess;
//
//    @Autowired
//    OrderPayDataAccess orderPayDataAccess;
//
//    @Autowired
//    OrderPayPointDataAccess orderPayPointDataAccess;
//
//    @Autowired
//    OrderPayCouponDataAccess orderPayCouponDataAccess;
//
//    @Autowired
//    OrderPayCashierDataAccess orderPayCashierDataAccess;
//
//    @Autowired
//    OrderVersionDataAccess orderVersionDataAccess;
//    //endregion
//
//    //region query order
//
//    /**
//     * 获取单笔订单
//     *
//     * @param orderId 订单编号
//     * @return
//     */
//    public OrderEntity getOrder(Long orderId) {
//        return orderDataAccess.getByOrderId(orderId);
//    }
//
//    /**
//     * 查询订单
//     *
//     * @param criteria
//     * @return
//     */
//    public DbQueryResult<OrderEntity> queryOrder(DbQueryCriteria criteria) {
//        return orderDataAccess.query(criteria);
//    }
//    //endregion
//
//    //region order factory
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    /**
//     * 加载订单对象
//     *
//     * @param orderId 订单编号
//     * @return
//     */
//    private Order loadOrder(Long orderId) {
//        OrderEntity orderEntity = orderDataAccess.getByOrderId(orderId);
//        OrderFactory orderFactory = applicationContext.getBean(orderEntity.getOrderFactory(), OrderFactory.class);
//        return orderFactory.loadOrder(orderId);
//    }
//
//    //endregion
//
//    //region order process
//
//    /**
//     * 创建订单
//     *
//     * @param orderEntity 订单信息
//     * @return 订单编号
//     */
//    public Long createOrder(OrderEntity orderEntity) {
//        OrderVersionEntity orderVersionEntity = orderVersionDataAccess.getByOrderVersionId(orderEntity.getOrderVersionId());
//
//        TransactionTemplate template = new TransactionTemplate(database.getTransactionManager());
//        template.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus status) {
//                //region save entity
//                Timestamp now = new Timestamp(System.currentTimeMillis());
//                String user = "sys";
//
//                orderEntity.setOrderVersionId(1);
//                orderEntity.setOrderTime(now);
//
//                orderEntity.setLocked(false);
//                orderEntity.setLastProcessTime(now);
//
//                orderEntity.setIsActive(true);
//                orderEntity.setCreator(user);
//                orderEntity.setInsertTime(now);
//                orderEntity.setModifier(user);
//                orderEntity.setUpdateTime(now);
//
//                orderEntity.setProcessInterval(1000);
//
//                orderEntity.setOrderFactory(orderVersionEntity.getOrderFactory());
//
//                orderDataAccess.create(orderEntity);
//
//                for (OrderProductEntity orderProductEntity : orderEntity.getOrderProducts()) {
//
//                    orderProductEntity.setOrderId(orderEntity.getOrderId());
//                    orderProductEntity.setIsActive(true);
//                    orderProductEntity.setCreator(user);
//                    orderProductEntity.setInsertTime(now);
//                    orderProductEntity.setModifier(user);
//                    orderProductEntity.setUpdateTime(now);
//
//                    orderProductDataAccess.create(orderProductEntity);
//                    //save extra info
//                }
//
//                for (OrderPayEntity orderPayEntity : orderEntity.getOrderPays()) {
//
//                    orderPayEntity.setOrderId(orderEntity.getOrderId());
//                    orderPayEntity.setIsActive(true);
//                    orderPayEntity.setCreator(user);
//                    orderPayEntity.setInsertTime(now);
//                    orderPayEntity.setModifier(user);
//                    orderPayEntity.setUpdateTime(now);
//                    orderPayEntity.setStatus(0);
//
//                    orderPayDataAccess.create(orderPayEntity);
//                    switch (orderPayEntity.getPayTypeId()) {
//                        case 1: //cashier
//                            OrderPayCashierEntity orderPayCashierEntity = new OrderPayCashierEntity();
//
//                            orderPayCashierEntity.setOrderId(orderEntity.getOrderId());
//                            orderPayCashierEntity.setOrderPayId(orderPayEntity.getOrderPayId());
//                            orderPayCashierEntity.setIsActive(true);
//                            orderPayCashierEntity.setCreator(user);
//                            orderPayCashierEntity.setInsertTime(now);
//                            orderPayCashierEntity.setModifier(user);
//                            orderPayCashierEntity.setUpdateTime(now);
//                            orderPayCashierEntity.setDescription(orderPayEntity.getDescription());
//                            orderPayCashierEntity.setAccount(orderPayEntity.getExtraInfos().get("AccountId"));
//
//                            orderPayCashierDataAccess.create(orderPayCashierEntity);
//                            break;
//                        case 2: //point
//                            OrderPayPointEntity orderPayPointEntity = new OrderPayPointEntity();
//
//                            orderPayPointEntity.setOrderId(orderEntity.getOrderId());
//                            orderPayPointEntity.setOrderPayId(orderPayEntity.getOrderPayId());
//                            orderPayPointEntity.setIsActive(true);
//                            orderPayPointEntity.setCreator(user);
//                            orderPayPointEntity.setInsertTime(now);
//                            orderPayPointEntity.setModifier(user);
//                            orderPayPointEntity.setUpdateTime(now);
//                            orderPayPointEntity.setDescription(orderPayEntity.getDescription());
//                            orderPayPointEntity.setAccountId(Long.parseLong(orderPayEntity.getExtraInfos().get("AccountId")));
//
//                            orderPayPointDataAccess.create(orderPayPointEntity);
//                            break;
//                        case 3: //coupon
//                            OrderPayCouponEntity orderPayCouponEntity = new OrderPayCouponEntity();
//
//                            orderPayCouponEntity.setOrderId(orderEntity.getOrderId());
//                            orderPayCouponEntity.setOrderPayId(orderPayEntity.getOrderPayId());
//                            orderPayCouponEntity.setIsActive(true);
//                            orderPayCouponEntity.setCreator(user);
//                            orderPayCouponEntity.setInsertTime(now);
//                            orderPayCouponEntity.setModifier(user);
//                            orderPayCouponEntity.setUpdateTime(now);
//                            orderPayCouponEntity.setDescription(orderPayEntity.getDescription());
//                            orderPayCouponEntity.setAccountId(Long.parseLong(orderPayEntity.getExtraInfos().get("AccountId")));
//
//                            orderPayCouponDataAccess.create(orderPayCouponEntity);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//                //endregion
//            }
//        });
//        return orderEntity.getOrderId();
//    }
//
//    /**
//     * 提交订单
//     *
//     * @param orderId 订单编号
//     * @return 处理状态
//     */
//    public void submitOrder(Long orderId) {
//        Order order = this.loadOrder(orderId);
//        order.sendEvent("submit", null);
//    }
//
//    public void pushOrder(Long orderId) {
//        Order order = this.loadOrder(orderId);
//        order.process();
//    }
//
//    //endregion
//}
