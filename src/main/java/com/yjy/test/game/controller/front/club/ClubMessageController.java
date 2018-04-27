package com.yjy.test.game.controller.front.club;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.controller.front.BaseFrontController;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.entity.club.Club;
import com.yjy.test.game.entity.club.ClubMessage;
import com.yjy.test.game.service.club.ClubMessageService;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.web.ErrorCode;
import com.yjy.test.util.hibernate.SimplePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;


/**
 * 俱乐部消息接口管理
 *
 * @author yjy
 * Created on 2017年12月6日 上午11:33:38
 */
@Controller
@RequestMapping("/clubMessage")
public class ClubMessageController extends BaseFrontController {

    public static final Logger log = LoggerFactory.getLogger(ClubMessageController.class);

    @Autowired
    private ClubMessageService clubMessageService;

    /**
     * 获取新消息数量
     *
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月15日 上午11:57:48
     */
    @RequestMapping(value = "/count.jtk")
    public void newCount(HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            User user = FrontUtils.getCurrentUser(request);
            ClubMessage clubMessage = new ClubMessage();
            clubMessage.setReceiveId(user.getId());
            clubMessage.setStatus(ClubMessage.STATUS_NEW);
            clubMessage.setIsDelete(ClubMessage.NO);
            long count = clubMessageService.findCount(clubMessage);
            successAjaxToJson(response, String.valueOf(count), SUCCESS);
            return;
        } catch (Exception e) {
            info = "获取新消息数量出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 获取我的消息列表
     *
     * @param pageNo   页号
     * @param pageSize 条数
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月5日 下午2:04:25
     */
    @RequestMapping(value = "/list.jtk")
    public void list(Integer status, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 获取列表
            User user = FrontUtils.getCurrentUser(request);
            List<ClubMessage> list = clubMessageService.findMyList(user.getId(), status, SimplePage.cpn(pageNo), SimplePage.cps(pageSize));
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Club.class,
                    "id", "nickName", "headImg", "clubName", "code", "type", "status", "remark", "sendId", "addTime");
            successAjaxToJson(response, JSON.toJSONString(list, filter, SerializerFeature.BrowserCompatible), SUCCESS);
            return;
        } catch (Exception e) {
            info = "获取我的消息列表出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 群主审核申请
     *
     * @param id       消息id
     * @param result   审核结果
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月6日 下午1:28:52
     */
    @RequestMapping(value = "/check.jtk", method = RequestMethod.POST)
    public void check(Long id, Integer result, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 验证参数
            if (id != null && result != null) {
                User user = FrontUtils.getCurrentUser(request);
                ClubMessage clubMessage = clubMessageService.findById(id);
                // 验证用户
                if (clubMessage != null && clubMessage.getReceiveId().equals(user.getId())) {
                    // 验证状态
                    if (clubMessage.getType().equals(ClubMessage.TYPE_APPLY) && clubMessage.getStatus().equals(ClubMessage.STATUS_NEW)) {
                        // 审核
                        clubMessage = clubMessageService.check(clubMessage, result);
                        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Club.class,
                                "id", "nickName", "headImg", "clubName", "code", "type", "status", "remark", "sendId", "addTime");
                        successAjaxToJson(response, JSON.toJSONString(clubMessage, filter, SerializerFeature.BrowserCompatible), SUCCESS);
                        return;
                    } else {
                        info = "不在待审核状态";
                        code = ErrorCode.ER001010;
                    }
                } else {
                    info = "消息不存在";
                    code = ErrorCode.ER001011;
                }
            } else {
                info = "参数不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "审核出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 删除消息
     *
     * @param id       消息id
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月6日 下午2:02:42
     */
    @RequestMapping(value = "/delete.jtk", method = RequestMethod.POST)
    public void delete(Long id, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 验证参数
            if (id != null) {
                User user = FrontUtils.getCurrentUser(request);
                ClubMessage clubMessage = clubMessageService.findById(id);
                // if 消息存在 & 接收人一致 & 未删除
                if (clubMessage != null && clubMessage.getReceiveId().equals(user.getId()) && clubMessage.getIsDelete().equals(ClubMessage.NO)) {
                    // if 不是申请消息 || 已操作
                    if (!clubMessage.getType().equals(ClubMessage.TYPE_APPLY) || clubMessage.getStatus().equals(ClubMessage.STATUS_OLD)) {
                        // 删除
                        clubMessage.setIsDelete(ClubMessage.YES);
                        clubMessage.setUpdateTime(new Date());
                        clubMessageService.update(clubMessage);
                        successAjaxToJson(response, null, SUCCESS);
                        return;
                    } else {
                        info = "消息未审核";
                        code = ErrorCode.ER001012;
                    }
                } else {
                    info = "消息不存在";
                    code = ErrorCode.ER001011;
                }
            } else {
                info = "参数不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "删除消息出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

}
