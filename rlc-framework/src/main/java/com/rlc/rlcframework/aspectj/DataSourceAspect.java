package com.rlc.rlcframework.aspectj;

import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.datasource.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
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
@Order(-10)
@Component
@Slf4j
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
//        mybatis生成的代理类，所以获取它的接口来获取DbAnnotation注解信息
        DS dataSource = null;
        try {
            Class<?> targetClass = point.getTarget().getClass().getInterfaces()[0];
            dataSource = targetClass.getAnnotation(DS.class);
        } catch (Exception e) {
            log.info("类、接口级数据源注解为空");
        }
        if (Objects.nonNull(dataSource)) {
            return dataSource;
        }
        try {
            Class<?> targetClass = point.getTarget().getClass();
            dataSource = targetClass.getAnnotation(DS.class);
        } catch (Exception e) {
            log.info("类、接口级数据源注解为空");
        }
        // 先判断类的注解，再判断方法注解
        if (Objects.nonNull(dataSource)) {
            return dataSource;
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        dataSource = AnnotationUtils.findAnnotation(signature.getDeclaringType(), DS.class);
        if (Objects.nonNull(dataSource)) {
            return dataSource;
        }
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        dataSource = methodSignature.getMethod().getAnnotation(DS.class);
        log.info("使用方法级数据源注解");
        return dataSource;

//        MethodSignature signature = (MethodSignature) point.getSignature();
//        DS dataSource = AnnotationUtils.findAnnotation(signature.getDeclaringType(), DS.class);
//        if (Objects.nonNull(dataSource))
//        {
//            return dataSource;
//        }
//        dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DS.class);
//                log.info("使用方法级数据源注解{}",dataSource.value());
//        return dataSource;
    }
}
