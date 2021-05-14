package com.rlc.rlcframework.datasource;

import com.rlc.rlcbase.persistence.datasource.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/5/13 11:54
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public String dbType;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public DynamicDataSource() {
    }
    /**
     * 动态更新自定义数据源
     * @param defaultTargetDataSource
     * @param targetDataSources
     */
    public void updateTargetDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources)
    {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
    /**
     * 动态更新自定义数据源
     * @param customDataSources
     */
    public void DynamicDataSource(Map<String,DataSource> customDataSources){
        Map<Object,Object> customDS=new HashMap<Object, Object>();
        customDS.putAll(customDataSources);
        super.setTargetDataSources(customDS);
        super.afterPropertiesSet();
    }
}
