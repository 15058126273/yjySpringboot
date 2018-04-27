package com.yjy.test.game.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 记录请求执行时间
 */
@WebFilter(urlPatterns = "/*")
public class ProcessTimeFilter implements Filter {

    protected final Logger log = LoggerFactory.getLogger(ProcessTimeFilter.class);

    /**
     * 请求执行开始时间
     */
    public static final String START_TIME = "_start_time";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        long time = System.currentTimeMillis();
        log.info("process start at {} for uri: {}", time, request.getRequestURI());
        request.setAttribute(START_TIME, time);
        chain.doFilter(request, response);
        time = System.currentTimeMillis() - time;
        log.info("process in {} ms: {}", time, request.getRequestURI());
    }

    public void init(FilterConfig arg0) throws ServletException {
        log.info("CustomFilter: ProcessTimeFilter init....");
    }

}
