package com.yjy.test.game.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 前台servlet
 *
 * @Author yjy
 * @Date 2018-04-23 16:26
 */
@WebServlet(name = "frontServlet", urlPatterns = {"/*"})
public class CustomFrontServlet extends DispatcherServlet {

    private static final Logger log = LoggerFactory.getLogger(CustomFrontServlet.class);

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("frontServlet doService...");
        super.doService(request, response);
    }

}