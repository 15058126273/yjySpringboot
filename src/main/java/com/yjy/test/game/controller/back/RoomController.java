package com.yjy.test.game.controller.back;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.Room;
import com.yjy.test.game.entity.RoomUser;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.service.RoomService;
import com.yjy.test.game.service.RoomUserService;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.util.BackUtils;
import com.yjy.test.game.util.DateUtils;
import com.yjy.test.game.web.CookieUtils;
import com.yjy.test.util.hibernate.Condition;
import com.yjy.test.util.hibernate.OrderBy;
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
 * 房间信息管理查看
 *
 * @author wdy
 * @version ：2017年6月30日 上午11:59:13
 */
@Controller
@Component(value = "backRoom")
@RequestMapping("/room")
public class RoomController extends BaseBackController {

    public static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomUserService roomUserService;
    @Autowired
    private UserService userDetailService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/list.do")
    public String list(String succ, Long userId, Integer status, String roomNo, Date start, Date end, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            Room room = new Room();
            if (null != userId) {
                room.setUserId(userId);
            }
            if (null != status) {
                room.setCloseType(status);
            }
            if (StringUtils.isNotBlank(roomNo)) {
                room.setRoomNo(roomNo);
            }
            Pagination p;
            if (null == start && null == end && !(Integer.valueOf(-1).equals(status))) {
                p = roomService.findListPage(room, cpn(pageNo),
                        CookieUtils.getPageSize(request), new Sort.Order(Sort.Direction.DESC, "id"));
            } else {
                p = roomService.findRoomBy(room, start, end, cpn(pageNo), CookieUtils.getPageSize(request));
            }
            List<Room> list = (List<Room>) p.getList();
            for (Room ro : list) {
                Long uId = ro.getUserId();
                User detail = userDetailService.findById(uId);
                if (null != detail) {
                    ro.setNickName(detail.getNickName());
                }
            }
            p.setList(list);
            model.addAttribute("pagination", p);
            model.addAttribute("userId", userId);
            model.addAttribute("status", status);
            model.addAttribute("roomNo", roomNo);
            model.addAttribute("start", start);
            model.addAttribute("end", end);
            return BackUtils.returnPage(config, "room", "list", request, model);
        } catch (Exception e) {
            log.error("查询房间信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查询房间的信息出错", null, model);
    }

    /**
     * 查看房间的参与用户
     *
     * @param id
     * @param pageNo
     * @author wdy
     * @version ：2017年6月30日 下午2:46:21
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/items.do")
    public String items(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            if (null == id) {
                message = "房间信息不存在";
                return BackUtils.returnErrorPage(config, request, message, null, model);
            }
            RoomUser roomUser = new RoomUser();
            roomUser.setRoomId(id);
            Pagination p = roomUserService.findListPage(roomUser, cpn(pageNo),
                    CookieUtils.getPageSize(request), new Sort.Order(Sort.Direction.ASC, "id"));
            List<RoomUser> list = (List<RoomUser>) p.getList();
            for (RoomUser ru : list) {
                Long uId = ru.getUserId();
                User detail = userDetailService.findById(uId);
                if (null != detail) {
                    ru.setNickName(detail.getNickName());
                }
            }
            p.setList(list);
            model.addAttribute("pagination", p);
            return BackUtils.returnPage(config, "room", "items", request, model);
        } catch (Exception e) {
            log.error("查询房间参与用户信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查询房间的参与用户信息出错", null, model);
    }

    /**
     * 查看战绩列表
     *
     * @return
     * @author wdy
     * @version ：2017年9月26日 下午5:51:32
     */
    @RequestMapping(value = "/scores.do")
    public String scores(String code, String roomNo, Date start, Date end, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            RoomUser roomUser = new RoomUser();
            if (StringUtils.isNotBlank(code)) {
                roomUser.setCode(code);
            }
            if (StringUtils.isNotBlank(roomNo)) {
                roomUser.setRoomNo(roomNo);
            }
            Pagination p = roomUserService.findRoomScoresBy(roomUser, start, end, cpn(pageNo), CookieUtils.getPageSize(request));
            model.addAttribute("pagination", p);
            model.addAttribute("code", code);
            model.addAttribute("roomNo", roomNo);
            model.addAttribute("start", start);
            model.addAttribute("end", end);
            return BackUtils.returnPage(config, "room", "scores", request, model);
        } catch (Exception e) {
            log.error("查询玩家战绩信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查询玩家的战绩信息出错", "/room/scores.do", model);
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
    public String dayScore(Long userId, Date start, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            if (null == userId) {
                return BackUtils.returnErrorPage(config, request, "提交查看的参数为空", "/room/scores.do", model);
            }
            Date end = null;
            if (null == start) {
                start = new Date();
            }
            end = DateUtils.getFinallyDate(start);
            start = DateUtils.getDateByDay(end, Integer.valueOf(-7));

            User user = userDetailService.findById(userId);
            RoomUser roomUser = new RoomUser();
            roomUser.setUserId(userId);
            List<RoomUser> list = roomUserService.findDayScores(roomUser, start, end);
            model.addAttribute("list", list);
            model.addAttribute("player", user);

            return BackUtils.returnPage(config, "room", "day_score", request, model);
        } catch (Exception e) {
            log.error("统计玩家战绩信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "统计玩家的战绩信息出错", "/room/scores.do", model);
    }


}
