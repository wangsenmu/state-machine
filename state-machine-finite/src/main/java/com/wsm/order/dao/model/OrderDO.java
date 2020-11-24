package com.wsm.order.dao.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 */
public class OrderDO {
    /**
     * 编号
     */
    private Long orderId;

    /**
     * 父订单
     */
    private Long parentOrderId;

    /**
     * 说明
     * $name:true
     */
    private String description;

    /**
     * 订单版本
     * $ref:order_version
     */
    private Integer orderVersionId;

    /**
     * 订单工厂
     */
    private String orderFactory;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单积分计算金额
     */
    private BigDecimal pointAmount;

    /**
     * 订单可退金额
     */
    private BigDecimal refundAmount;

    /**
     * 订单来源
     * $ref:source
     */
    private Integer sourceId;

    /**
     * 订单业务
     * $ref:business
     */
    private Integer businessId;

    /**
     * 业务标识
     */
    private String businessNo;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 下单用户
     */
    private Long userId;

    /**
     * 付款用户
     */
    private Long chargeUserId;

    /**
     * 过期时间
     */
    private Date overdueTime;

    /**
     * 当前步骤
     */
    private Long currentOrderStateId;

    /**
     * 当前步骤名称
     */
    private String currentOrderStateName;

    /**
     * 最近处理时间
     */
    private Date lastProcessTime;

    /**
     * 处理间隔
     */
    private Integer processInterval;

    /**
     * 是否锁定
     */
    private Integer locked;

    /**
     * 创建人
     * $sys:true
     */
    private String creator;

    /**
     * 修改人
     * $sys:true
     */
    private String modifier;

    /**
     * 是否激活
     * $sys:true
     */
    private Boolean isActive;

    /**
     * 创建时间
     * $sys:true
     */
    private Date insertTime;

    /**
     * 修改时间
     * $sys:true
     */
    private Date updateTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(Long parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderVersionId() {
        return orderVersionId;
    }

    public void setOrderVersionId(Integer orderVersionId) {
        this.orderVersionId = orderVersionId;
    }

    public String getOrderFactory() {
        return orderFactory;
    }

    public void setOrderFactory(String orderFactory) {
        this.orderFactory = orderFactory;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(BigDecimal pointAmount) {
        this.pointAmount = pointAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChargeUserId() {
        return chargeUserId;
    }

    public void setChargeUserId(Long chargeUserId) {
        this.chargeUserId = chargeUserId;
    }

    public Date getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(Date overdueTime) {
        this.overdueTime = overdueTime;
    }

    public Long getCurrentOrderStateId() {
        return currentOrderStateId;
    }

    public void setCurrentOrderStateId(Long currentOrderStateId) {
        this.currentOrderStateId = currentOrderStateId;
    }

    public String getCurrentOrderStateName() {
        return currentOrderStateName;
    }

    public void setCurrentOrderStateName(String currentOrderStateName) {
        this.currentOrderStateName = currentOrderStateName;
    }

    public Date getLastProcessTime() {
        return lastProcessTime;
    }

    public void setLastProcessTime(Date lastProcessTime) {
        this.lastProcessTime = lastProcessTime;
    }

    public Integer getProcessInterval() {
        return processInterval;
    }

    public void setProcessInterval(Integer processInterval) {
        this.processInterval = processInterval;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}