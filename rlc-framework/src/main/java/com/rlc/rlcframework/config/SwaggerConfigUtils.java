package com.rlc.rlcframework.config;

import com.rlc.rlcbase.config.AppBaseInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/5/14 18:26
 */
//@Component
//@EnableSwagger2
public class SwaggerConfigUtils {
//    @Bean
//    public Docket coreApi() {
//        String moduleCode = "CMDB";
//        String moduleName = "CMDB模块";
//        String[] basePackage = { "com.rlc.rlccmdbapi.modules.cmdb.controller", "com.rlc.rlccmdbapi.modules.fmb.controller" };
//        return SwaggerConfigUtils.docket(moduleCode, moduleName, basePackage);
//    }
    /**
     * 配置模块
     *
     * @param moduleCode  模块Code
     * @param moduleName  模块名称
     * @param basePackage 基础包（多个）
     * @return
     */
    public static Docket docket(String moduleCode, String moduleName, String... basePackage) {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo(moduleName)).groupName(moduleCode).select()
                .apis(Predicates.and(RequestHandlerSelectors.withMethodAnnotation(ResponseBody.class),
                        basePackage(basePackage)))
                .build();
    }
    /**
     * 声明基础包
     *
     * @param basePackage 基础包路径
     * @return
     */
    public static Predicate<RequestHandler> basePackage(final String... basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    /**
     * 校验基础包
     *
     * @param basePackage 基础包路径
     * @return
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String... basePackage) {
        return input -> {
            for (String strPackage : basePackage) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * 检验基础包实例
     *
     * @param requestHandler 请求处理类
     * @return
     */
    @SuppressWarnings("deprecation")
    private static Optional<? extends Class<?>> declaringClass(RequestHandler requestHandler) {
        return Optional.fromNullable(requestHandler.declaringClass());
    }

    /**
     * 添加摘要信息
     */
    private static ApiInfo apiInfo(String moduleName)
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：CMDB_接口文档")
                // 描述
                .description("描述：用于管理CMDB的接口信息,具体包括cmdb,fmb模块...")
                // 作者信息
                .contact(new Contact("Powered By RlcTech","http://www.rlctech.com", null))
                .termsOfServiceUrl("http://www.rlctech.com")
                // 版本
                .version("版本号:" + AppBaseInfo.getVersion())
                .build();
    }
}
