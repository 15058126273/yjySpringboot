package com.yjy.test.controller;

import com.yjy.test.property.JdbcProperty;
import com.yjy.test.property.UserProperty;
import com.yjy.test.entity.User;
import com.yjy.test.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-17 13:49
 */
@Controller
@RequestMapping(value = "/index")
@PropertySource(value = {"/config/my.properties"})
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired UserProperty userProperty;
    private final ApplicationArguments applicationArguments;
    private final IndexService indexService;
    @Autowired
    private JdbcProperty jdbcProperty;

    @Autowired
    public IndexController(ApplicationArguments args, IndexService indexService) {
        this.applicationArguments = args;
        this.indexService = indexService;
    }


    @Value("${what}")
    private String what;
    @Value("${app.name}")
    private String appName;
    @Value("${app.description}")
    private String appDescription;
    @Value("${key1}")
    private String key1;
    @Value("${user.list[0].username}")
    private String username;


    @ResponseBody
    @RequestMapping(value = "/hello")
    public String hello(HttpServletRequest request) {
        // yml
        System.out.printf("what: %s, \n appName: %s, \n appDescription: %s, \n username: %s \n",
                what, appName, appDescription, username);

        // properties
        System.out.printf("key1: %s, \n ", key1);

        // program arguments
        boolean debug = applicationArguments.containsOption("debug");
        List<String> param = applicationArguments.getNonOptionArgs();
        System.out.printf("debug: %s, \n params: %s \n", debug, param);

        // autowired Service
        indexService.print(request.getRequestURL().toString());

        // configurationProperties
        List<User> list = userProperty.getList();
        System.out.printf("list: %s \n", list);

        // Profile
        System.out.printf("jdbc > UserName: %s, password: %s \n", jdbcProperty.getUsername(), jdbcProperty.getPassword());

        // try log info
        log.info("log4j : {}", log.getName());

        return "Hello World! " + request.getRequestURL();
    }

    @RequestMapping(value = "/{param}")
    public String bye(@PathVariable String param, HttpServletRequest request) {
        log.info("param: {}", param);
        return "hello";
    }

}
