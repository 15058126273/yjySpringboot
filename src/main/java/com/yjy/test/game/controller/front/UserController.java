package com.yjy.test.game.controller.front;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.entity.User;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.web.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * 游戏参与用户的接口管理
 *
 * @author wdy
 * @version ：2017年5月19日 上午9:45:09
 */
@Controller
@Component(value = "frontUser")
@RequestMapping("/user")
public class UserController extends BaseFrontController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 根据昵称查询用户列表
     *
     * @author wdy
     * @version ：2017年5月18日 下午6:55:37
     */
    @RequestMapping(value = "/info.jtk")
    public void info(HttpServletRequest request, HttpServletResponse response) {
        User user = FrontUtils.getCurrentUser(request);
        Long userId = user.getId();
        try {
            User userDetail = userService.findBeanById(userId);

            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            Set<String> include = filter.getIncludes();
            include.add("id");
            include.add("headImg");
            include.add("nickName");
            include.add("money");
            include.add("code");
            include.add("bindMobile");
            include.add("mobile");

            successAjaxToJson(response, JSON.toJSONString(userDetail, filter, SerializerFeature.BrowserCompatible), "");
            return;
        } catch (Exception e) {
            log.error("根据昵称查询用户信息出错", e);
        }
        errorAjaxToJson(response, "查询信息出错", ErrorCode.ER000103);
        return;
    }


}
