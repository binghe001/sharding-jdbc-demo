package org.sharding.jdbc.demo.realize.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 加载配置文件
 * @author liuyazhuang
 *
 */
public class LoadProps extends BaseProps{
	
	
	
	private static Properties properties =  null;
	static {
		try {
			InputStream in = LoadProps.class.getClassLoader().getResourceAsStream("properties/jdbc.properties");
			properties = new Properties();
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key) {
		return properties.getProperty(key, "");
	}
	
	public static void main(String[] args) {
		System.out.println(getValue(JDBC_DB2_URL));
	}
}
