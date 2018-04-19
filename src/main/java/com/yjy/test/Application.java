package com.yjy.test;

import com.yjy.test.controller.IndexController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-17 12:43
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
//@SpringBootApplication // same as default annotations equals -> (@Configuration, @EnableAutoConfiguration, @ComponentScan)
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    }

}
