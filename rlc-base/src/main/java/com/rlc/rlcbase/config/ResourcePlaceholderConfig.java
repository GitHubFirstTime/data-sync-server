package com.rlc.rlcbase.config;

import com.rlc.rlcbase.security.EncryptPropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        PropertySourcesPlaceholderConfigurer config = new EncryptPropertyPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        config.setProperties(yaml.getObject());
        return config;
    }
}