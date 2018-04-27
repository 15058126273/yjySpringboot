package com.yjy.test.game.controller.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.base.Constants;
import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.service.AdminService;
import com.yjy.test.game.service.ConfigService;
import com.yjy.test.game.service.LoginRecordService;
import com.yjy.test.game.util.APICodeUtils;
import com.yjy.test.game.util.BackUtils;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AdminLoginController extends BaseBackController {

    public static final Logger log = LoggerFactory.getLogger(AdminLoginController.class);

    @Autowired
    private ConfigService configService;
    @Autowired
    private AdminService adminUserService;
    @Autowired
    private LoginRecordService loginService;

    /**
     * 后台初始地址
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request, ModelMap model) {
        Config config = configService.findThisConfig();
        return BackUtils.returnPage(config, "index", "index", request, model);
    }

    @RequestMapping(value = "/welcome.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String welcome(HttpServletRequest request, ModelMap model) {
        Config config = configService.findThisConfig();
        return BackUtils.returnPage(config, "index", "welcome", request, model);
    }

    /**
     * 后台登录
     *
     * @param userName
     * @param password
     * @param request
     * @param model
     * @return
     * @author wdy
     * @version ：2017年1月9日 下午7:03:38
     */
    @RequestMapping(value = "/login.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(String userName, String password, HttpServletRequest request, ModelMap model) {
        Config config = configService.findThisConfig();
        Admin user;
        String message = null;
        if (StringUtils.isNotBlank(userName)) {
            Admin au = new Admin();
            String md5 = MD5.MD5Encode(password);
            au.setUserName(userName);
            List<Admin> list = adminUserService.findList(au);
            if (list != null && list.size() > 0) {
                user = list.get(0);
                if (md5.equals(user.getPassword())) {
                    if (Integer.valueOf(1).equals(user.getStatus())) {
                        try {
                            request.getSession().setAttribute(Constants.ADMIN_USER, user);
                            loginService.loginLog(request, user.getId());
                            return BackUtils.returnPage(config, "index", "index", request, model);
                        } catch (Exception e) {
                            log.error("管理员登入出错了！", e);
                            message = "登入出错了！";
                        }
                    } else {
                        message = "账号被禁用！";
                    }
                } else {
                    message = "密码错误！";
                }
            } else {
                message = "用户名不存在！";
            }
        }
        model.addAttribute("message", message);
        return BackUtils.returnPage(config, "index", "login", request, model);
    }

    /**
     * 注销
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/loginOut.do")
    public String loginOut(HttpServletRequest request, ModelMap model) {
        request.getSession().removeAttribute(Constants.ADMIN_USER);
        return "redirect:login.do";
    }

    /**
     * 获取游戏的当前的数据信息
     *
     * @param request
     * @param response
     * @author wdy
     * @version ：2017年9月13日 下午6:02:43
     */
    @RequestMapping(value = "/gameInfo.do")
    public void gameInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = FrontUtils.configParame(Constants.CONFIG_BROADCAST_URL);
            String succ = APICodeUtils.gameCurrent(url);
            if (StringUtils.isNotBlank(succ)) {
                ajaxSuccessToJson(response, succ);
                return;
            }
        } catch (Exception e) {
            log.error("获取游戏当前信息出错", e);
        }
        ajaxErrorToJson(response, null, "获取游戏当前信息出错");
    }

}
