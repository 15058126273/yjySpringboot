package com.yjy.test.game.controller.back;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.Room;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.service.RoomService;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.util.APICodeUtils;
import com.yjy.test.game.util.BackUtils;
import com.yjy.test.game.util.DateUtils;
import com.yjy.test.game.util.PaginationUtils;
import com.yjy.test.game.util.pojo.ConsumeLog;
import com.yjy.test.util.hibernate.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.yjy.test.util.hibernate.SimplePage.cpn;


/**
 * 玩家钻石消费记录控制层
 *
 * @author wdy
 * @version ：2017年5月27日 下午5:37:00
 */
@Controller
@RequestMapping("/consume")
public class ConsumeLogController extends BaseBackController {

    public static final Logger logger = LoggerFactory.getLogger(ConsumeLogController.class);

    public static final String CONSUME_LOG_INFO = "game/consumes.jtk";//消费记录接口

    public static final String CONSUME_LOG_STATIS = "game/consumeStatis.jtk";//消费统计接口

    @Autowired
    private UserService userDetailService;
    @Autowired
    private RoomService roomService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/list.do")
    public String list(Long userId, Date start, Date end, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            Pagination p = null;
            String consumeUrl = com.yjy.test.game.base.Config.playerCenterUrl;
            consumeUrl += CONSUME_LOG_INFO;
            String json = APICodeUtils.consumeLogs(consumeUrl, userId, start, end, cpn(pageNo));
            if (StringUtils.isNotBlank(json)) {
                p = PaginationUtils.jsonToPage(json, ConsumeLog.class);
            }

            if (null != p) {
                List<ConsumeLog> list = (List<ConsumeLog>) p.getList();
                for (ConsumeLog login : list) {
                    Long uId = login.getUserId();
                    User detail = userDetailService.findById(uId);
                    if (null != detail) {
                        login.setBuyerName(detail.getNickName());
                    }
                }
                p.setList(list);
            } else {
                p = new Pagination(1, 0, 0);
            }
            model.addAttribute("pagination", p);
            model.addAttribute("userId", userId);
            model.addAttribute("start", start);
            model.addAttribute("end", end);
            return BackUtils.returnPage(config, "consume", "list", request, model);
        } catch (Exception e) {
            log.error("查询玩家消费钻石信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查询玩家消费钻石信息出错", null, model);
    }

    /**
     * 统计消费数据
     *
     * @param request
     * @param model
     * @return
     * @author wdy
     * @version ：2017年7月27日 下午2:55:06
     */
    @RequestMapping(value = "/chart.do")
    public String chart(HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            Date start = null;
            start = DateUtils.getDateByDay(new Date(), Integer.valueOf(-30));

            model.addAttribute("start", start);
            return BackUtils.returnPage(config, "consume", "chart", request, model);
        } catch (Exception e) {
            log.error("访问消费统计图表页面出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查看费统计图表信息出错", null, model);
    }

    /**
     * 统计图表的数据
     *
     * @param start
     * @param end
     * @return
     * @author wdy
     * @version ：2017年7月24日 下午2:30:14
     */
    @RequestMapping(value = "/chartData.do")
    public void chartData(Date start, Date end, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (null == start && null == end) {
                start = DateUtils.getDateByDay(new Date(), Integer.valueOf(-30));
            }

            List<ConsumeLog> consumeLogs = null;
            String consumeUrl = com.yjy.test.game.base.Config.playerCenterUrl;
            consumeUrl += CONSUME_LOG_STATIS;
            String json = APICodeUtils.consumeStatis(consumeUrl, start, end);
            if (StringUtils.isBlank(json)) {
                ajaxErrorToJson(response, null, "获取的消费统计信息出错");
                return;
            }
            consumeLogs = JSON.parseArray(json, ConsumeLog.class);


            Room room = new Room();
            List<Room> rooms = roomService.findDayRoom(room, start, end);
            boolean goon = true;
            int i = 0, k = 0;
            int iLen = rooms.size();//房间信息
            int kLen = consumeLogs.size();//消费信息
            List<ConsumeLog> list = new ArrayList<ConsumeLog>();
            ConsumeLog consumeLog = null;
            while (goon) {
                if (i >= iLen && k >= kLen) {
                    break;
                }
                Date addTime = null;
                if (i < iLen) {
                    room = rooms.get(i);
                    addTime = room.getAddTime();
                }
                Date addDate = null;
                if (k < kLen) {
                    consumeLog = consumeLogs.get(k);
                    addDate = consumeLog.getAddTime();
                }
                if (null != addTime && null != addDate) {
                    //两个时间相等
                    int result = addTime.compareTo(addDate);
                    switch (result) {
                        case 0:
                            Integer num = room.getNum();
                            consumeLog.setBalance(num);
                            i++;
                            k++;
                            break;

                        case 1:
                            consumeLog.setBalance(Integer.valueOf(0));
                            k++;
                            break;

                        case -1:
                            consumeLog = new ConsumeLog();
                            consumeLog.setAddTime(addTime);
                            consumeLog.setNum(Integer.valueOf(0));
                            num = room.getNum();
                            consumeLog.setBalance(num);
                            i++;
                            break;

                        default:
                            break;
                    }
                } else {
                    //其中一个不存在
                    if (addTime == null) {
                        consumeLog.setBalance(Integer.valueOf(0));
                        k++;
                    } else {
                        consumeLog = new ConsumeLog();
                        consumeLog.setAddTime(addTime);
                        consumeLog.setNum(Integer.valueOf(0));
                        Integer num = room.getNum();
                        consumeLog.setBalance(num);
                        i++;
                    }
                }
                list.add(consumeLog);
                if (i >= iLen && k >= kLen) {
                    goon = false;
                }
            }
            //balance 为房间的数量
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            filter = new SimplePropertyPreFilter(ConsumeLog.class, "addTime", "balance", "num");
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
            ajaxSuccessToJson(response, JSON.toJSONString(list, filter, SerializerFeature.BrowserCompatible, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteDateUseDateFormat));
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            return;
        } catch (Exception e) {
            log.error("查询消费统计数据信息出错", e);
        }
        ajaxErrorToJson(response, null, "查看用户的消费统计信息出错");
        return;
    }

}
