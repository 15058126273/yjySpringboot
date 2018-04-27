package com.yjy.test.game.controller.back;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.base.BaseAction;
import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.entity.Log;
import com.yjy.test.game.util.BackUtils;
import com.yjy.test.game.util.HttpSendUtils;
import com.yjy.test.game.util.concurrent.ConsumerLog;
import com.yjy.test.game.util.concurrent.Producer;
import com.yjy.test.game.web.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;


@Controller
public class BaseBackController extends BaseAction {

    public static final Logger log = LoggerFactory.getLogger(BaseBackController.class);

    public Boolean hasPermission(Admin adminUser) {
        if (null == adminUser) {
            return false;
        }
        if (Integer.valueOf(0).equals(adminUser.getLevel())) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public static BlockingQueue queueLog = new ArrayBlockingQueue(10000);

    @SuppressWarnings("rawtypes")
    public void doLog(Integer itemId, Integer itemType, Integer operateType, String itemName, HttpServletRequest request) {
        Log log = this.saveLog(itemId, itemType, operateType, itemName, request);
        BlockingQueue queueLog = BaseBackController.queueLog;
        Producer producerLog = new Producer(queueLog, log);
        new Thread(producerLog).start();
        if (!ConsumerLog.running) {
            ConsumerLog consumer = new ConsumerLog(queueLog);
            new Thread(consumer).start();
            ConsumerLog.running = true;
        }
    }

    public Log saveLog(Integer itemId, Integer itemType, Integer operateType, String itemName, HttpServletRequest request) {
        Admin adminUser = BackUtils.getCurrentUser(request);
        String ip = RequestUtils.getIpAddr(request);
        Log log = new Log();
        log.setItemType(itemType);
        log.setOperateDate(new Date());
        log.setOperateType(operateType);
        log.setOperator(adminUser.getUserName());
        log.setIp(ip);
        log.setItemId(itemId);
        log.setAdminId(adminUser.getId());
        log.setItemName(itemName);
        return log;
    }

    public ModelMap setMessage(String message, ModelMap model) {
        model.addAttribute("message", message);
        return model;
    }

    /**
     * 组装成json字符串
     *
     * @param num
     * @return
     * @author wdy
     * @version ：2017年3月17日 下午5:13:34
     */
    protected String numJson(int num) {
        return "{\"num\":\"" + num + "\"}";
    }

    /**
     * 检验接口是否合法
     *
     * @param parames
     * @param sign
     * @return
     * @author wdy
     * @version ：2017年9月28日 上午10:57:21
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

}
