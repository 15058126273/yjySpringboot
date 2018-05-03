package com.yjy.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * SpringBoot 启动入口
 *
 * @Author yjy
 * @Date 2018-04-17 12:43
 */
//@SpringBootApplication = (@Configuration, @EnableAutoConfiguration, @ComponentScan)
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EntityScan("com.yjy.test.game.entity") // 扫描实体类
@ServletComponentScan(basePackages = "com.yjy.test") // 扫描自定义Servlet
@PropertySource(value = { // 导入配置
        "classpath:/config/application.yml",
})
public class Application extends SpringBootServletInitializer {

    // IDEA运行时 运行此函数
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // 导出war在外部tomcat使用时, 不能使用main函数运行, 需要配置此项
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
