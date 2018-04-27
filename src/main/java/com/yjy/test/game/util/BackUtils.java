package com.yjy.test.game.util;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.base.Constants;
import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.web.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class BackUtils {

    public static final Integer STATIC_PAPER_COURSE = 300;

    public static final String BACK_PATH = "/WEB-INF/back/";
    //private static final String SUFFIX = ".html";

    public static String returnPage(Config config, String dir, String page, HttpServletRequest request, ModelMap model) {
        backData(config, request, model);
        return "back/" + dir + "/" + page;
//		return BACK_PATH + dir + "/" +page + SUFFIX;
    }

    public static String returnErrorPage(Config config, HttpServletRequest request, String msg, String url, ModelMap model) {
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        backData(config, request, model);
        return "error";
    }

    public static String returnSuccessPage(Config config, HttpServletRequest request, String msg, String url, ModelMap model) {
        model.addAttribute("url", url);
        model.addAttribute("msg", msg);
        backData(config, request, model);
        return "success";
    }

    /**
     * 为前台模板设置公用数据
     *
     * @param request
     * @param map
     */
    public static void backData(Config config, HttpServletRequest request, Map<String, Object> map) {
        if (request == null) {
            return;
        }
        Admin user = (Admin) request.getSession().getAttribute(Constants.ADMIN_USER);
        String location = RequestUtils.getLocation(request);
        frontData(config, map, user, location);
    }

    public static void frontData(Config config, Map<String, Object> map, Admin user, String location) {
        if (user != null) {
            map.put("user", user);
        }
        String base = config.getContextPath() != null ? config.getContextPath() : "";
        String backBase = base + "/manager/admin";
        map.put("base", base);
        map.put("backBase", backBase);
        map.put("location", location);
    }

    /**
     * 获取当前登陆的用户
     *
     * @param request
     * @return
     */
    public static Admin getCurrentUser(HttpServletRequest request) {
        Admin user = (Admin) request.getSession().getAttribute(Constants.ADMIN_USER);
        return user;
    }

    public static Integer getCurUserId(HttpServletRequest request) {
        return getCurrentUser(request).getId();
    }

    /**
     * 获取config 参数
     *
     * @param key
     * @return
     * @author wdy
     * @version ：2017年5月11日 下午7:28:08
     */
    @SuppressWarnings("unchecked")
    public static String configParame(String key) {
        String value = null;
        if (StringUtils.isBlank(key)) {
            return value;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ServletContext servletContext = request.getSession().getServletContext();
        Map<String, String> configMap = (Map<String, String>) servletContext.getAttribute("configMap");
        if (null != configMap) {
            value = configMap.get(Constants.PRE_CONFIG + key);
        }
        return value;
    }

}