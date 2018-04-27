package com.yjy.test.game.controller.front;


import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.entity.Notice;
import com.yjy.test.game.service.NoticeService;
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
 * 系统公告的控制
 *
 * @author wdy
 * @version ：2017年5月24日 下午4:11:53
 */
@Controller
@Component(value = "frontNotice")
@RequestMapping("/notice")
public class NoticeController extends BaseFrontController {

    private static final Logger log = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;

    /**
     * 最新公告信息
     *
     * @author wdy
     * @version ：2017年6月21日 下午3:58:48
     */
    @RequestMapping(value = "/info.jtk")
    public void info(HttpServletRequest request, HttpServletResponse response) {
        try {
            Notice notice = noticeService.findLastOne(Notice.TYPE_NOTICE);
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            Set<String> include = filter.getIncludes();
            include.add("content");
            include.add("id");
            include.add("updateTime");

            successAjaxToJson(response, JSON.toJSONString(notice, filter, SerializerFeature.BrowserCompatible), "");
            return;
        } catch (Exception e) {
            log.error("查看最新系统公告信息出错", e);
        }
        errorAjaxToJson(response, "查看最新系统公告出错", ErrorCode.ER000801);
        return;
    }

    /**
     * 获取维护信息
     *
     * @author wdy
     * @version ：2017年6月21日 下午3:58:57
     */
    @RequestMapping(value = "/service.jtk")
    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            Notice notice = noticeService.findLastOne(Notice.TYPE_SERVICE);
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            Set<String> include = filter.getIncludes();
            include.add("content");
            include.add("id");
            include.add("updateTime");

            successAjaxToJson(response, JSON.toJSONString(notice, filter, SerializerFeature.BrowserCompatible), "");
            return;
        } catch (Exception e) {
            log.error("查看最新维护信息出错", e);
        }
        errorAjaxToJson(response, "查看最新维护公告出错", ErrorCode.ER000802);
        return;
    }

    @RequestMapping(value = "/save.jtk")
    public void service(String qq, String nickname, Date senddate, String content, String sign, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.error("I am qq: " + qq);
            log.error("I am nickname: " + nickname);
            log.error("I am senddate: " + senddate.toString());
            log.error("I am content: " + content);
            log.error("I am sign: " + sign);
            String newString = content.replaceAll("[\\t\\n\\r]", "<br>");
            System.out.println(newString);
            String[] ss = content.split("(\r\n|\r|\n|\n\r)");
            for (int i = 0; i < ss.length; i++) {
                System.out.println(i + ":" + ss[i]);
            }
            String hh = content.replaceAll("\n", "<br />");
            System.out.println("nn:" + hh);

            hh = content.replaceAll("\r", "<br />");
            System.out.println("rr:" + hh);

            Notice notice = new Notice();
            notice.setType(Notice.TYPE_NOTICE);
            notice.setIsDelete(Integer.valueOf(0));
            content = "qq:" + qq + ";nickname:" + nickname + ";content:" + content + ";sign:" + sign + ".";
            notice.setAddTime(senddate);
            notice.setUpdateTime(new Date());
            notice.setContent(content);
            noticeService.save(notice);
            successAjaxToJson(response, null, "");
            return;
        } catch (Exception e) {
            log.error("保存qq群消息出错", e);
        }
        errorAjaxToJson(response, "保存信息出错", ErrorCode.ER000802);
        return;
    }


}
