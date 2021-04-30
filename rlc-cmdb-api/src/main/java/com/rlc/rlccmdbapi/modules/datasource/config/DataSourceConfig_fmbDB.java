package com.rlc.rlccmdbapi.modules.datasource.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.persistence.interceptor.PaginationInterceptor;
import com.rlc.rlcbase.persistence.typeHandler.ConvertBlobTypeHandler;
import com.rlc.rlccmdbapi.modules.datasource.prop.DBConfig_FMB;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"com.rlc.rlccmdbapi.modules.biz.dao"}, sqlSessionFactoryRef = "fmbSqlSessionFactory")
public class DataSourceConfig_fmbDB {
    @Bean("fmbDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.fmbdb")
    public DataSource getmesDataSource(DBConfig_FMB fmbDBConfig) throws SQLException {
//        return DataSourceBuilder.create().build();
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(fmbDBConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(fmbDBConfig.getPassword());
        mysqlXaDataSource.setUser(fmbDBConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("fmbDataSource");
        return xaDataSource;
    }

    @Bean("fmbSqlSessionFactory")
    public SqlSessionFactory cmdbDBSqlSessionFactory(@Qualifier("fmbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        Properties properties = new Properties();
        //数据库
        properties.setProperty("jdbc.type", "oracle");
        paginationInterceptor.setProperties(properties);
        bean.setPlugins(paginationInterceptor);
        bean.setTypeAliases(Page.class);
        bean.setTypeAliasesPackage("com.rlc.cmdbServer.modules.fmb.entity");
        bean.setTypeHandlers(new ConvertBlobTypeHandler());
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappings/modules/fmb/*.xml"));
        return bean.getObject();
    }
    /*
     * @methodDesc: 功能描述:(test2 事物管理)
     */
//    @Bean(name = "fmbTransactionManager")
//    public DataSourceTransactionManager fmbTransactionManager(@Qualifier("fmbDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
    @Bean("fmbSqlSessionTemplate")
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("fmbSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}