package com.rlc.rlcframework.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import com.rlc.rlcbase.persistence.interceptor.PaginationInterceptor;
import com.rlc.rlcframework.datasource.DynamicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: 数据源管理
 * @date 2021/5/13 15:47
 */
@Configuration
public class DruidConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.cmdbdb")
    public DataSource cmdbDataSource(Environment env)
    {
        DBConfig dbConfig = build(env,"spring.datasource.cmdbdb");
        dbConfig.setDbType("mysql");
        dbConfig.setDataSourceName("cmdbDataSource");
        return getDataSource(dbConfig);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.fmbdb")
//    @ConditionalOnProperty(prefix = "spring.datasource.fmbdb", name = "enabled", havingValue = "true")
    public DataSource fmbDataSource(Environment env)
    {
        DBConfig dbConfig = build(env,"spring.datasource.fmbdb");
        dbConfig.setDbType("oracle");
        dbConfig.setDataSourceName("fmbDataSource");
        return getDataSource(dbConfig);
    }
    protected DataSource getDataSource(DBConfig dbConfig)
    {
        DataSource ds = null;
         String dbType = dbConfig.getDbType();
        if(StringUtils.isBlank(dbType)){
            return ds;
        }
        try {

            switch(dbType){
                case "mysql":
                    ds = getMysqlDataSource(dbConfig);
                    break;
                case "oracle":
                    ds = getOracleDataSource(dbConfig);
                    break;
                case "sqlserver":
                    break;
                default:
                    ds = getMysqlDataSource(dbConfig);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    public DataSource getMysqlDataSource(DBConfig dbConfig) throws SQLException {
        //Atomikos统一管理分布式事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();

        //mysql连接基础信息
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dbConfig.getUrl());
        mysqlXaDataSource.setPassword(dbConfig.getPassword());
        mysqlXaDataSource.setUser(dbConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        xaDataSource.setXaDataSource(mysqlXaDataSource);
        //连接池配置信息
        xaDataSource.setUniqueResourceName(dbConfig.getDataSourceName());
        xaDataSource.setMinPoolSize(dbConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dbConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(dbConfig.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(dbConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(dbConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(dbConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(dbConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(dbConfig.getTestQuery());
        return xaDataSource;
    }
    public DataSource getOracleDataSource(DBConfig dbConfig) throws SQLException {
        //创建xa datasource
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        // 基础连接信息
        druidXADataSource.setUrl(dbConfig.getUrl());
        druidXADataSource.setUsername(dbConfig.getUsername());
        druidXADataSource.setPassword(dbConfig.getPassword());
        druidXADataSource.setDriverClassName(dbConfig.getDriverClassName());
        // 连接池连接信息
        druidXADataSource.setInitialSize(dbConfig.getInitialSize());
        druidXADataSource.setMinIdle(dbConfig.getMinIdle());
        druidXADataSource.setMaxActive(dbConfig.getMaxActive());
        druidXADataSource.setMaxWait(dbConfig.getMaxWait());
        druidXADataSource.setPoolPreparedStatements(true); // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        druidXADataSource.setMaxPoolPreparedStatementPerConnectionSize(50);
        druidXADataSource.setConnectionProperties("oracle.net.CONNECT_TIMEOUT=6000;oracle.jdbc.ReadTimeout=60000");// 对于耗时长的查询sql，会受限于ReadTimeout的控制，单位毫秒
        druidXADataSource.setTestOnBorrow(true); // 申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
        druidXADataSource.setTestWhileIdle(true);// 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        String validationQuery = "select 1 from dual";
        druidXADataSource.setValidationQuery(validationQuery); // 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
        druidXADataSource.setFilters("stat,wall");// 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
        druidXADataSource.setTimeBetweenEvictionRunsMillis(60000); // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidXADataSource.setMinEvictableIdleTimeMillis(180000); // 配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
        druidXADataSource.setKeepAlive(true); // 打开druid.keepAlive之后，当连接池空闲时，池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作，即执行druid.validationQuery指定的查询SQL，一般为select
        // * from
        // dual，只要minEvictableIdleTimeMillis设置的小于防火墙切断连接时间，就可以保证当连接空闲时自动做保活检测，不会被防火墙切断

        druidXADataSource.setRemoveAbandoned(true); // 是否移除泄露的连接/超过时间限制是否回收。
        druidXADataSource.setRemoveAbandonedTimeout(3600); // 泄露连接的定义时间(要超过最大事务的处理时间)；单位为秒。这里配置为1小时
        druidXADataSource.setLogAbandoned(true);  //移除泄露连接发生是是否记录日志

        //2.注册到我们全局事务上
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setMinPoolSize(dbConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dbConfig.getMaxPoolSize());
        xaDataSource.setMaintenanceInterval(dbConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(dbConfig.getMaxIdleTime());
        xaDataSource.setUniqueResourceName(dbConfig.getDataSourceName());
        return xaDataSource;
    }
    protected DBConfig build(Environment env,String prefix)
    {
        DBConfig dbConfig = Binder.get(env)
                .bind(prefix, Bindable.of(DBConfig.class) )
                .get();
        return dbConfig;
    }
    /**
     * 动态数据源配置
     *
     * @param cmdbDataSource   cmdbDataSource
     * @param fmbDataSource fmbDataSource
     * @return DataSource
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DataSource dynamicDataSource(@Qualifier("cmdbDataSource") DataSource cmdbDataSource, @Qualifier("fmbDataSource") DataSource fmbDataSource) {
        DynamicDataSource multipleDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("cmdbDataSource", cmdbDataSource);
        targetDataSources.put("fmbDataSource", fmbDataSource);
//        targetDataSources.put("quartzDataSource", quartzDataSource);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(cmdbDataSource);
        //添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        multipleDataSource.updateTargetDataSource(cmdbDataSource,targetDataSources);
        return multipleDataSource;
    }
}