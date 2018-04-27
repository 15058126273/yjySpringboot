package com.yjy.test.game.controller.front;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.base.Constants;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.redis.RedisUtils;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.util.StrUtils;
import com.yjy.test.game.web.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页请求处理
 *
 * @author wdy
 * @version ：2017年5月18日 上午9:14:57
 */
@Controller
@Component(value = "frontIndex")
public class IndexController extends BaseFrontController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/config.jtk")
    public void config(Integer num, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Map<String, String> configMap = (Map<String, String>) RedisUtils.getInstance().getObject(Constants.ALL_CONFIG_MAP);
        for (String key : configMap.keySet()) {
            System.out.println("Key: " + key + "=Value:" + configMap.get(key));
        }
        String json = com.alibaba.fastjson.JSONArray.toJSONString(configMap);
        System.out.println(json.toString());//
        ajaxSuccessToJson(response, json);
        RedisUtils.getInstance().setString("test001", "jjjjjjjjjjjjjjjjjjjjjjjjjjj");

        if (null == num)
            num = Integer.valueOf(500);
    }

    /**
     * 检查用户当前的登录状态
     *
     * @param request
     * @param response
     * @author wdy
     * @version ：2017年5月25日 下午5:04:25
     */
    @RequestMapping("/checkLogin.jtk")
    public void checkLogin(String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = FrontUtils.getCurrentUser(request);
            if (null == user && StringUtils.isBlank(token)) {
                errorAjaxToJson(response, "用户登录超时", ErrorCode.ER_NOT_LOGIN);
                return;
            }
            if (StringUtils.isNotBlank(token)) {
                String uid = RedisUtils.getInstance().getString(token);
                if (StringUtils.isNotBlank(uid) && !("null".equals(uid))) {
                    Long userId = Long.valueOf(uid);
                    user = userService.findById(userId);
                    if (null == user) {
                        user = new User();
                        user.setId(userId);
                    }
                    FrontUtils.set2Session(Constants.FRONT_USER, user);
                } else {
                    errorAjaxToJson(response, "用户登录超时", ErrorCode.ER_NOT_LOGIN);
                    return;
                }
            }
            String socketToken = new SimpleDateFormat("yyyyMMdd").format(new Date());
            socketToken += StrUtils.getStringRandom(6);
            RedisUtils.getInstance().setString(socketToken, user.getId().toString());

            ajaxSuccessToJson(response, "{\"token\":\"" + socketToken + "\"}");
            return;
        } catch (Exception e) {
            log.error("验证登陆过程失败", e);
        }
        errorAjaxToJson(response, "用户登录校验失败", ErrorCode.ER_SYSTEM);
        return;
    }

}
