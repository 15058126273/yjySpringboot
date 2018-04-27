package com.yjy.test.game.controller.back;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台
 *
 * @Author yjy
 * @Date 2018-04-23 16:34
 */
@Controller
@Component("backIndex")
@RequestMapping("/index")
public class IndexControllerTest {

    @RequestMapping(value = "/hello.do", method = RequestMethod.GET)
    public String hello(HttpServletRequest request, Model model) {
        model.addAttribute("param", "你好!");
        return "back/index/hello";
    }

}
