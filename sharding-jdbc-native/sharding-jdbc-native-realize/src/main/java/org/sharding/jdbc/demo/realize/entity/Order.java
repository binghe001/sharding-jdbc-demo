package org.sharding.jdbc.demo.realize.entity;

import java.io.Serializable;

/**
 * 订单表
 * @author liuyazhuang
 *
 */
public class Order implements Serializable{
	
	private static final long serialVersionUID = 7241033340504021540L;
	private Integer orderId = 0;
	private Integer userId = 0;
	
	public Order() {
		super();
	}
	public Order(Integer orderId, Integer userId) {
		super();
		this.orderId = orderId;
		this.userId = userId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", userId=" + userId + "]";
	}
	
}
