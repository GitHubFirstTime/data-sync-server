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

@Configuration
@MapperScan(basePackages = {"com.rlc.rlccmdbapi.modules.cmdb.dao"}, sqlSessionFactoryRef = "cmdbDBSqlSessionFactory")
public class DataSourceConfig_cmdbDB {

    @Primary // 表示这个数据源是默认数据源, 这个注解必须要加，因为不加的话spring将分不清楚那个为主数据源（默认数据源）
    @Bean("cmdbDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.cmdbdb") //读取application.yml中的配置参数映射成为一个对象
    public DataSource getCmdbDataSource(DBConfig_CMDB dbConfigCmdb) throws SQLException {
//        DataSource d = DataSourceBuilder.create().build();
//        return DataSourceBuilder.create().build();
        //Atomikos统一管理分布式事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();

//        Properties p = new Properties();
//        p.setProperty ( "user" , dbConfigCmdb.getUsername() );
//        p.setProperty ( "password" , dbConfigCmdb.getPassword() );
//        p.setProperty ( "URL" , dbConfigCmdb.getUrl() );
//        xaDataSource.setXaProperties ( p );

        //用druidXADataSource方式或者上面的Properties方式都可以
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setUrl(dbConfigCmdb.getUrl());
        druidXADataSource.setUsername(dbConfigCmdb.getUsername());
        druidXADataSource.setPassword(dbConfigCmdb.getPassword());
        druidXADataSource.setDriverClassName(dbConfigCmdb.getDriverClassName());

       /* MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dbConfigCmdb.getUrl());
        mysqlXaDataSource.setPassword(dbConfigCmdb.getPassword());
        mysqlXaDataSource.setUser(dbConfigCmdb.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);*/

        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setUniqueResourceName("cmdbDataSource");
        xaDataSource.setMinPoolSize(dbConfigCmdb.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dbConfigCmdb.getMaxPoolSize());
        xaDataSource.setMaxLifetime(dbConfigCmdb.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(dbConfigCmdb.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(dbConfigCmdb.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(dbConfigCmdb.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(dbConfigCmdb.getMaxIdleTime());
//        xaDataSource.setTestQuery(dbConfigCmdb.getTestQuery());
        return xaDataSource;
    }

    @Primary
    @Bean("cmdbDBSqlSessionFactory")
    public SqlSessionFactory cmdbDBSqlSessionFactory(@Qualifier("cmdbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        Properties properties = new Properties();
        //数据库
        properties.setProperty("jdbc.type", "mysql");
        paginationInterceptor.setProperties(properties);
        bean.setPlugins(paginationInterceptor);
        bean.setTypeAliases(Page.class);
        bean.setTypeAliasesPackage("com.rlc.rlccmdbapi.modules.cmdb.entity");
        bean.setTypeHandlers(new ConvertBlobTypeHandler());
        bean.setDataSource(dataSource);
        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappings/modules/cmdb/*.xml"));
        return bean.getObject();
    }
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
    @Bean(name = "transactionManager")
    @Primary
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
    @Primary
    @Bean("cmdbSqlSessionTemplate")
    public SqlSessionTemplate cmdbSqlSessionTemplate(@Qualifier("cmdbDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}