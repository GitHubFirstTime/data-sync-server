package com.rlc.rlcbase.quartz;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/5/7 20:31
 */
@Configuration
public class QuartzDataSourceConfig {
    /**
     * 数据源配置的前缀，必须与application-{profile}.yml中配置的对应数据源的前缀一致
     */

    private static final String QUARTZ_DATASOURCE_PREFIX = "spring.datasource.quartz";

    /**
     * @QuartzDataSource 注解则是配置Quartz独立数据源的配置
     */
    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = QUARTZ_DATASOURCE_PREFIX)
    public DataSource quartzDataSource(){
//        return DataSourceBuilder.create().build();
        return new DruidDataSource();
    }
}
