package com.yjy.test.game.web.interceptor;

import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.service.ConfigService;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.web.ErrorCode;
import com.yjy.test.util.UnicodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-24 15:03
 */
@ConfigurationProperties(prefix = "front.login")
public class FrontLoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(FrontLoginInterceptor.class);

    // 例外
    private List<String> excludeUrls = new ArrayList<>();
    private ConfigService configService;

    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.info("FrontLoginInterceptor > excludeUrls: {}", excludeUrls);
        String uri = getURI(request);
        if (exclude(uri)) {
            return true;
        }

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            User user = FrontUtils.getCurrentUser(request);
            if (null == user) {
                Enumeration s = request.getHeaderNames();
                String requestType = request.getHeader("X-Requested-With");
                if (requestType != null && requestType.equals("XMLHttpRequest")) {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    response.getOutputStream().print("{\"status\":0,\"info\":\""
                            + UnicodeUtil.toEncodedUnicode( "登录超时，请重新登录", false)
                            + "\", \"data\":null, \"code\": \"" + ErrorCode.ER_NOT_LOGIN + "\"}" );
                    return false;
                }
                Config config = configService.findThisConfig();
                String path = null;
                if(null != config){
                    path = StringUtils.isNotBlank(config.getContextPath()) ? config.getContextPath():"";
                }
                String reLogin = "/autho.jtk";
                if(StringUtils.isNotBlank(path) && path.length() > 1) {
                    reLogin = path + reLogin;
                }
                response.sendRedirect(reLogin);
                return false;
            }
        } catch (Exception e) {
            log.error("检查前台登录参数出错", e);
        }

        return super.preHandle(request, response, handler);
    }


    private boolean exclude(String uri) {
        if (excludeUrls != null) {
            for (String exc : excludeUrls) {
                if (exc.equals(uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得第三个路径分隔符的位置
     *
     * @param request
     * @throws IllegalStateException
     *             访问路径错误，没有三(四)个'/'
     */
    private static String getURI(HttpServletRequest request)
            throws IllegalStateException {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        return uri;
    }

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public ConfigService getConfigService() {
        return configService;
    }
}
