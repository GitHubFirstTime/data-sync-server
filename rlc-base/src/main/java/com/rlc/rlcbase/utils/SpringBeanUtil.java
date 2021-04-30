package com.rlc.rlcbase.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * TODO
 * ClassName:SpringBeanUtil <br/>
 * Function: bean注入工具 ADD FUNCTION. <br/>
 * Reason:	 bean注入工具 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/14 15:22
 * @since JDK 1.8
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    public static Object getBeanByName(String beanName) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }
}