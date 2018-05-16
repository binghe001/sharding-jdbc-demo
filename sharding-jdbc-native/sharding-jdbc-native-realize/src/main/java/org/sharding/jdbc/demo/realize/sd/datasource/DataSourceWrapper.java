package org.sharding.jdbc.demo.realize.sd.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.sharding.jdbc.demo.realize.utils.LoadProps;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;

/**
 * 获取读写分离的数据源包装类
 * @author liuyazhuang
 *
 */
public class DataSourceWrapper {
	
	/**
	 * 整合sharding-jdbc获取数据源
	 * @return
	 * @throws Exception
	 */
	public static DataSource getDataSource() throws Exception{
		//存储真实数据源
		Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
		
		//配置第一个数据源
		BasicDataSource dataSource1 = new BasicDataSource();
		dataSource1.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		dataSource1.setUrl(LoadProps.getValue(LoadProps.JDBC_DB1_URL));
		dataSource1.setUsername(LoadProps.getValue(LoadProps.JDBC_DB1_USERNAME));
		dataSource1.setPassword(LoadProps.getValue(LoadProps.JDBC_DB1_PASSWORD));
		dataSourceMap.put("ds_0", dataSource1);
		
		//配置第二个数据源
		BasicDataSource dataSource2 = new BasicDataSource();
		dataSource2.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		dataSource2.setUrl(LoadProps.getValue(LoadProps.JDBC_DB2_URL));
		dataSource2.setUsername(LoadProps.getValue(LoadProps.JDBC_DB2_USERNAME));
		dataSource2.setPassword(LoadProps.getValue(LoadProps.JDBC_DB2_PASSWORD));
		dataSourceMap.put("ds_1", dataSource2);
		
		//-----------------------配置Order表规则----------------------
		//配置Order表规则
		TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
		orderTableRuleConfig.setLogicTable("t_order");
		orderTableRuleConfig.setActualDataNodes("ds_${0..1}.t_order_${0..1}");
		
		//配置分库策略
		orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));
		//配置分表策略
		orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));
		
		//--------------------配置OrderItem表规则------------------------
		//配置OrderItem表规则
		TableRuleConfiguration orderItemTableRuleConfig = new TableRuleConfiguration();
		orderItemTableRuleConfig.setLogicTable("t_order_item");
		orderItemTableRuleConfig.setActualDataNodes("ds_${0..1}.t_order_item_${0..1}");
		
		//配置分库策略
		orderItemTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));
		orderItemTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_item_${order_id % 2}"));
		
		//配置分片规则
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
		shardingRuleConfig.getTableRuleConfigs().add(orderItemTableRuleConfig);
		
		//获取数据源对象
		DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap<>(), new Properties());
		
		//返回数据源对象
		return dataSource;
	}
}
