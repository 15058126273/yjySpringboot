package com.yjy.test.game.controller;

import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * extend from ${@link BasicErrorController}
 * @see BasicErrorController
 * @Author yjy
 * @Date 2018-04-23 13:47
 *
 */
@Controller
public class ErrorController extends AbstractErrorController {

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = "/error")
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = getErrorAttributes(request, false);
//        {"timestamp", "status", "error", "message", "path"}

        // 增加自定义的信息
        addMyErrorMessage(model, status);

        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        return (modelAndView == null ? new ModelAndView(getErrorPath(), model) : modelAndView);
    }

    /**
     * 增加自定义的信息
     * @param model 数据
     * @param status 错误码
     */
    private void addMyErrorMessage(Map<String, Object> model, HttpStatus status) {
        String desc = null;
        switch (status) {
            case NOT_FOUND:
                desc = "搜寻无果"; break;
            default:
                break;
        }
        if (desc != null) {
            model.put("desc", desc);
        }
    }

    @Override
    public String getErrorPath() {
        return "error/error";
    }
}
