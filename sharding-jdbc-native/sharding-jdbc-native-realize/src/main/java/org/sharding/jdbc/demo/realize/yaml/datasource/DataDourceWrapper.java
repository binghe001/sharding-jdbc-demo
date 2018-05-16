package org.sharding.jdbc.demo.realize.yaml.datasource;

import java.io.File;

import javax.sql.DataSource;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;

/**
 * 通过读取yaml文件来配置DataSource
 * @author liuyazhuang
 *
 */
public class DataDourceWrapper {
	
	/**
	 * 根据yaml文件获取数据源
	 * @return：数据源
	 * @throws Exception
	 */
	public static DataSource getDataSource() throws Exception{
		File file = new File(DataDourceWrapper.class.getClassLoader().getResource("yaml/sharding.yaml").getPath());
		DataSource dataSource = ShardingDataSourceFactory.createDataSource(file);
		return dataSource;
	}
}
