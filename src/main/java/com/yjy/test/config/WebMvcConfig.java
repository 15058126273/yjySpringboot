package com.yjy.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义WebMvcConfigurerAdapter配置
 *
 * @Author yjy
 * @Date 2018-04-23 11:40
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

    /**
     * 静态资源请求配置
     * @param registry 资源处理注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("addResourceHandlers...........................");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/common/");
        super.addResourceHandlers(registry);
    }

}
