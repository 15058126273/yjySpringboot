package com.yjy.test.game.controller.front;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.entity.Feedback;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.service.FeedbackService;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.web.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 用户反馈的控制
 *
 * @author wdy
 * @version ：2017年5月24日 下午4:11:53
 */
@Controller
@Component(value = "frontFeedback")
@RequestMapping("/feedback")
public class FeedbackController extends BaseFrontController {

    private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "/save.jtk")
    public void save(String content, HttpServletRequest request, HttpServletResponse response) {
        User user = FrontUtils.getCurrentUser(request);
        Long userId = user.getId();
        if (StringUtils.isBlank(content)) {
            errorAjaxToJson(response, "提交信息为空", ErrorCode.ER_NOT_ALL_SUBMIT);
            return;
        }
        try {
            Feedback feedback = new Feedback();
            feedback.setUserId(userId);
            feedback.setContent(content);
            feedback.setAddTime(new Date());

            feedback = feedbackService.save(feedback);
            if (null != feedback) {
                successAjaxToJson(response, null, "");
                return;
            }
        } catch (Exception e) {
            log.error("保存用户反馈信息出错", e);
        }
        errorAjaxToJson(response, "保存信息出错", ErrorCode.ER000301);
        return;
    }


}
