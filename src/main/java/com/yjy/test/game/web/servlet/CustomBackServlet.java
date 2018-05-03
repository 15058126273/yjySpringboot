package com.yjy.test.game.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台servlet
 * 需要添加对应的 *-servlet.xml
 *
 * @Author yjy
 * @Date 2018-04-23 16:26
 */
@WebServlet(name = "backServlet", urlPatterns = {"/manager/admin/*"})
public class CustomBackServlet extends DispatcherServlet {

    private static final Logger log = LoggerFactory.getLogger(CustomBackServlet.class);

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("backServlet doService...");
        super.doService(request, response);
    }


}
