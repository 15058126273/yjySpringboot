package com.yjy.test.game.controller.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.entity.User;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.util.pojo.DepositLog;
import com.yjy.test.game.web.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 充值记录的控制
 *
 * @author wdy
 * @version ：2017年5月24日 上午11:58:51
 */
@Controller
@Component(value = "frontDeposit")
@RequestMapping("/deposit")
public class DepositLogController extends BaseFrontController {

    private static final Logger log = LoggerFactory.getLogger(DepositLogController.class);

    @RequestMapping(value = "/records.jtk")
    public void records(Integer pageNo, HttpServletRequest request, HttpServletResponse response) {
        User user = FrontUtils.getCurrentUser(request);
        Long userId = user.getId();
        try {
            DepositLog depositLog = new DepositLog();
            depositLog.setUserId(userId);
            return;
        } catch (Exception e) {
            log.error("查看用户的充值记录出错", e);
        }
        errorAjaxToJson(response, "查看用户的充值记录出错", ErrorCode.ER000202);
        return;
    }

}
