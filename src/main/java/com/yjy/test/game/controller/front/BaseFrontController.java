package com.yjy.test.game.controller.front;


import com.yjy.test.game.base.BaseAction;
import com.yjy.test.game.util.HttpSendUtils;
import com.yjy.test.game.util.MD5;
import com.yjy.test.game.util.UnicodeUtil;
import com.yjy.test.game.web.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;


import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * 前端的基类controller
 *
 * @author wdy
 * @version ：2016年8月25日 上午11:14:10
 */
@Controller
public class BaseFrontController extends BaseAction {

    public static final String SUCCESS = "success";//操作成功的标识

    //加密key
    public static final String MD5_KEY = "frerewrwrwerwer";

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

    protected String signString(String token) {
        String md5Str = null;
        if (StringUtils.isBlank(token)) {
            return md5Str;
        }
        token = token + "=" + MD5_KEY;
        md5Str = MD5.MD5Encode(token).toLowerCase();
        return md5Str;
    }

    /**
     * 检查接口是否合法
     *
     * @param parames
     * @param sign
     * @return
     * @author wdy
     * @version ：2017年5月18日 下午7:09:08
     */
    protected boolean checkAPI(Map<String, Object> parames, String sign) {
        boolean succ = false;
        if (StringUtils.isBlank(sign))
            return succ;
        String newSign = HttpSendUtils.signKey(parames);
        if (sign.equals(newSign)) {
            succ = true;
        }
        return succ;
    }

    protected void errorAjaxToJson(HttpServletResponse response, String errorInfo, ErrorCode code) {
        ajaxToJson(response, null, errorInfo, 0, code);
    }

    protected void successAjaxToJson(HttpServletResponse response, String jsonString, String info) {
        ajaxToJson(response, jsonString, info, 1, null);
    }
}
