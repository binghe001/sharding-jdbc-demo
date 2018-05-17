package io.sharding.jdbc.spring.wr;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.sharding.jdbc.spring.wr.dao.DBHandlerDao;
import io.sharding.jdbc.spring.wr.entity.Order;
import io.sharding.jdbc.spring.wr.entity.OrderItem;

/**
 * 测试
 * @author liuyazhuang
 *
 */
public class ShardingDBTest {
	
	private DBHandlerDao handlerDao;
	
	@Before
	public void init(){
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-context.xml");
		handlerDao = (DBHandlerDao) context.getBean("dbHandler");
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
