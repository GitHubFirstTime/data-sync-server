package com.rlc.rlcfmbapi.datasource.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/4/23 21:42
 */
//@PropertySource(value = "classpath:/application-${spring.profiles.active}.yml",encoding = "utf-8",factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "spring.datasource.fmbdb")
@Data
public class DBConfig_FMB {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    //以下为数据源连接的拓展配置属性包含连接池等信息
    private int initialSize;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int minIdle;
    private int maxIdleTime;
    private int maxActive;
    private int maxWait;
}
