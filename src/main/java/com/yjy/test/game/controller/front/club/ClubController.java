package com.yjy.test.game.controller.front.club;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.base.Result;
import com.yjy.test.game.controller.front.BaseFrontController;
import com.yjy.test.game.entity.Room;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.entity.club.Club;
import com.yjy.test.game.entity.club.ClubUser;
import com.yjy.test.game.service.RoomService;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.service.club.ClubService;
import com.yjy.test.game.service.club.ClubUserService;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.web.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import static com.yjy.test.util.hibernate.SimplePage.cpn;
import static com.yjy.test.util.hibernate.SimplePage.cps;


/**
 * 俱乐部接口管理
 *
 * @author yjy
 * Created on 2017年12月5日 上午11:34:31
 */
@Controller
@RequestMapping("/club")
public class ClubController extends BaseFrontController {

    public static final Logger log = LoggerFactory.getLogger(ClubController.class);

    @Autowired
    private ClubService clubService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ClubUserService clubUserService;
    @Autowired
    private UserService userDetailService;

    /**
     * 俱乐部系统推荐列表
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param pageSize  条数
     * @param request   请求
     * @param response  响应
     * @author yjy
     * Created on 2017年12月5日 下午2:04:25
     */
    @RequestMapping(value = "/recommendList.jtk")
    public void recommendList(Double longitude, Double latitude, Integer pageSize,
                              HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            User user = FrontUtils.getCurrentUser(request);
            // 获取推荐列表
            List<Club> list = clubService.findRecommend(user.getId(), longitude, latitude, cps(pageSize));
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Club.class,
                    "id", "userId", "code", "name", "headImg", "address", "canJoin",
                    "condition", "userCount", "introduce", "maxPerson");
            successAjaxToJson(response, JSON.toJSONString(list, filter), SUCCESS);
            return;
        } catch (Exception e) {
            info = "获取俱乐部推荐列表出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 搜索俱乐部列表
     *
     * @param clubCode 俱乐部编号
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月5日 下午2:04:25
     */
    @RequestMapping(value = "/searchList.jtk")
    public void searchList(String clubCode, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 验证参数
            if (StringUtils.isNotBlank(clubCode)) {
                // 获取搜索列表
                Club club = clubService.findSearch(clubCode);
                if (club != null) {
                    SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Club.class,
                            "id", "userId", "code", "name", "headImg", "address", "canJoin",
                            "condition", "userCount", "introduce", "maxPerson");
                    successAjaxToJson(response, JSON.toJSONString(club, filter), SUCCESS);
                    return;
                } else {
                    info = "俱乐部不存在";
                    code = ErrorCode.ER001006;
                }
            } else {
                info = "请输入俱乐部编号";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "搜索俱乐部出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 获取我的俱乐部列表
     *
     * @param pageNo   页号
     * @param pageSize 条数
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月5日 下午2:41:14
     */
    @RequestMapping(value = "/myList.jtk")
    public void myList(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 获取我的信息
            User user = FrontUtils.getCurrentUser(request);
            // 获取我的俱乐部列表
            List<Club> list = clubService.findMyList(user.getId(), cpn(pageNo), cps(pageSize));
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Club.class,
                    "id", "userId", "code", "name", "headImg", "address", "canJoin",
                    "condition", "recommend", "userCount", "roomCount", "introduce", "maxPerson");
            successAjaxToJson(response, JSON.toJSONString(list, filter), SUCCESS);
            return;
        } catch (Exception e) {
            info = "获取我的俱乐部列表出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 获取俱乐部成员列表
     *
     * @param clubId   俱乐部id
     * @param pageNo   页号
     * @param pageSize 条数
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月5日 下午5:32:46
     */
    @RequestMapping(value = "/memberList.jtk")
    public void memeberList(Long clubId, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 验证参数
            if (clubId != null) {
                // 获取成员列表
                List<ClubUser> list = clubUserService.findMemberList(clubId, cpn(pageNo), cps(pageSize));
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(ClubUser.class,
                        "id", "role", "headImg", "nickName", "addTime", "userCode");
                successAjaxToJson(response, JSON.toJSONString(list, filter, SerializerFeature.BrowserCompatible), SUCCESS);
                return;
            } else {
                info = "参数不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "获取成员列表出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 创建俱乐部
     *
     * @param name      俱乐部名称
     * @param address   所在地
     * @param canJoin   是否允许加入
     * @param condition 申请条件
     * @param request   请求
     * @param response  响应
     * @author yjy
     * Created on 2017年12月5日 上午11:37:03
     */
    @RequestMapping(value = "/create.jtk", method = RequestMethod.POST)
    public void createClub(String name, String address, Integer canJoin, Integer condition, Double longitude, Double latitude,
                           String introduce, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // if 参数完整
            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(address) && canJoin != null && condition != null) {
                if (name.length() <= 50 && address.length() <= 50) {
                    User user = FrontUtils.getCurrentUser(request);
                    user = userDetailService.findBeanById(user.getId());
                    if (clubService.findCreateCount(user.getId()) < 10) {
                        // 创建俱乐部
                        Club club = clubService.createClub(user.getId(), user.getHeadImg(), name, address, canJoin, condition, longitude, latitude, introduce);
                        successAjaxToJson(response, club.getId().toString(), SUCCESS);
                        return;
                    } else {
                        info = "创建俱乐部数量达到上限";
                        code = ErrorCode.ER001016;
                    }
                } else {
                    info = "提交参数过长";
                    code = ErrorCode.ER_PARAM_TOO_LONG;
                }
            } else {
                info = "提交信息不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "创建俱乐部出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 修改俱乐部信息
     *
     * @param id      俱乐部id
     * @param canJoin   是否允许加入
     * @param condition 申请条件
     * @param request   请求
     * @param response  响应
     * @author yjy
     * Created on 2017年12月5日 上午11:37:03
     */
    @RequestMapping(value = "/edit.jtk", method = RequestMethod.POST)
    public void createClub(Long id, Integer canJoin, Integer condition, Integer recommend, String introduce,
                           HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // if 参数完整
            if (id != null && canJoin != null && condition != null && recommend != null) {
                User user = FrontUtils.getCurrentUser(request);
                // 获取俱乐部信息
                Club club = clubService.findById(id);
                if (club != null && club.getUserId().equals(user.getId())) {
                    // 修改俱乐部
                    club.setCanJoin(canJoin);
                    club.setCondition(condition);
                    club.setRecommend(recommend);
                    club.setIntroduce(introduce);
                    clubService.update(club);
                    successAjaxToJson(response, club.getId().toString(), SUCCESS);
                    return;
                } else {
                    info = "非创建人";
                    code = ErrorCode.ER001007;
                }
            } else {
                info = "提交信息不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "修改俱乐部信息出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 申请加入俱乐部
     *
     * @param clubId   俱乐部id
     * @param remark   备注
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月5日 下午2:50:47
     */
    @RequestMapping(value = "/apply.jtk", method = RequestMethod.POST)
    public void applyJoin(Long clubId, String remark, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // if 参数完整
            if (clubId != null) {
                if (remark == null || remark.length() <= 50) {
                    // 获取俱乐部信息
                    Club club = clubService.findById(clubId);
                    // if 存在 & 状态正常 & 允许加入
                    if (club != null && club.getStatus().equals(Club.STATUS_NORMAL) && club.getCanJoin().equals(Club.YES)) {
                        // if 无条件加入 || 提交了微信号
                        if (club.getCondition().equals(Club.CONDITION_NONE) || StringUtils.isNotBlank(remark)) {
                            // 获取当前登入用户
                            User user = FrontUtils.getCurrentUser(request);
                            // 申请加入
                            Result result = clubUserService.applyJoin(club, user.getId(), remark);
                            if (result.isSuccess()) {
                                successAjaxToJson(response, null, SUCCESS);
                                return;
                            } else {
                                info = result.getInfo();
                                code = result.getCode();
                            }
                        } else {
                            info = "请提交你的微信号";
                            code = ErrorCode.ER_NOT_ALL_SUBMIT;
                        }
                    } else {
                        info = "俱乐部不存在或禁止加入";
                        code = ErrorCode.ER001002;
                    }
                } else {
                    info = "参数长度超过限制";
                    code = ErrorCode.ER_PARAM_TOO_LONG;
                }
            } else {
                info = "参数错误";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "申请加入出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 移除一个成员
     *
     * @param clubUserId 成员id
     * @author yjy
     * Created on 2017年12月5日 下午4:29:46
     */
    @RequestMapping(value = "/remove.jtk", method = RequestMethod.POST)
    public void removeUser(Long clubUserId, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 验证参数
            if (clubUserId != null) {
                // 获取当前登入用户
                User user = FrontUtils.getCurrentUser(request);
                // 获取成员信息
                ClubUser clubUser = clubUserService.findById(clubUserId);
                if (clubUser != null && clubUser.getStatus().equals(ClubUser.STATUS_JOINED)) {
                    Club club = clubService.findById(clubUser.getClubId());
                    // if 是创建人
                    if (club.getUserId().equals(user.getId())) {
                        // if 移除玩家非创建人
                        if (!club.getUserId().equals(clubUser.getUserId())) {
                            // 移除玩家
                            clubUserService.removePlayer(clubUser, user.getId());
                            successAjaxToJson(response, null, SUCCESS);
                            return;
                        } else {
                            info = "不能移除自己";
                            code = ErrorCode.ER001008;
                        }
                    } else {
                        info = "不是创建人";
                        code = ErrorCode.ER001007;
                    }
                } else {
                    info = "玩家不存在";
                    code = ErrorCode.ER001009;
                }
            } else {
                info = "参数不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "移除成员出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 玩家退出俱乐部
     *
     * @param clubId   俱乐部id
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月5日 下午5:23:10
     */
    @RequestMapping(value = "/quit.jtk", method = RequestMethod.POST)
    public void quitClub(Long clubId, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 验证参数
            if (clubId != null) {
                User user = FrontUtils.getCurrentUser(request);
                ClubUser clubUser = clubUserService.findByUserIdAndClubId(user.getId(), clubId);
                // if 成员存在 & 状态正常
                if (clubUser != null && clubUser.getStatus().equals(ClubUser.STATUS_JOINED)) {
                    // 退出俱乐部
                    clubUserService.quitClub(clubUser);
                    successAjaxToJson(response, null, SUCCESS);
                    return;
                } else {
                    info = "您未在此俱乐部中";
                    code = ErrorCode.ER001009;
                }
            } else {
                info = "参数不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "退出俱乐部出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 解散俱乐部
     *
     * @param clubId   俱乐部id
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月15日 上午10:33:24
     */
    @RequestMapping(value = "dissolve.jtk", method = RequestMethod.POST)
    public void dissolveClub(Long clubId, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 验证参数
            if (clubId != null) {
                User user = FrontUtils.getCurrentUser(request);
                Club club = clubService.findById(clubId);
                // if 俱乐部存在 && 创建人
                if (club != null && club.getUserId().equals(user.getId())) {
                    // 解散
                    clubService.dissolve(club);
                    SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Club.class,
                            "id", "userId", "code", "name", "headImg", "address", "condition", "userCount");
                    successAjaxToJson(response, JSON.toJSONString(club, filter), SUCCESS);
                    return;
                } else {
                    info = "俱乐部不存在";
                    code = ErrorCode.ER001006;
                }
            } else {
                info = "参数不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "解散出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 创建俱乐部游戏房间
     *
     * @param clubId       俱乐部id
     * @param gameNum      游戏局数
     * @param baseIntegral 底分
     * @param gameMode     游戏模式
     * @param scoreMode    玩法模式
     * @param doublePao    是否放铳
     * @param startMode    开始模式
     * @param request
     * @param response
     * @author yjy
     * Created on 2018年4月13日 下午4:25:26
     */
    @RequestMapping(value = "/createRoom.jtk", method = RequestMethod.POST)
    public void createRoom(Long clubId, Integer gameNum, Integer baseIntegral, Integer gameMode, Integer scoreMode,
                           Integer doublePao, Integer startMode, Integer jingStart, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            User user = FrontUtils.getCurrentUser(request);
            // 创建房间
            Room room = new Room(clubId, gameNum, baseIntegral, gameMode, scoreMode, doublePao, startMode, jingStart);
            Result res = roomService.createRoom(user.getId(), room);
            if (res.isSuccess()) {
                successAjaxToJson(response, null, SUCCESS);
                return;
            } else {
                info = res.getInfo();
                code = res.getCode();
            }
        } catch (Exception e) {
            info = "创建房间出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error(info, e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 获取俱乐部当前房间列表
     *
     * @param clubId   俱乐部id
     * @param pageNo   页号
     * @param pageSize 条数
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月6日 下午4:05:43
     */
    @RequestMapping(value = "/roomList.jtk")
    public void roomList(Long clubId, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 验证参数
            if (clubId != null) {
                // 获取房间列表
                List<Room> list = roomService.findClubRoomList(clubId, cpn(pageNo), cps(pageSize));
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Room.class,
                        "id", "roomNo", "baseIntegral", "playerNum", "maxPerson", "gameNum", "scoreMode", "doublePao",
                        "gameMode", "startMode", "jingStart", "headImg", "status", "gameStatus", "nickName", "code", "userId");
                successAjaxToJson(response, JSON.toJSONString(list, filter), SUCCESS);
                return;
            } else {
                info = "参数不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "获取房间列表出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error("info", e);
        }
        errorAjaxToJson(response, info, code);
    }

    /**
     * 获取俱乐部房间记录
     *
     * @param clubId   俱乐部id
     * @param pageNo   页号
     * @param pageSize 条数
     * @param request  请求
     * @param response 响应
     * @author yjy
     * Created on 2017年12月14日 下午3:29:21
     */
    @RequestMapping(value = "/roomRecord.jtk")
    public void roomRecord(Long clubId, Integer pageNo, Integer pageSize,
                           HttpServletRequest request, HttpServletResponse response) {
        String info;
        ErrorCode code;
        try {
            // 验证参数
            if (clubId != null) {
                User user = FrontUtils.getCurrentUser(request);
                // 获取房间列表
                List<Room> list = roomService.findClubRoomRecord(clubId, user.getId(), cpn(pageNo), cps(pageSize));
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Room.class,
                        "id", "roomNo", "baseIntegral", "playerNum", "maxPerson", "gameNum", "scoreMode", "doublePao",
                        "gameMode", "startMode", "jingStart", "headImg", "status", "gameStatus", "nickName", "code", "userId");
                successAjaxToJson(response, JSON.toJSONString(list, filter), SUCCESS);
                return;
            } else {
                info = "参数不完整";
                code = ErrorCode.ER_NOT_ALL_SUBMIT;
            }
        } catch (Exception e) {
            info = "获取房间列表出错了";
            code = ErrorCode.ER_SYSTEM;
            log.error("info", e);
        }
        errorAjaxToJson(response, info, code);
    }

}
