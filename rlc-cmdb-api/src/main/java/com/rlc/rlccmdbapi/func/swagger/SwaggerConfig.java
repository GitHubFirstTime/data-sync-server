package com.rlc.rlccmdbapi.func.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.rlc.rlcbase.config.AppBaseInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: Swagger2的接口配置
 * @date 2021/5/14 16:23
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig  {
    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
    /**
     * 创建API
     */
    @Bean("createRestApi")
    public Docket createRestApi()
    {
        Predicate selector1 = RequestHandlerSelectors.basePackage("com.rlc.rlccmdbapi.modules.cmdb.controller");
        Predicate selector2 = RequestHandlerSelectors.basePackage("com.rlc.rlccmdbapi.modules.fmb.controller");
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
//                .apis(RequestHandlerSelectors.basePackage("com.rlc.rlccmdbapi.modules.cmdb.controller"))
                .apis(Predicates.or(selector1,selector2))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * 创建API
     */
//    @Bean("createRestApi2")
//    public Docket createRestApi2()
//    {
//        return new Docket(DocumentationType.SWAGGER_2)
//                // 是否启用Swagger
//                .enable(enabled)
//                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
//                .apiInfo(apiInfo())
//                // 设置哪些接口暴露给Swagger展示
//                .select()
//                // 扫描所有有注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                // 扫描指定包中的swagger注解
//                .apis(RequestHandlerSelectors.basePackage("com.rlc.rlccmdbapi.modules.fmb.controller"))
//                // 扫描所有 .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：CMDB_接口文档")
                // 描述
                .description("描述：用于管理CMDB的接口信息,具体包括cmdb,fmb模块...")
                // 作者信息
                .contact(new Contact(AppBaseInfo.getProjectName(),"http://www.rlctech.com", null))
                // 版本
                .version("版本号:" + AppBaseInfo.getVersion())
                .build();
    }
}
