package com.yjy.test.game.web.servlet;

import com.yjy.test.game.service.OptionItemService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * 自定义初始化 ServletContext
 *
 * WebServlet中 urlPatterns必须填写, 否则不会加载此Servlet, 同时需要配置 loadOnStartup = 1
 *
 * @Author yjy
 * @Date 2018-05-02 11:47
 */
//@WebServlet(urlPatterns = "", loadOnStartup = 1)
public class DictServlet extends HttpServlet {

    private OptionItemService optionItemService;

    public void setOptionItemService(OptionItemService optionItemService) {
        this.optionItemService = optionItemService;
    }

    public void init() throws ServletException {
        System.out.println("DictServlet init..............................");

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        setOptionItemService(wac.getBean (OptionItemService.class));
        optionItemService.getAllFieldName();
        // init something...
        // 例子: 设置Servlet全局属性
        this.getServletContext().setAttribute("appName", "项目名");

        super.init();
    }

}
