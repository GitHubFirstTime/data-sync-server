/**
 * Copyright (c) 2005-2011 springside.org.cn
 * 
 * $Id: PropertiesLoader.java 1690 2012-02-22 13:42:00Z calvinxiu $
 */
package com.rlc.rlcbase.common;

import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Properties文件载入工具类. 可载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的值，但以System的Property优先.
 * @author calvin
 * @version 2013-05-15
 */

public class PropertiesLoader {
//
//	private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
//
//	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
//
//	private final Properties properties;
//
//	public PropertiesLoader(String... resourcesPaths) {
//		properties = loadProperties(resourcesPaths);
//	}
//
//	public Properties getProperties() {
//		return properties;
//	}
//
//	/**
//	 * 取出Property，但以System的Property优先,取不到返回空字符串.
//	 */
//	private String getValue(String key) {
//		String systemProperty = System.getProperty(key);
//		if (systemProperty != null) {
//			return systemProperty;
//		}
//		if (properties.containsKey(key)) {
//	        return properties.getProperty(key);
//	    }
//	    return "";
//	}
//
//	/**
//	 * 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常.
//	 */
//	public String getProperty(String key) {
//		String value = getValue(key);
//		if (value == null) {
//			throw new NoSuchElementException();
//		}
//		return value;
//	}
//
//	/**
//	 * 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
//	 */
//	public String getProperty(String key, String defaultValue) {
//		String value = getValue(key);
//		return value != null ? value : defaultValue;
//	}
//
//	/**
//	 * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
//	 */
//	public Integer getInteger(String key) {
//		String value = getValue(key);
//		if (value == null) {
//			throw new NoSuchElementException();
//		}
//		return Integer.valueOf(value);
//	}
//
//	/**
//	 * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
//	 */
//	public Integer getInteger(String key, Integer defaultValue) {
//		String value = getValue(key);
//		return value != null ? Integer.valueOf(value) : defaultValue;
//	}
//
//	/**
//	 * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
//	 */
//	public Double getDouble(String key) {
//		String value = getValue(key);
//		if (value == null) {
//			throw new NoSuchElementException();
//		}
//		return Double.valueOf(value);
//	}
//
//	/**
//	 * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
//	 */
//	public Double getDouble(String key, Integer defaultValue) {
//		String value = getValue(key);
//		return value != null ? Double.valueOf(value) : defaultValue;
//	}
//
//	/**
//	 * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
//	 */
//	public Boolean getBoolean(String key) {
//		String value = getValue(key);
//		if (value == null) {
//			throw new NoSuchElementException();
//		}
//		return Boolean.valueOf(value);
//	}
//
//	/**
//	 * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
//	 */
//	public Boolean getBoolean(String key, boolean defaultValue) {
//		String value = getValue(key);
//		return value != null ? Boolean.valueOf(value) : defaultValue;
//	}
//
//	/**
//	 * 载入多个文件, 文件路径使用Spring Resource格式.
//	 */
//	private Properties loadProperties(String... resourcesPaths) {
//		Properties props = new Properties();
//
//		for (String location : resourcesPaths) {
//
////			logger.debug("Loading properties file from:" + location);
//
//			// InputStream is = null;
//			InputStreamReader inputStreamReader = null;
//			try {
//				Properties propsTemp = new Properties();
//				InputStream isTemp = null;
//				Resource resourceTemp = resourceLoader.getResource("/environment.properties");
//				isTemp = resourceTemp.getInputStream();
//				propsTemp.load(isTemp);
//				String environment = propsTemp.getProperty("environment").trim();
//
//				Resource resource = null;
//				if (location.contains("fmbReport.properties")) {
//					resource = resourceLoader.getResource(environment + "/" + location);
//				} else {
//					resource = resourceLoader.getResource(location);
//				}
////			    is = resource.getInputStream();
//				inputStreamReader = new InputStreamReader(resource.getInputStream(), "UTF-8");
//				props.load(inputStreamReader);
//			} catch (IOException ex) {
//				logger.info("Could not load properties from path:" + location + ", " + ex.getMessage());
//			} finally {
//				IOUtils.closeQuietly(inputStreamReader);
//			}
//		}
//		return props;
//	}

	public static Properties props;

	static {
		try {
			readPropertiesFile("/cmdbServer.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties readPropertiesFile(String filePath) throws FileNotFoundException, IOException {
		try {
			ClassPathResource classPathResource = new ClassPathResource(filePath);
			InputStream inputStream = classPathResource.getInputStream();
			props = new Properties();
			props.load(new InputStreamReader(inputStream, "UTF-8"));
			return props;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getProperty(String key) {
		String methodName = props.getProperty(key);
		return methodName;
	}

}
