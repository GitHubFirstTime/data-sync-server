package com.rlc.rlcframework.config;

import com.rlc.rlcbase.persistence.interceptor.PaginationInterceptor;
import com.rlc.rlcframework.datasource.DynamicSqlSessionTemplate;
import com.rlc.rlcframework.transaction.MultiDataSourceTransactionFactory;
import com.rlc.rlcframework.transaction.MyTransactionsFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.*;


/**
 * @author rlc_zyc
 * @version 1.0
 * @description Mybatis支持*匹配扫描包
 * @date 2021/5/13 17:26
 */
@Configuration
public class MybatisConfig {
    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(Environment env,@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) throws Exception {
////        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
////        bean.setDataSource(dataSource);
////        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("mybatis-config.xml"));
////        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mappers/**.xml"));
////        return bean.getObject();
//        return createSqlSessionFactory(env, dynamicDataSource);
//    }
//    @Bean(name = "sqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(Environment env, @Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
//        return createSqlSessionFactory(env, dataSource);
//    }
//    @Bean(name = "sqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
    public SqlSessionFactory createSqlSessionFactory(Environment env, DataSource dataSource) throws Exception
    {
        String typeAliasesPackage = env.getProperty("mybatis.type-aliases-package");
        String mapperLocations = env.getProperty("mybatis.mapper-locations");
        String configLocation = env.getProperty("mybatis.config-location");
        typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
        VFS.addImplClass(SpringBootVFS.class);
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        Properties properties = new Properties();
        //数据库
        properties.setProperty("jdbc.type", getDBType(dataSource.getConnection()));
        paginationInterceptor.setProperties(properties);

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory.setPlugins(paginationInterceptor);
        sessionFactory.setMapperLocations(resolveMapperLocations(StringUtils.split(mapperLocations, ",")));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        sessionFactory.setTransactionFactory(new MultiDataSourceTransactionFactory());
        sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
    }
    public static String setTypeAliasesPackage(String typeAliasesPackage)
    {
        ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        List<String> allResult = new ArrayList<String>();
        try
        {
            for (String aliasesPackage : typeAliasesPackage.split(","))
            {
                List<String> result = new ArrayList<String>();
                aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()) + "/" + DEFAULT_RESOURCE_PATTERN;
                Resource[] resources = resolver.getResources(aliasesPackage);
                if (resources != null && resources.length > 0)
                {
                    MetadataReader metadataReader = null;
                    for (Resource resource : resources)
                    {
                        if (resource.isReadable())
                        {
                            metadataReader = metadataReaderFactory.getMetadataReader(resource);
                            try
                            {
                                result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                            }
                            catch (ClassNotFoundException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (result.size() > 0)
                {
                    HashSet<String> hashResult = new HashSet<String>(result);
                    allResult.addAll(hashResult);
                }
            }
            if (allResult.size() > 0)
            {
                typeAliasesPackage = String.join(",", (String[]) allResult.toArray(new String[0]));
            }
            else
            {
                throw new RuntimeException("mybatis typeAliasesPackage 路径扫描错误,参数typeAliasesPackage:" + typeAliasesPackage + "未找到任何包");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return typeAliasesPackage;
    }

    public static Resource[] resolveMapperLocations(String[] mapperLocations)
    {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null)
        {
            for (String mapperLocation : mapperLocations)
            {
                try
                {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                }
                catch (IOException e)
                {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }
    /**
     * 获取数据库类型方法
     */
    public static String getDBType(Connection conn){
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            String dataBaseType = dbmd.getDatabaseProductName();	              //获取数据库类型
            if(("Microsoft SQL Server").equals(dataBaseType)){
                return "sqlserver";
            }else if(("HSQL Database Engine").equals(dataBaseType)){
                return "hsql";
            }else if(("MySQL").equals(dataBaseType)){
                return "mysql";
            }else if(("Oracle").equals(dataBaseType)){
                return "oracle";
//			}else if(){

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //下面为动态多数据源配置区域
    @Bean(name = "sqlSessionFactoryCMDB")
    public SqlSessionFactory sqlSessionFactoryMaster(Environment env, @Qualifier("cmdbDataSource") DataSource dataSource) throws Exception
    {
        return createSqlSessionFactory(env, dataSource);
    }

    @Bean(name = "sqlSessionFactoryFMB")
    public SqlSessionFactory sqlSessionFactorySlave(Environment env, @Qualifier(value = "fmbDataSource") DataSource dataSource) throws Exception
    {
        return createSqlSessionFactory(env, dataSource);
    }
    @Bean(name = "sqlSessionFactoryMESUAT")
    public SqlSessionFactory sqlSessionFactory_MESUAT(Environment env, @Qualifier("mesUatDataSource") DataSource dataSource) throws Exception
    {
        return createSqlSessionFactory(env, dataSource);
    }

    @Bean(name = "sqlSessionFactoryMES")
    public SqlSessionFactory sqlSessionFactory_MES(Environment env, @Qualifier(value = "mesDataSource") DataSource dataSource) throws Exception
    {
        return createSqlSessionFactory(env, dataSource);
    }
    @Bean(name = "sqlSessionFactoryREPORT")
    public SqlSessionFactory sqlSessionFactory_REPORT(Environment env, @Qualifier(value = "reportDataSource") DataSource dataSource) throws Exception
    {
        return createSqlSessionFactory(env, dataSource);
    }
    @Bean(name = "sqlSessionTemplate")
    public DynamicSqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryCMDB") SqlSessionFactory factoryMaster,
                                                        @Qualifier("sqlSessionFactoryFMB") SqlSessionFactory factorySlave,
                                                        @Qualifier("sqlSessionFactoryMESUAT") SqlSessionFactory factoryMesUat,
                                                        @Qualifier("sqlSessionFactoryMES") SqlSessionFactory factoryMes,
                                                        @Qualifier("sqlSessionFactoryREPORT") SqlSessionFactory factoryReport) throws Exception
    {
        Map<Object, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();
        sqlSessionFactoryMap.put("cmdbdb", factoryMaster);
        sqlSessionFactoryMap.put("fmbdb", factorySlave);
        sqlSessionFactoryMap.put("mesuatdb", factoryMesUat);
        sqlSessionFactoryMap.put("mesdb", factoryMes);
        sqlSessionFactoryMap.put("reportdb", factoryReport);

        DynamicSqlSessionTemplate customSqlSessionTemplate = new DynamicSqlSessionTemplate(factoryMaster);
        customSqlSessionTemplate.setTargetSqlSessionFactorys(sqlSessionFactoryMap);
        return customSqlSessionTemplate;
    }
//    @Bean(name = "userSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(Environment env, @Autowired @Qualifier("dynamicDataSource") DataSource dynamicDataSource)
//            throws Exception {
////        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
////        // 注意这里替换成我们写的DynamicDatasource
////        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
////        // 替换原有的事物工厂，不然无法切换数据库
////        sqlSessionFactoryBean.setTransactionFactory(new MyTransactionsFactory());
////        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
//
//        return  createSqlSessionFactory(env, dynamicDataSource);
//    }
}
