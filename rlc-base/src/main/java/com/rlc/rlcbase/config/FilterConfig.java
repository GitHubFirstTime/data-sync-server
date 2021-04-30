package com.rlc.rlcbase.config;

import com.rlc.rlcbase.filter.HttpServletRequestReplacedFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 * ClassName:FilterConfig <br/>
 * Function: 过滤器配置类 ADD FUNCTION. <br/>
 * Reason:	 过滤器配置类 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/10/14 16:49
 * @since JDK 1.8
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServletRequestReplacedFilter());
        registration.addUrlPatterns("/v1/eqpDetail");
        registration.setName("httpServletRequestReplacedFilter");
        registration.setOrder(1);
        return registration;
    }
}