package com.yjy.test.game.controller.back;


import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.util.BackUtils;
import com.yjy.test.game.web.CookieUtils;
import com.yjy.test.util.hibernate.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.yjy.test.util.hibernate.SimplePage.cpn;


/**
 * 前台代理用户的信息管理
 *
 * @author wdy
 * @version ：2017年3月1日 下午8:17:36
 */
@Controller
@Component(value = "backUser")
@RequestMapping("/user")
public class UserController extends BaseBackController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list.do")
    public String list(String succ, String name, Integer status, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            User userDetail = new User();
            if (StringUtils.isNotBlank(name)) {
                userDetail.setNickName(name);
            }
            Pagination p = userService.findListPage(userDetail, cpn(pageNo),
                    CookieUtils.getPageSize(request), new Sort.Order(Sort.Direction.DESC, "id"));
            model.addAttribute("pagination", p);
            model.addAttribute("name", name);
            model.addAttribute("status", status);
            return BackUtils.returnPage(config, "user", "list", request, model);
        } catch (Exception e) {
            log.error("查询代理用户信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查询代理用户的信息出错", null, model);
    }

    @RequestMapping(value = "/detail.do")
    public String detail(Long id, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            User bean = userService.findById(id);
            User userDetail = userService.findById(id);
            model.addAttribute("bean", bean);
            model.addAttribute("userDetail", userDetail);
            return BackUtils.returnPage(config, "user", "detail", request, model);
        } catch (Exception e) {
            log.error("查看代理用户详细信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查看代理用户详细信息出错", "/user/list.do", model);
    }

    /**
     * 禁用账号
     *
     * @param id
     * @return
     * @author wdy
     * @version ：2017年5月16日 下午5:45:58
     */
    @RequestMapping(value = "/delete.do")
    public String delete(Long id, HttpServletRequest request, ModelMap model) {
        if (null == id) {
            message = "提交参数为空";
            return this.list(message, null, null, null, request, model);
        }
        User user = userService.findById(id);
        if (null == user) {
            message = "用户不存在";
            return this.list(message, null, null, null, request, model);
        }
        try {
            userService.update(user);
            message = "删除用户信息成功";
            model.addAttribute("message", message);
        } catch (Exception e) {
            log.error("禁用用户信息出错", e);
        }
        return this.list(message, null, null, null, request, model);
    }

}
