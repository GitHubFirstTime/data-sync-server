package com.rlc.rlcframework.config;

import lombok.Data;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/5/13 16:37
 */
@Data
public class DBConfig {
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
    private Boolean testOnBorrow = false;
    private String testQuery = "select 1";
    //自定义属性
    private String dataSourceName;////数据源名称
    private String dbType;//数据库类型
}
