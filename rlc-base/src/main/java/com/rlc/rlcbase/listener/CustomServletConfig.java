package com.rlc.rlcbase.listener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.EventListener;

/**
 * TODO
 * ClassName:CustomServletConfig <br/>
 * Function: Configuration注册该配置类 ADD FUNCTION. <br/>
 * Reason:	 Configuration注册该配置类 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/14 15:16
 * @since JDK 1.8
 */
public class CustomServletConfig {
    // 注册Listener
    @Bean
    public ServletListenerRegistrationBean myListener(){
        ServletListenerRegistrationBean<EventListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new SpringInit());
        return servletListenerRegistrationBean;
    }
}