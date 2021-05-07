package com.rlc.rlccmdbapi.modules.datasource.prop;

import com.rlc.rlcbase.config.YmlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/4/23 21:42
 */
//@PropertySource(value = "classpath:/application-${spring.profiles.active}.yml",encoding = "utf-8",factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "spring.datasource.ryvdb")
@Data
public class DBConfig_RYVDB {
    private String url;
    private String username;
    private String password;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
}
