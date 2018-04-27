package com.yjy.test.game.controller.back;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.base.Constants;
import com.yjy.test.game.entity.RoomUser;
import com.yjy.test.game.service.RoomUserService;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.util.DateUtils;
import com.yjy.test.game.web.CookieUtils;
import com.yjy.test.game.web.ErrorCode;
import com.yjy.test.util.hibernate.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import static com.yjy.test.util.hibernate.SimplePage.cpn;

/**
 * 游戏接口
 *
 * @author wdy
 * @version ：2017年9月28日 上午10:28:18
 */
@Controller
@RequestMapping("/game")
public class GameAPIController extends BaseBackController {

    public static final Logger log = LoggerFactory.getLogger(GameAPIController.class);

    @Autowired
    private RoomUserService roomUserService;

    @RequestMapping(value = "/scores.do")
    public void scores(String code, String roomNo, Date start, Date end, Integer pageNo, String sign, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> parames = new HashMap<String, Object>();
            parames.put(Constants.API_URL, "gameScores.list");
            parames.put("code", code);
            parames.put("roomNo", roomNo);
            parames.put("start", start);
            parames.put("end", end);
            parames.put("pageNo", pageNo);

            boolean succ = checkAPI(parames, sign);
            if (!succ) {
                ajaxErrorToJson(response, null, "接口校验有误", ErrorCode.ER000501);
                return;
            }

            RoomUser roomUser = new RoomUser();
            if (StringUtils.isNotBlank(code)) {
                roomUser.setCode(code);
            }
            if (StringUtils.isNotBlank(roomNo)) {
                roomUser.setRoomNo(roomNo);
            }

            Pagination p = roomUserService.findRoomScoresBy(roomUser, start, end, cpn(pageNo), CookieUtils.getPageSize(request));
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(RoomUser.class,
                    "id", "roomId", "userId", "roomNo", "integral", "nickName", "code", "comeTime");
            ajaxSuccessToJson(response, JSON.toJSONString(p, filter, SerializerFeature.BrowserCompatible));
            return;
        } catch (Exception e) {
            log.error("接口查询玩家战绩信息出错", e);
        }
        ajaxErrorToJson(response, null, "查询玩家战绩信息出错");
    }

    /**
     * 统计玩家战绩信息出错
     *
     * @param userId
     * @param start
     * @return
     * @author wdy
     * @version ：2017年9月27日 上午10:47:12
     */
    @RequestMapping(value = "/dayScore.do")
    public void dayScore(Long userId, Date start, String sign, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> parames = new HashMap<String, Object>();
            parames.put(Constants.API_URL, "dayScores.list");
            parames.put("userId", userId);
            parames.put("start", start);

            boolean succ = checkAPI(parames, sign);
            if (!succ) {
                ajaxErrorToJson(response, "", "接口校验有误", ErrorCode.ER000501);
                return;
            }

            if (null == userId) {
                ajaxErrorToJson(response, null, "提交查看的参数为空");
                return;
            }

            Date end = null;
            if (null == start) {
                start = new Date();
            }
            end = DateUtils.getFinallyDate(start);
            start = DateUtils.getDateByDay(end, Integer.valueOf(-7));

            RoomUser roomUser = new RoomUser();
            roomUser.setUserId(userId);
            List<RoomUser> list = roomUserService.findDayScores(roomUser, start, end);

            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(RoomUser.class,
                    "integral", "comeTime");
            ajaxSuccessToJson(response, JSON.toJSONString(list, filter, SerializerFeature.BrowserCompatible));
            return;
        } catch (Exception e) {
            log.error("接口统计玩家战绩信息出错", e);
        }
        ajaxErrorToJson(response, null, "统计玩家的战绩信息出错");
    }

}
