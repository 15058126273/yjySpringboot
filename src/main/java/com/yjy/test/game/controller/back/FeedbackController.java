package com.yjy.test.game.controller.back;


import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.Feedback;
import com.yjy.test.game.service.FeedbackService;
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
 * 玩家反馈信息控制查看
 *
 * @author wdy
 * @version ：2017年5月27日 上午11:07:11
 */
@Controller
@Component(value = "backFeedback")
@RequestMapping("/feedback")
public class FeedbackController extends BaseBackController {

    public static final Logger log = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "/list.do")
    public String list(String name, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            Feedback feedback = new Feedback();
            if (StringUtils.isNotBlank(name)) {
                feedback.setContent(name);
            }
            Pagination p = feedbackService.findListPage(feedback, cpn(pageNo), CookieUtils.getPageSize(request),
                    new Sort.Order(Sort.Direction.DESC, "id"));
            model.addAttribute("pagination", p);
            model.addAttribute("name", name);
            return BackUtils.returnPage(config, "feedback", "list", request, model);
        } catch (Exception e) {
            log.error("查询玩家反馈信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查看玩家的反馈的信息出错", null, model);
    }

    /**
     * 反馈的详细信息
     *
     * @param id
     * @return
     * @author wdy
     * @version ：2017年5月27日 上午11:13:42
     */
    @RequestMapping(value = "/detail.do")
    public String detail(Integer id, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            if (null == id) {
                return BackUtils.returnErrorPage(config, request, "提交查看的参数为空", null, model);
            }
            Feedback bean = feedbackService.findById(id);
            model.addAttribute("bean", bean);
            return BackUtils.returnPage(config, "feedback", "detail", request, model);
        } catch (Exception e) {
            log.error("查询玩家反馈信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查询玩家反馈信息出错", "/feedback/list.do", model);
    }

}
