package org.sharding.jdbc.demo.realize.yaml.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.sharding.jdbc.demo.realize.dao.DBHandlerDao;
import org.sharding.jdbc.demo.realize.entity.Order;
import org.sharding.jdbc.demo.realize.entity.OrderItem;
import org.sharding.jdbc.demo.realize.yaml.datasource.DataDourceWrapper;

/**
 * 增删改查具体实现
 * @author liuyazhuang
 *
 */
public class DBHandlerDaoImpl implements DBHandlerDao {
	private Connection conn;
	
	public DBHandlerDaoImpl() throws Exception{
		super();
		this.conn = DataDourceWrapper.getDataSource().getConnection();
	}

	@Override
	public int saveOrder(Order order) {
		int count = 0;
		try {
			String sql = "insert into t_order(order_id, user_id) values (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, order.getOrderId());
			ps.setInt(2, order.getUserId());
			count = ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


	@Override
	public Order getOrder(Integer orderId) {
		try {
			String sql = "select order_id as orderId, user_id as userId from t_order where order_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			Order order = new Order();
			if(rs.next()) {
				order.setOrderId(rs.getInt("orderId"));
				order.setUserId(rs.getInt("userId"));
			}
			rs.close();
			ps.close();
			return order;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Order> getOrderList() {
		try {
			String sql = "select order_id as orderId, user_id as userId from t_order ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Order> list = new ArrayList<Order>();
			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("orderId"));
				order.setUserId(rs.getInt("userId"));
				list.add(order);
			}
			rs.close();
			ps.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int saveOrderItem(OrderItem orderItem) {
		int count = 0;
	    try {
	    	String sql = "insert into t_order_item(item_id, order_id, user_id) values(?, ?, ?)";
	    	PreparedStatement ps = conn.prepareStatement(sql);
	    	ps.setInt(1, orderItem.getItemId());
	    	ps.setInt(2, orderItem.getOrderId());
	    	ps.setInt(3, orderItem.getUserId());
	    	count = ps.executeUpdate();
	    	ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public OrderItem getOrderItem(Integer orderItemId) {
		try {
			String sql = "select item_id as itemId, order_id as orderId, user_id as userId from t_order_item where item_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderItemId);
			ResultSet rs = ps.executeQuery();
			OrderItem item = new OrderItem();
			if(rs.next()) {
				item.setItemId(rs.getInt("itemId"));
				item.setOrderId(rs.getInt("orderId"));
				item.setUserId(rs.getInt("userId"));
			}
			rs.close();
			ps.close();
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<OrderItem> getOrderItemList() {
		try {
			String sql = "select item_id as itemId, order_id as orderId, user_id as userId from t_order_item ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<OrderItem> list = new ArrayList<OrderItem>();
			while (rs.next()) {
				OrderItem item = new OrderItem();
				item.setItemId(rs.getInt("itemId"));
				item.setOrderId(rs.getInt("orderId"));
				item.setUserId(rs.getInt("userId"));
				list.add(item);
			}
			rs.close();
			ps.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
