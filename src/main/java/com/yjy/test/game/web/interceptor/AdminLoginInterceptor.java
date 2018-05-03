package com.yjy.test.game.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.service.ConfigService;
import com.yjy.test.game.util.BackUtils;
import com.yjy.test.game.util.UnicodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台上下文登录检测
 *
 * @author wdy
 * @version ：2016年2月29日 下午6:22:56
 */
// 这里导入前缀为 back.login 的配置参数
@ConfigurationProperties(prefix = "back.login")
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(AdminLoginInterceptor.class);

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
        String uri = getURI(request);
        if (exclude(uri)) {
            return true;
        }
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            Admin user = BackUtils.getCurrentUser(request);
            if (null == user) {
                String requestType = request.getHeader("X-Requested-With");
                if (requestType != null && requestType.equals("XMLHttpRequest")) {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    response.getOutputStream().print("{\"status\":2, \"code\":\"login\", \"info\":\""
                            + UnicodeUtil.toEncodedUnicode("登录超时，请重新登录", false)
                            + "\", \"data\":null}");
                    return false;
                }
                Config config = configService.findThisConfig();
                String path = null;
                if (null != config) {
                    path = StringUtils.isNotBlank(request.getContextPath()) ? request.getContextPath() : "";
                }
                String reLogin = "/manager/admin/login.do";
                if (StringUtils.isNotBlank(path) && path.length() > 1) {
                    reLogin = path + reLogin;
                }
                response.sendRedirect(reLogin);
                return false;
            }
        } catch (Exception e) {
            log.error("检查后台登录参数出错", e);
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
     * @throws IllegalStateException 访问路径错误，没有三(四)个'/'
     */
    private static String getURI(HttpServletRequest request)
            throws IllegalStateException {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        int start = 0, i = 0, count = 2;
        if (!StringUtils.isBlank(ctxPath)) {
            count++;
        }
        while (i < count && start != -1) {
            start = uri.indexOf('/', start + 1);
            i++;
        }

        if (start <= 0) {
            throw new IllegalStateException(
                    "admin access path not like '/manager/admin/...' pattern: "
                            + uri);
        }
        return uri.substring(start);
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