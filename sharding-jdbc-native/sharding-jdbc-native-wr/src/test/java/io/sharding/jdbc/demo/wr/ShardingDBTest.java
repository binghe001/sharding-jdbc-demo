package io.sharding.jdbc.demo.wr;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.sharding.jdbc.demo.dao.DBHandlerDao;
import io.sharding.jdbc.demo.dao.impl.DBHandlerDaoImpl;
import io.sharding.jdbc.demo.entity.Order;
import io.sharding.jdbc.demo.entity.OrderItem;

/**
 * 测试Sharding-jdbc的分库分表+读写分离能力
 * @author liuyazhuang
 *
 */
public class ShardingDBTest {
	private DBHandlerDao handlerDao;
	
	@Before
	public void init() throws Exception{
		handlerDao = new DBHandlerDaoImpl();
	}
	
	@Test
	public void testSaveOrder() throws Exception{
		Order order = new Order(1, 1);
		System.out.println(handlerDao.saveOrder(order));
		
	}
	@Test
	public void testGetOrder() throws Exception{
		System.out.println(handlerDao.getOrder(1));
	}
	@Test
	public void testGetOrderList() throws Exception{
		List<Order> orderList = handlerDao.getOrderList();
		for(Order order : orderList) {
			System.out.println(order.toString());
		}
	}
	@Test
	public void testSaveOrderItem() throws Exception{
		OrderItem item = new OrderItem(1, 1, 1);
		System.out.println(handlerDao.saveOrderItem(item));
	}
	@Test
	public void testGetOrderItem() throws Exception{
		System.out.println(handlerDao.getOrderItem(1));
	}
	@Test
	public void testGetOrderItemList() throws Exception{
		List<OrderItem> orderItemList = handlerDao.getOrderItemList();
		for(OrderItem item : orderItemList) {
			System.out.println(item.toString());
		}
	}
}
