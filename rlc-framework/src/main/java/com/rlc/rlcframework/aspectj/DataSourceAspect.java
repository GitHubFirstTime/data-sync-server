package com.rlc.rlcframework.aspectj;

import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: 多数据源处理
 * @date 2021/5/13 17:33
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {
    @Pointcut("@annotation(com.rlc.rlcbase.persistence.annotation.DS)"
            + "|| @within(com.rlc.rlcbase.persistence.annotation.DS)")
    public void dsPointCut()
    {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        DS ds = getDataSource(point);

        if (null!=ds)
        {
            DynamicDataSourceContextHolder.setDataSourceType(ds.value());
        }

        try
        {
            return point.proceed();
        }
        finally
        {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 获取需要切换的数据源
     */
    public DS getDataSource(ProceedingJoinPoint point)
    {
        MethodSignature signature = (MethodSignature) point.getSignature();
        DS dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DS.class);
        if (Objects.nonNull(dataSource))
        {
            return dataSource;
        }

        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DS.class);
    }
}
