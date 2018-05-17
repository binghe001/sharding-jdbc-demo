package io.sharding.jdbc.spring.wr.dao;

import java.util.List;

import io.sharding.jdbc.spring.wr.entity.Order;
import io.sharding.jdbc.spring.wr.entity.OrderItem;

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
