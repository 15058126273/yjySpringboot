package com.yjy.test.game.controller.front;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-23 16:30
 */
@Controller
@Component("frontIndexTest")
@RequestMapping("/index")
public class IndexControllerTest {


    @RequestMapping(value = "/hello.sv", method = RequestMethod.GET)
    public String hello(HttpServletRequest request, Model model) {
        model.addAttribute("param", "你好!");
        return "front/index/hello";
    }

}
