package com.yjy.test.game.controller.back;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.base.Constants;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.LoginLog;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.service.LoginLogService;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.util.APICodeUtils;
import com.yjy.test.game.util.BackUtils;
import com.yjy.test.game.util.PaginationUtils;
import com.yjy.test.util.hibernate.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.yjy.test.util.hibernate.SimplePage.cpn;

/**
 * 前台用户登录日志
 *
 * @author wdy
 * @version ：2017年6月30日 下午3:01:58
 */
@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseBackController {

    public static final Logger logger = LoggerFactory.getLogger(LoginLogController.class);

    public static final String LOGIN_LOG_INFO = "game/logins.jtk";//登录接口

    @Autowired
    private UserService userDetailService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/list.do")
    public String list(Long userId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            Pagination p = null;
            String loginUrl = com.yjy.test.game.base.Config.playerCenterUrl;
            loginUrl += LOGIN_LOG_INFO;
            String json = APICodeUtils.loginLogs(loginUrl, null, null, cpn(pageNo));
            if (StringUtils.isNotBlank(json)) {
                p = PaginationUtils.jsonToPage(json, LoginLog.class);
            } else {
                p = new Pagination(1, 0, 0);
            }
            List<LoginLog> list = (List<LoginLog>) p.getList();
            for (LoginLog login : list) {
                Long uId = login.getUserId();
                User detail = userDetailService.findById(uId);
                if (null != detail) {
                    login.setNickName(detail.getNickName());
                }
            }
            p.setList(list);
            model.addAttribute("pagination", p);
            model.addAttribute("userId", userId);
            return BackUtils.returnPage(config, "login_log", "list", request, model);
        } catch (Exception e) {
            log.error("查询玩家登录信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查询玩家登录信息出错", null, model);
    }


}
