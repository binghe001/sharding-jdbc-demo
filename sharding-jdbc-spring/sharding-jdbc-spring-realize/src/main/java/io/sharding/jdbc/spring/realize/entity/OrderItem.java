package io.sharding.jdbc.spring.realize.entity;

import java.io.Serializable;

/**
 * 订单详情
 * @author liuyazhuang
 *
 */
public class OrderItem implements Serializable{

	private static final long serialVersionUID = -4951411901460371229L;
	
	private Integer itemId = 0;
	private Integer orderId = 0;
	private Integer userId = 0;
	
	public OrderItem() {
		super();
	}

	public OrderItem(Integer itemId, Integer orderId, Integer userId) {
		super();
		this.itemId = itemId;
		this.orderId = orderId;
		this.userId = userId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
		return "OrderItem [itemId=" + itemId + ", orderId=" + orderId + ", userId=" + userId + "]";
	}
	
}
