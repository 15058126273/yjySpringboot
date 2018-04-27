package com.yjy.test.game.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yjy.test.game.base.Constants;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.redis.RedisUtils;
import com.yjy.test.game.web.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class FrontUtils {

    public static final Integer STATIC_PAPER_COURSE = 300;

    public static final String FRONT_PATH = "/WEB-INF/front/";
    //private static final String SUFFIX = ".html";

    public static String returnPage(Config config, String dir, String page, HttpServletRequest request, ModelMap model) {
        frontData(config, request, model);
        return "front/" + dir + "/" + page;
//		return BACK_PATH + dir + "/" +page + SUFFIX;
    }

    public static String returnErrorPage(Config config, HttpServletRequest request, String msg, String url, ModelMap model) {
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        frontData(config, request, model);
        return "error";
    }

    public static String returnSuccessPage(Config config, HttpServletRequest request, String msg, String url, ModelMap model) {
        model.addAttribute("url", url);
        model.addAttribute("msg", msg);
        frontData(config, request, model);
        return "success";
    }

    /**
     * 为前台模板设置公用数据
     *
     * @param request
     * @param map
     */
    public static void frontData(Config config, HttpServletRequest request, Map<String, Object> map) {
        if (request == null) {
            return;
        }
        User user = (User) request.getSession().getAttribute(Constants.FRONT_USER);
        String location = RequestUtils.getLocation(request);
        frontData(config, map, user, location);
    }

    public static void frontData(Config config, Map<String, Object> map, User user, String location) {
        if (user != null) {
            map.put("user", user);
        }
        String base = config.getContextPath() != null ? config.getContextPath() : "";
        String frontBase = base + "/";
        map.put("base", base);
        map.put("frontBase", frontBase);
        map.put("location", location);
    }

    /**
     * 获取当前登陆的用户
     *
     * @param request
     * @return
     */
    public static User getCurrentUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.FRONT_USER);
        if (null == user) {

        }
        return user;
    }

    public static Long getCurUserId(HttpServletRequest request) {
        return getCurrentUser(request).getId();
    }

    /**
     * session 的管理
     *
     * @param name
     * @param value
     * @author wdy
     * @version ：2016年8月28日 下午2:05:40
     */
    public static void set2Session(String name, Object value) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute(name, value);
    }

    public static Object getFromSession(String name) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        return session.getAttribute(name);
    }

    public static void removeFromSession(String name) {
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().removeAttribute(name);
    }

    public static String getCustomerModel(String agent) {
        Pattern pattern = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
        Matcher matcher = pattern.matcher(agent);
        String customerModel = null;
        if (matcher.find()) {
            customerModel = matcher.group(1).trim();
            return customerModel;
        }
        return null;
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
//    	HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//    	ServletContext servletContext = request.getSession().getServletContext();
//    	Map<String, String> configMap = (Map<String, String>)servletContext.getAttribute(Constants.ALL_CONFIG_MAP);
        Map<String, String> configMap = (Map<String, String>) RedisUtils.getInstance().getObject(Constants.ALL_CONFIG_MAP);
        if (null != configMap) {
            value = configMap.get(Constants.PRE_CONFIG + key);
        }
        return value;
    }

}