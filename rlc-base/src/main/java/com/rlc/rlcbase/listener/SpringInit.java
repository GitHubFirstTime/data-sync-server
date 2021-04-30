/**
 * Project Name:edrp
 * File Name:SpringInit.java
 * Package Name:com.rlc.cmdbServer.common.listener
 * Date:2018年8月6日
 * Copyright (c) 2018, www.xxx.com All Rights Reserved.
 *
*/

package com.rlc.rlcbase.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

/**
 * ClassName:SpringInit 
 * Description:	 WebApplicationContext 获取工具
 * Date:     2018年8月6日 
 * @author   RLC_ZYC
 * @version  1.0
 * @since    JDK 1.8
 * @see 	 
 */
public class SpringInit extends ContextLoaderListener{
	private static WebApplicationContext springContext;  
	public SpringInit() {
		super();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		springContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	
	}
	public static ApplicationContext getApplicationContext() {     
		if (null == springContext) {
			springContext = ContextLoader.getCurrentWebApplicationContext();    
		}
        return springContext;    
	}
	
	@SuppressWarnings("hiding")
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	public static Object getBean(String className) {
		return getApplicationContext().getBean(className);
	}
}

