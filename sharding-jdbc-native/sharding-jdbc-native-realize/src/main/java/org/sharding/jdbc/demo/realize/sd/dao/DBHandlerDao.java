package org.sharding.jdbc.demo.realize.sd.dao;

import java.util.List;

import org.sharding.jdbc.demo.realize.entity.Order;
import org.sharding.jdbc.demo.realize.entity.OrderItem;

/**
 * 实现数据增删改查接口
 * @author liuyazhuang
 *
 */
public interface DBHandlerDao {
	
	int saveOrder(Order order);
	Order getOrder(Integer orderId);
	List<Order> getOrderList();
	
	int saveOrderItem(OrderItem orderItem);
	OrderItem getOrderItem(Integer orderItemId);
	List<OrderItem> getOrderItemList();
}
