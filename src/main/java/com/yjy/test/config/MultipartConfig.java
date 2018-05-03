package com.yjy.test.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.MultipartConfigElement;

/**
 * 配置tomcat上传限制
 *
 * @Author yjy
 * @Date 2018-04-24 14:38
 */
@Configuration
public class MultipartConfig {

    /**
     * 配置tomcat上传限制
     * @return 配置
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("50MB");
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }

}
