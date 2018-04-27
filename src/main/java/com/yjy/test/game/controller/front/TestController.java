package com.yjy.test.game.controller.front;

import com.yjy.test.game.service.UserService;
import com.yjy.test.property.JdbcProperty;
import com.yjy.test.property.UserProperty;
import com.yjy.test.game.entity.User;
import com.yjy.test.util.hibernate.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;
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

    @Autowired UserProperty userProperty;
    private final ApplicationArguments applicationArguments;
    @Autowired
    private JdbcProperty jdbcProperty;
    @Resource
    private UserService userService;

    @Autowired
    public TestController(ApplicationArguments args) {
        this.applicationArguments = args;
    }

    @Value("${app.name}")
    private String appName;
    @Value("${app.description}")
    private String appDescription;
    @Value("${user.list[0].nickname}")
    private String username;


    @ResponseBody
    @RequestMapping(value = "/hello.sv")
    public String hello(HttpServletRequest request) {
        // yml
        System.out.printf("appName: %s, \n appDescription: %s, \n username: %s \n",
                 appName, appDescription, username);

        // program arguments
        boolean debug = applicationArguments.containsOption("debug");
        List<String> param = applicationArguments.getNonOptionArgs();
        System.out.printf("debug: %s, \n params: %s \n", debug, param);

        // configurationProperties
        List<User> list = userProperty.getList();
        System.out.printf("list: %s \n", list);

        // Profile
        System.out.printf("jdbc > UserName: %s, password: %s \n", jdbcProperty.getUsername(), jdbcProperty.getPassword());

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

    @RequestMapping(value = "/jpa.sv")
    public String welcome() {
//        User user = new User("test save", "test save");
//        user = userService.save(user);
//        System.out.println(user);
//
//        Long id = user.getId();
//
//        user.setHeadImg("test update");
//        user = userService.update(user);
//        System.out.println(user);
//
//        List<User> allList = userService.findAll();
//        System.out.println(allList);
//
//        User user1 = userService.findById(id);
//        System.out.println(user1);
//
//        User user2 = userService.load(id);
//        System.out.println(user2);
//
//        user2.setOpenId("test saveAndRefresh success");
//        user2 = userService.saveAndRefresh(user2);
//        System.out.println(user2);
//
//        long allCount = userService.findAllCount();
//        System.out.println(allCount);
//
//        User user3 = new User();
//        user3.setNickName("test1");
//        long count = userService.findCount(user3);
//        System.out.println(count);
//
//        User user4 = userService.findByProperty("nickName", "test save");
//        System.out.println(user4);
//
//        List<User> list = userService.findListByProperty("nickName", "test", true);
//        System.out.println(list);
//
//        User user5 = new User();
//        user5.setNickName("test1");
//        List<User> list1 = userService.findList(user5);
//        System.out.println(list1);
//
//        Pagination page = userService.findAllPage(2, 4, new Sort.Order(Sort.Direction.DESC, "id"));
//        System.out.println(page);
//        log.info("totalPage: {}, totalCount: {}, content: {}",
//                page.getTotalPage(), page.getTotalCount(), page.getList());
//
//
//        User user6 = new User();
//        user6.setNickName("test1");
//        Pagination page1 = userService.findListPage(user6, 2, 2, new Sort.Order(Sort.Direction.DESC, "id"));
//        System.out.println(page1);

//        userService.deleteById(26L);


//        User user7 = new User();
//        user7.setId(1L);
//        userService.delete(user7);
//        System.out.println("deleted...");

        Pagination p = userService.findAllPage(1, 10);
        System.out.println(p);
        return "front/index";
    }

}
