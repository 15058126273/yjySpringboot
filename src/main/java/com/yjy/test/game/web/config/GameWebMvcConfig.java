package com.yjy.test.game.web.config;

import com.yjy.test.game.web.interceptor.AdminLoginInterceptor;
import com.yjy.test.game.web.interceptor.FrontLoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义WebMvcConfigurerAdapter配置
 *
 * @Author yjy
 * @Date 2018-04-23 11:40
 */
@Configuration
public class GameWebMvcConfig extends WebMvcConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(GameWebMvcConfig.class);

    /**
     * 拦截器配置
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("addInterceptors1....................");
        registry.addInterceptor(getFrontLoginInterceptor())
                .addPathPatterns("*.jtk", "/*.jtk", "/*/*.jtk", "/*/*/*.jtk");
        registry.addInterceptor(getAdminLoginInterceptor())
                .addPathPatterns("*.do", "/*.do", "/*/*.do", "/*/*/*.do");
        super.addInterceptors(registry);
    }


    @Bean
    AdminLoginInterceptor getAdminLoginInterceptor() {
        return new AdminLoginInterceptor();
    }

    @Bean
    FrontLoginInterceptor getFrontLoginInterceptor() {
        return new FrontLoginInterceptor();
    }

}
