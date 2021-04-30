package com.rlc.rlccmdbapi.modules.datasource.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/4/23 21:42
 */
//@Component
@ConfigurationProperties(prefix = "spring.datasource.cmdbdb")
@Data
public class DBConfig_CMDB {
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
