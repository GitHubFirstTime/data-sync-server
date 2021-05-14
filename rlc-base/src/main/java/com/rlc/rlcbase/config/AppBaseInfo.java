package com.rlc.rlcbase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: 应用基础信息
 * @date 2021/5/14 16:27
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppBaseInfo {
    /** 项目名称 */
    private static String projectName;

    /** 版本 */
    private static String version;

    /** 版权年份 */
    private static String copyrightYear;

    public static String getProjectName() {
        return projectName;
    }

    public static void setProjectName(String projectName) {
        AppBaseInfo.projectName = projectName;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        AppBaseInfo.version = version;
    }

    public static String getCopyrightYear() {
        return copyrightYear;
    }

    public static void setCopyrightYear(String copyrightYear) {
        AppBaseInfo.copyrightYear = copyrightYear;
    }
}
