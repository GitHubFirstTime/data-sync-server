package com.rlc.rlcbase.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;


/**
 * TODO
 * ClassName:EncryptPropertyPlaceholderConfigurer <br/>
 * Function: 配置解密 ADD FUNCTION. <br/>
 * Reason:	 配置解密 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/29 17:52
 * @since JDK 1.8
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer implements InitializingBean{
    /**
     * 需要解密的配置项前缀
     */
    private static final String PREFIX_ENC = "enc:";

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected Properties mergeProperties() throws IOException {
        Properties mergedProperties = new Properties();
        for (Properties localProp : localProperties) {
            mergedProperties.putAll(localProp);
        }

        for (Map.Entry entry : mergedProperties.entrySet()) {
            if (entry.getValue().toString().startsWith(PREFIX_ENC)) {
                String key = System.getProperty("enc.key");
                if(StringUtils.isEmpty(key)){
                    key = "zhonghuan2020@";
                }
//                System.out.println("ssssss==========="+key);
                String value = entry.getValue().toString().replace(PREFIX_ENC, StringUtils.EMPTY);
//                System.out.println("ssssssvalue==========="+value);
                mergedProperties.setProperty(entry.getKey().toString(), AESUtil.decode(value, key));
            }
        }

        //针对sharding-jdbc datasource自定义解密的特殊处理
        //因为sharding-jdbc的datasource注入是从environment中获取propertySource,
        //不能直接通过PropertySourcesPlaceholderConfigurer定义的datasource获取
        MutablePropertySources sources = ((ConfigurableEnvironment) environment).getPropertySources();
        sources.addFirst(new PropertiesPropertySource(LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME, mergedProperties));

        return mergedProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        localOverride = true;
    }
}