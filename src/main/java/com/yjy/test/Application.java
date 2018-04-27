package com.yjy.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-17 12:43
 */
// same as default annotations equals -> (@Configuration, @EnableAutoConfiguration, @ComponentScan)
//@SpringBootApplication

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EntityScan("com.yjy.test.game.entity") // 扫描实体类
@ServletComponentScan(basePackages = "com.yjy.test")
@PropertySource(value = {
        "classpath:/config/application.yml",
})
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
