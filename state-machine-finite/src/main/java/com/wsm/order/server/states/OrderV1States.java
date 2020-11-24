package com.wsm.order.server.states;

/**
 * 订单步骤
 */
public class OrderV1States {

    /**
     * 已创建
     * <p>
     * 状态变迁
     * 1.侦听提交事件，等待提交订单，之后跳转至已提交
     */
    public final static String Created = "已创建";

    /**
     * 已提交
     * <p>
     * 状态变迁
     * 1.如果锁定成功，则转至支付中
     * 2.如果锁定失败，则转至锁定失败
     */
    public final static String Submitted = "已提交";

    /**
     * 支付中
     * <p>
     * 1.如果支付成功，则转至已支付
     * 2.如果支付失败，则转至失败
     */
    public final static String InPay = "支付中";

    /**
     * 失败
     *
     * 为终态
     */
    public final static String Failed = "失败";

    /**
     * 已支付
     *
     * 为终态
     */
    public final static String Payed = "已支付";

}
