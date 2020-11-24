package com.wsm.order.dao.model;

import java.util.Date;

/**
 * 订单步骤
 */
public class OrderStateDO {
    /**
     * 编号
     */
    private Long orderStateId;

    /**
     * 订单
     */
    private Long orderId;

    /**
     * 名称
     * $name:true
     */
    private String name;

    /**
     * 上一步骤
     */
    private Long prevOrderStateId;

    /**
     * 过期时间
     */
    private Date overdueTime;

    /**
     * 最近处理时间
     */
    private Date lastProcessTime;

    /**
     * 处理间隔
     */
    private Integer processInterval;

    /**
     * 状态
     */
    private Integer status;

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

    public Long getOrderStateId() {
        return orderStateId;
    }

    public void setOrderStateId(Long orderStateId) {
        this.orderStateId = orderStateId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrevOrderStateId() {
        return prevOrderStateId;
    }

    public void setPrevOrderStateId(Long prevOrderStateId) {
        this.prevOrderStateId = prevOrderStateId;
    }

    public Date getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(Date overdueTime) {
        this.overdueTime = overdueTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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