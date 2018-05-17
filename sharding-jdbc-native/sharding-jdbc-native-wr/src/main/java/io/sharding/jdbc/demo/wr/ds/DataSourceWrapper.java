package io.sharding.jdbc.demo.wr.ds;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import io.sharding.jdbc.demo.utils.LoadProps;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;

/**
 * 支持读写分离的数据源
 * @author liuyazhuang
 *
 */
public class DataSourceWrapper {
	
	/**
	 * 获取读写分离数据源
	 * @return
	 */
	public static DataSource getWRDataDource() throws Exception {
		//配置写数据源
		BasicDataSource masterDataSource = new BasicDataSource();
		masterDataSource.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		masterDataSource.setUrl(LoadProps.getValue(LoadProps.JDBC_DB1_URL));;
		masterDataSource.setUsername(LoadProps.getValue(LoadProps.JDBC_DB1_USERNAME));
		masterDataSource.setPassword(LoadProps.getValue(LoadProps.JDBC_DB1_PASSWORD));
		
		//配置读数据源
		BasicDataSource slaveDataSource = new BasicDataSource();
		slaveDataSource.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		slaveDataSource.setUrl(LoadProps.getValue(LoadProps.JDBC_DB2_URL));
		slaveDataSource.setUsername(LoadProps.getValue(LoadProps.JDBC_DB2_USERNAME));
		slaveDataSource.setPassword(LoadProps.getValue(LoadProps.JDBC_DB2_PASSWORD));
		
		//配置读写数据源
		Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
		dataSourceMap.put("masterDataSource", masterDataSource);
		dataSourceMap.put("slaveDataSource", slaveDataSource);
		
		//配置读写分离规则
		MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
		masterSlaveRuleConfig.setName("ms_ds");
		masterSlaveRuleConfig.setMasterDataSourceName("masterDataSource");
		masterSlaveRuleConfig.getSlaveDataSourceNames().add("slaveDataSource");
		
		//创建读写分离数据源
		DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfig, new HashMap<String, Object>());
		//返回数据源
		return dataSource;
	}
	
	/**
	 * 获取分库分表+读写分离的数据源
	 * @return
	 * @throws Exception
	 */
	public static DataSource getShardWRDataSource() throws Exception {
		//获取数据源Map配置
		Map<String, DataSource> dataSourceMap = getShardWRDataSourceMap();
		
		//配置第一组数据源读写分离
		MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration();
		masterSlaveRuleConfig1.setName("ds_0");
		masterSlaveRuleConfig1.setMasterDataSourceName("masterDataSource1");
		masterSlaveRuleConfig1.getSlaveDataSourceNames().add("slaveDataSource11");
		masterSlaveRuleConfig1.getSlaveDataSourceNames().add("slaveDataSource12");
		
		//配置第二组数据源读写分离
		MasterSlaveRuleConfiguration masterSlaveRuleConfig2 = new MasterSlaveRuleConfiguration();
		masterSlaveRuleConfig2.setName("ds_1");
		masterSlaveRuleConfig2.setMasterDataSourceName("masterDataSource2");
		masterSlaveRuleConfig2.getSlaveDataSourceNames().add("slaveDataSource21");
		masterSlaveRuleConfig2.getSlaveDataSourceNames().add("slaveDataSource22");
		
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
		
		
		//配置分库分表规则
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getMasterSlaveRuleConfigs().add(masterSlaveRuleConfig1);
		shardingRuleConfig.getMasterSlaveRuleConfigs().add(masterSlaveRuleConfig2);
		shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
		shardingRuleConfig.getTableRuleConfigs().add(orderItemTableRuleConfig);
		
		//创建数据源
		DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new HashMap<String, Object>(), new Properties());
		return dataSource;
	}
	
	/**
	 * 获取分库分表+读写分离数据源Map
	 * @return
	 */
	private static Map<String, DataSource> getShardWRDataSourceMap(){
		//配置主数据源1
		BasicDataSource masterDataSource1 = new BasicDataSource();
		masterDataSource1.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		masterDataSource1.setUrl(LoadProps.getValue(LoadProps.JDBC_DB1_URL));;
		masterDataSource1.setUsername(LoadProps.getValue(LoadProps.JDBC_DB1_USERNAME));
		masterDataSource1.setPassword(LoadProps.getValue(LoadProps.JDBC_DB1_PASSWORD));
		
		//配置主数据源2
		BasicDataSource masterDataSource2 = new BasicDataSource();
		masterDataSource2.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		masterDataSource2.setUrl(LoadProps.getValue(LoadProps.JDBC_DB4_URL));
		masterDataSource2.setUsername(LoadProps.getValue(LoadProps.JDBC_DB4_USERNAME));
		masterDataSource2.setPassword(LoadProps.getValue(LoadProps.JDBC_DB4_PASSWORD));
		
		//配置主数据源1上的从数据源1
		BasicDataSource slaveDataSource11 = new BasicDataSource();
		slaveDataSource11.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		slaveDataSource11.setUrl(LoadProps.getValue(LoadProps.JDBC_DB2_URL));;
		slaveDataSource11.setUsername(LoadProps.getValue(LoadProps.JDBC_DB2_USERNAME));
		slaveDataSource11.setPassword(LoadProps.getValue(LoadProps.JDBC_DB2_PASSWORD));
		
		//配置主数据源1上的从数据源2
		BasicDataSource slaveDataSource12 = new BasicDataSource();
		slaveDataSource12.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		slaveDataSource12.setUrl(LoadProps.getValue(LoadProps.JDBC_DB3_URL));
		slaveDataSource12.setUsername(LoadProps.getValue(LoadProps.JDBC_DB3_USERNAME));
		slaveDataSource12.setPassword(LoadProps.getValue(LoadProps.JDBC_DB3_PASSWORD));
		
		//配置主数据源2上的从数据源1
		BasicDataSource slaveDataSource21 = new BasicDataSource();
		slaveDataSource21.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		slaveDataSource21.setUrl(LoadProps.getValue(LoadProps.JDBC_DB5_URL));;
		slaveDataSource21.setUsername(LoadProps.getValue(LoadProps.JDBC_DB5_USERNAME));
		slaveDataSource21.setPassword(LoadProps.getValue(LoadProps.JDBC_DB5_PASSWORD));
		
		//配置主数据源2上的从数据源2
		BasicDataSource slaveDataSource22 = new BasicDataSource();
		slaveDataSource22.setDriverClassName(LoadProps.getValue(LoadProps.JDBC_DRIVER));
		slaveDataSource22.setUrl(LoadProps.getValue(LoadProps.JDBC_DB6_URL));;
		slaveDataSource22.setUsername(LoadProps.getValue(LoadProps.JDBC_DB6_USERNAME));
		slaveDataSource22.setPassword(LoadProps.getValue(LoadProps.JDBC_DB6_PASSWORD));
		
		//配置数据源
		Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
		dataSourceMap.put("masterDataSource1", masterDataSource1);
		dataSourceMap.put("slaveDataSource11", slaveDataSource11);
		dataSourceMap.put("slaveDataSource12", slaveDataSource12);
		
		dataSourceMap.put("masterDataSource2", masterDataSource2);
		dataSourceMap.put("slaveDataSource21", slaveDataSource21);
		dataSourceMap.put("slaveDataSource22", slaveDataSource22);
		
		//返回dataSourceMap
		return dataSourceMap;
	}
}
