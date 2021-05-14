package com.rlc.rlcbase.persistence.annotation;

import java.lang.annotation.*;
/**
 * @author rlc_zyc
 * @version 1.0
 * @description: 自定义多数据源切换注解
 * 优先级：先方法，后类，如果方法覆盖了类上的数据源类型，以方法的为准，否则以类上的为准
 * @date 2021/5/13 10:22
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DS {
    /**
     * 切换数据源名称
     */
//    public DataSourceType value() default DataSourceType.MASTER;
    public String value();
}
