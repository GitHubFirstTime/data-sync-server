package com.rlc.rlccmdbapi.modules.datasource.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.mysql.cj.jdbc.MysqlXADataSource;
import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.persistence.interceptor.PaginationInterceptor;
import com.rlc.rlcbase.persistence.typeHandler.ConvertBlobTypeHandler;
import com.rlc.rlccmdbapi.modules.datasource.prop.DBConfig_CMDB;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.sql.SQLException;
import java.util.Properties;

//@Configuration
//@MapperScan(basePackages = {"com.rlc.rlccmdbapi.modules.cmdb.dao"}, sqlSessionFactoryRef = "cmdbDBSqlSessionFactory")
public class DataSourceConfig_cmdbDB {

//    @Primary // 表示这个数据源是默认数据源, 这个注解必须要加，因为不加的话spring将分不清楚那个为主数据源（默认数据源）
//    @Bean("cmdbDataSource")
//    public DataSource getCmdbDataSource(DBConfig_CMDB dbConfigCmdb) throws SQLException {
////        DataSource d = DataSourceBuilder.create().build();
////        return DataSourceBuilder.create().build();
//        //Atomikos统一管理分布式事务
//        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
//
//        //mysql连接基础信息
//        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
//        mysqlXaDataSource.setUrl(dbConfigCmdb.getUrl());
//        mysqlXaDataSource.setPassword(dbConfigCmdb.getPassword());
//        mysqlXaDataSource.setUser(dbConfigCmdb.getUsername());
//        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
//
//        xaDataSource.setXaDataSource(mysqlXaDataSource);
//        //连接池配置信息
//        xaDataSource.setUniqueResourceName("cmdbDataSource");
//        xaDataSource.setMinPoolSize(dbConfigCmdb.getMinPoolSize());
//        xaDataSource.setMaxPoolSize(dbConfigCmdb.getMaxPoolSize());
//        xaDataSource.setMaxLifetime(dbConfigCmdb.getMaxLifetime());
//        xaDataSource.setBorrowConnectionTimeout(dbConfigCmdb.getBorrowConnectionTimeout());
//        xaDataSource.setLoginTimeout(dbConfigCmdb.getLoginTimeout());
//        xaDataSource.setMaintenanceInterval(dbConfigCmdb.getMaintenanceInterval());
//        xaDataSource.setMaxIdleTime(dbConfigCmdb.getMaxIdleTime());
//        xaDataSource.setTestQuery(dbConfigCmdb.getTestQuery());
//        return xaDataSource;
//    }
//    @Primary // 表示这个数据源是默认数据源, 这个注解必须要加，因为不加的话spring将分不清楚那个为主数据源（默认数据源）
//    @Bean("cmdbDataSource")
//    public DataSource getCmdbDataSource(DBConfig_CMDB dbConfigCmdb) throws SQLException {
//        //Atomikos统一管理分布式事务
//        Properties pro = build(dbConfigCmdb);
//        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
//        atomikosDataSourceBean.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
//        atomikosDataSourceBean.setXaProperties(pro);
//
//        atomikosDataSourceBean.setMinPoolSize(Integer.parseInt(pro.getProperty("minIdle")));
//        atomikosDataSourceBean.setMaxPoolSize(Integer.parseInt(pro.getProperty("maxActive")));
//        atomikosDataSourceBean.setBorrowConnectionTimeout(Integer.parseInt(pro.getProperty("maxWait")));
//        atomikosDataSourceBean.setUniqueResourceName("cmdbDataSource");
//        return atomikosDataSourceBean;
//    }
    private Properties build(DBConfig_CMDB dbConfigCmdb) {
        Properties prop = new Properties();
        prop.put("username", dbConfigCmdb.getUsername());
        prop.put("password", dbConfigCmdb.getPassword());
        prop.put("url", dbConfigCmdb.getUrl());
        prop.put("driverClassName", dbConfigCmdb.getDriverClassName());
        prop.put("initialSize", String.valueOf(dbConfigCmdb.getInitialSize()));
        prop.put("maxActive", String.valueOf(dbConfigCmdb.getMaxActive()));
        prop.put("minIdle", String.valueOf(dbConfigCmdb.getMinIdle()));
        prop.put("maxWait", String.valueOf(dbConfigCmdb.getMaxWait()));
        prop.put("testOnBorrow", String.valueOf(dbConfigCmdb.getTestOnBorrow()));
        /*prop.put("testQuery", dbConfigCmdb.getTestQuery());*/
        return prop;
    }

//    @Primary
//    @Bean("cmdbDBSqlSessionFactory")
//    public SqlSessionFactory cmdbDBSqlSessionFactory(@Qualifier("cmdbDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        Properties properties = new Properties();
//        //数据库
//        properties.setProperty("jdbc.type", "mysql");
//        paginationInterceptor.setProperties(properties);
//        bean.setPlugins(paginationInterceptor);
//        bean.setTypeAliases(Page.class);
//        bean.setTypeAliasesPackage("com.rlc.rlccmdbapi.modules.cmdb.entity");
//        bean.setTypeHandlers(new ConvertBlobTypeHandler());
//        bean.setDataSource(dataSource);
//        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappings/modules/cmdb/*.xml"));
//        return bean.getObject();
//    }
    /*
     * @methodDesc: 功能描述:(test2 事物管理)
     */
 /*   @Primary
    @Bean(name = "cmdbTransactionManager")
    public DataSourceTransactionManager cmdbTransactionManager(@Qualifier("cmdbDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/
    /*
     * 注意：【使用这个来做总事务 后面的数据源就不用设置事务了】
     * */
 /*   @Bean(name = "transactionManager")
    @Primary
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }*/
//    @Primary
//    @Bean("cmdbSqlSessionTemplate")
//    public SqlSessionTemplate cmdbSqlSessionTemplate(@Qualifier("cmdbDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}