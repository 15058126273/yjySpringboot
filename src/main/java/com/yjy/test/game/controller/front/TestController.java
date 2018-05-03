package com.yjy.test.game.controller.front;

import com.yjy.test.game.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-17 13:49
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    private final ApplicationArguments applicationArguments;
    @Resource
    private UserService userService;

    @Autowired
    public TestController(ApplicationArguments args) {
        this.applicationArguments = args;
    }

    @ResponseBody
    @RequestMapping(value = "/hello.sv")
    public String hello(HttpServletRequest request) {

        // program arguments
        boolean debug = applicationArguments.containsOption("debug");
        List<String> param = applicationArguments.getNonOptionArgs();
        System.out.printf("debug: %s, \n params: %s \n", debug, param);

        // try log info
        log.info("log4j : {}", log.getName());

        return "Hello World! " + request.getRequestURL();
    }

    @CrossOrigin(value = "*", allowedHeaders = "*", methods = {RequestMethod.GET})
    @RequestMapping(value = "/{param}/custom.sv")
    public String bye(@PathVariable String param, HttpServletRequest request, Model model) {
        log.info("param: {}", param);
        return "hello1";
    }


}
