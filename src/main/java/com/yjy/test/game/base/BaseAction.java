package com.yjy.test.game.base;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.yjy.test.base.BaseClass;
import com.yjy.test.game.util.UnicodeUtil;
import com.yjy.test.game.web.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;


@Controller
public class BaseAction extends BaseClass {

    public static final Logger log = LoggerFactory.getLogger(BaseAction.class);

    public static final String MESSAGE = "msg";//页面信息提醒

    public String message;//提醒消息

    /**
     * ajax 返回成功Json数据
     *
     * @param response
     * @param jsonString json格式数据
     */
    protected void ajaxSuccessToJson(HttpServletResponse response, String jsonString) {
        ajaxToJson(response, jsonString, "", 1, null);
    }

    protected void ajaxSuccessToJson(HttpServletResponse response, String jsonString, ErrorCode code) {
        ajaxToJson(response, jsonString, "", 1, code);
    }

    protected void ajaxErrorToJson(HttpServletResponse response, String jsonString, String info, ErrorCode code) {
        ajaxToJson(response, jsonString, info, 0, code);
    }

    protected void ajaxErrorToJson(HttpServletResponse response, String jsonString, String info) {
        ajaxToJson(response, jsonString, info, 0, null);
    }

    public String getMessage() {
        return message;
    }

    protected ModelMap setMessage(String message, ModelMap model) {
        this.message = message;
        model.addAttribute("message", message);
        return model;
    }

    /**
     * ajax 返回 json 数据
     *
     * @param response
     * @param jsonString 待返回的JSON数据
     * @param info       描述信息，一般为有错误时使用
     * @param status     返回数据的状态，1为成功，0为失败
     * @param code       错误代码
     */
    private void ajaxToJson(HttpServletResponse response, String jsonString, String info, Integer status, ErrorCode code) {
        StringBuffer result = new StringBuffer("{\"status\":");
        result.append(status).append(",\"data\":")
                .append(jsonString).append(",\"info\":\"")
                .append(info == null ? "" : UnicodeUtil.toEncodedUnicode(info.trim(), false)).append("\",\"code\":\"")
                .append(code).append("\"}");

        try {
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", -1);
            //response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
//            response.getOutputStream().print(result.toString());
            response.getWriter().print(result);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("无法返回请求数据", e);
            }
        }
    }


}
