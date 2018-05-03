package com.yjy.test.game.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.entity.Room;
import com.yjy.test.game.entity.RoomUser;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.service.RoomService;
import com.yjy.test.game.service.RoomUserService;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.tencent.pojo.RoomShare;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.web.CookieUtils;
import com.yjy.test.game.web.ErrorCode;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import static com.yjy.test.util.hibernate.SimplePage.cpn;

/**
 * 房间用户等的接口管理
 *
 * @author wdy
 * @version ：2017年5月19日 上午9:45:09
 */
@Controller
@Component(value = "frontRoom")
@RequestMapping("/room")
public class RoomController extends BaseFrontController {

    public static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomUserService roomUserService;
    @Autowired
    private UserService userDetailService;

    /**
     * 查询用户的所有房间的战绩
     *
     * @param pageNo
     * @param request
     * @param response
     * @author wdy
     * @version ：2017年5月25日 上午9:10:16
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/records.jtk")
    public void records(Integer pageNo, HttpServletRequest request, HttpServletResponse response) {
        User user = FrontUtils.getCurrentUser(request);
        Long userId = user.getId();
        try {
            RoomUser roomUser = new RoomUser();
            roomUser.setUserId(userId);
            roomUser.setIsPlayer(YES);
            Condition[] conds = {OrderBy.desc("id")};

            Pagination p = roomUserService.findListPage(roomUser, cpn(pageNo), CookieUtils.getPageSize(request));
            List<RoomUser> list = (List<RoomUser>) p.getList();
            Integer roomGameNum = null;
            Integer gameMode = null;
            for (RoomUser ru : list) {
                Long roomId = ru.getRoomId();
                Room room = roomService.findById(roomId);
                if (null != room) {
                    roomGameNum = room.getGameNum();
                    gameMode = room.getGameMode();
                }
                ru.setRoomGameNum(roomGameNum);
                ru.setGameMode(gameMode);
            }
            p.setList(list);
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            filter = new SimplePropertyPreFilter(RoomUser.class, "id", "roomId", "roomNo", "roomBaseWager", "addTime", "roomGameNum", "gameMode");
            successAjaxToJson(response, JSON.toJSONString(p, filter, SerializerFeature.BrowserCompatible, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty), "");
            return;
        } catch (Exception e) {
            log.error("用户的战绩信息出错", e);
        }
        errorAjaxToJson(response, "查询用户的战绩出错", ErrorCode.ER000501);
        return;
    }

    /**
     * 该房间所有用户的战绩
     *
     * @param id
     * @param pageNo
     * @param request
     * @param response
     * @author wdy
     * @version ：2017年6月13日 下午6:30:28
     */
    @RequestMapping(value = "/detail.jtk")
    public void detail(Long id, Integer pageNo, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (null == id) {
                errorAjaxToJson(response, "提交请求参数缺少", ErrorCode.ER_NOT_ALL_SUBMIT);
                return;
            }
            //房间状态
            Room room = roomService.findById(id);
            Integer roomStatus = null;
            Integer bankerMode = null;
            Integer roomGameNum = null;
            Integer roomBaseWager = null;
            if (null != room) {
                roomStatus = room.getStatus();
                roomGameNum = room.getGameNum();
            }

            RoomUser bean = new RoomUser();
            bean.setRoomId(id);
            bean.setIsPlayer(YES);
            List<RoomUser> list = roomUserService.findList(bean, new Sort.Order(Sort.Direction.ASC, "id"));
            for (RoomUser ru : list) {
                Long userId = ru.getUserId();
                User userDetail = userDetailService.findById(userId);
                ru.setNickName(userDetail.getNickName());
                ru.setCode(userDetail.getCode());
                ru.setHeadImg(userDetail.getHeadImg());
                ru.setRoomStatus(roomStatus);
                ru.setRoomGameNum(roomGameNum);
            }
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            filter = new SimplePropertyPreFilter(RoomUser.class, "id", "userId", "code", "roomNo", "initIntegral", "integral", "addTime",
                    "roomBaseWager", "roomStatus", "nickName", "headImg", "roomGameNum", "bankerMode");
            successAjaxToJson(response, JSON.toJSONString(list, filter, SerializerFeature.BrowserCompatible,
                    SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty), "");
            return;

        } catch (Exception e) {
            log.error("查询战绩详细信息出错", e);
        }
        errorAjaxToJson(response, "查询战绩的详细信息出错", ErrorCode.ER000502);
        return;
    }

    /**
     * 用户分享后的房间的信息
     *
     * @param roomId           房间的id
     * @param code：用户的code
     * @author wdy
     * @version ：2017年6月8日 下午4:06:24
     */
    @RequestMapping(value = "/share.jtk")
    public void share(Long roomId, String code, HttpServletRequest request, HttpServletResponse response) {
        if (null == roomId || StringUtils.isBlank(code)) {
            errorAjaxToJson(response, "提交请求参数缺少", ErrorCode.ER_NOT_ALL_SUBMIT);
            return;
        }
        try {
            Room room = roomService.findById(roomId);
            User userDetail = userDetailService.findByCode(code);
            if (null == room || null == userDetail) {
                errorAjaxToJson(response, "提交请求参数缺少", ErrorCode.ER000503);
                return;
            }

            RoomShare roomShare = new RoomShare();
            roomShare.setRoomNo(room.getRoomNo());
            roomShare.setRoomRoundNum(room.getGameNum());

            roomShare.setMyHeader(userDetail.getHeadImg());
            roomShare.setMyNickname(userDetail.getNickName());

            Long myId = userDetail.getId();
            List<RoomUser> list = roomUserService.findByRoomId(roomId, YES);
            List<RoomUser> roomUsers = new ArrayList<RoomUser>();
            for (RoomUser roomUser : list) {
                Long userId = roomUser.getUserId();
                if (null == userId)
                    continue;
                if (myId.equals(userId)) {
                    roomShare.setMyIntegral(roomUser.getIntegral());
                    continue;
                }
                RoomUser bean = new RoomUser();
                User detail = userDetailService.findById(userId);
                bean.setHeadImg(detail.getHeadImg());
                bean.setNickName(detail.getNickName());
                bean.setIntegral(roomUser.getIntegral());
                roomUsers.add(bean);
            }
            roomShare.setList(roomUsers);

            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            filter = new SimplePropertyPreFilter(RoomUser.class, "integral", "headImg", "nickName", "roomBaseWager");
            successAjaxToJson(response, JSON.toJSONString(roomShare, filter, SerializerFeature.BrowserCompatible, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty), "");
            return;
        } catch (Exception e) {
            log.error("获取分享后的房间的信息出错", e);
        }

        errorAjaxToJson(response, "获取用户分享出的房间参与信息出错", ErrorCode.ER000504);
        return;
    }
}
