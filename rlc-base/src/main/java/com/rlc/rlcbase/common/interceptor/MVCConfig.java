package com.rlc.rlcbase.common.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO
 * ClassName:MVCConfig  <br/>
 * Function: mvc配置类中添加拦截器 ADD FUNCTION. <br/>
 * Reason:	 mvc配置类中添加拦截器 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/10/14 10:29
 * @since JDK 1.8
 */
@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Bean
    public TokenInterceptor getTokenInterceptor(){
        return new TokenInterceptor();
    }
    //添加自定义拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenInterceptor())
                .addPathPatterns("/v1/eqpDetail")
                //配置不拦截的路径
                .excludePathPatterns("/v1/auth**");
    }
}