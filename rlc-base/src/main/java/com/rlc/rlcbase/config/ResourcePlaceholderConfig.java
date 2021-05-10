package com.rlc.rlcbase.config;

import com.rlc.rlcbase.security.EncryptPropertyPlaceholderConfigurer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * TODO
 * ClassName:ResourcePlaceholderConfig <br/>
 * Function: 接管配置文件解析 ADD FUNCTION. <br/>
 * Reason:	 接管配置文件解析 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/30 9:33
 * @since JDK 1.8
 */
@Configuration
public class ResourcePlaceholderConfig {
    Logger logger =  LogManager.getLogger(ResourcePlaceholderConfig.class);
    String active;
    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        active = Global.getConfig("active.evn");
        PropertySourcesPlaceholderConfigurer config = new EncryptPropertyPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application-"+active+".yml"));
        logger.info("激活配置文件环境:{}",active);
        config.setProperties(yaml.getObject());
        return config;
    }
}