package com.yjy.test.game.controller.back;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.Log;
import com.yjy.test.game.entity.LoginRecord;
import com.yjy.test.game.service.AdminService;
import com.yjy.test.game.service.ConfigService;
import com.yjy.test.game.service.LogService;
import com.yjy.test.game.service.LoginRecordService;
import com.yjy.test.game.util.BackUtils;
import com.yjy.test.game.util.MD5;
import com.yjy.test.game.web.CookieUtils;
import com.yjy.test.game.web.ResponseUtils;
import com.yjy.test.util.hibernate.Pagination;
import com.yjy.test.util.hibernate.SimplePage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import static com.yjy.test.game.base.BaseAction.MESSAGE;

/**
 * 后台系统用户
 *
 * @author wsc
 * @version 2016年3月24日
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseBackController {

    public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private LoginRecordService loginService;
    @Autowired
    private LogService logService;

    /**
     * 管理员列表
     *
     * @param pageNo
     * @param keyWords
     * @return
     */
    @RequestMapping("/list.do")
    public String list(String message, Integer pageNo, String keyWords, HttpServletRequest request, ModelMap model) {
        Admin adminUserCur = BackUtils.getCurrentUser(request);
        Boolean permission = hasPermission(adminUserCur);
        if (!permission) {
            return "redirect:/index.do";
        }
        Admin adminUser = new Admin();
        if (StringUtils.isNotBlank(keyWords)) {
            adminUser.setUserName(keyWords);
        }
        Config config = configService.findThisConfig();
        Pagination pagination = adminService.findListPage(adminUser, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("keyWords", keyWords);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute(MESSAGE, message);
        return BackUtils.returnPage(config, "admin", "list", request, model);
    }

    @RequestMapping("/add.do")
    public String add(HttpServletRequest request, ModelMap model) {
        Admin adminUserCur = BackUtils.getCurrentUser(request);
        Boolean permission = hasPermission(adminUserCur);
        if (!permission) {
            return "redirect:/index.do";
        }
        Config config = configService.findThisConfig();
        return BackUtils.returnPage(config, "admin", "add", request, model);
    }

    @RequestMapping("/save.do")
    public String save(Admin adminUser, String role, HttpServletRequest request, ModelMap model) {
        Admin adminUserCur = BackUtils.getCurrentUser(request);
        Boolean permission = hasPermission(adminUserCur);
        String message = null;
        if (!permission) {
            return "redirect:/index.do";
        }
        Config config = configService.findThisConfig();
        if (null != adminUser) {
            try {
                Date now = new Date();
                String password = adminUser.getPassword().trim();
                String md5Encode = MD5.MD5Encode(password);
                adminUser.setAdminId(adminUserCur.getId());
                adminUser.setAddTime(now);
                adminUser.setUpdateTime(now);
                adminUser.setPassword(md5Encode);
                adminUser.setStatus(Integer.valueOf(1));
                adminService.saveOne(adminUser, request);
                message = "新增用户成功";
                this.doLog(null, Log.ITEM_USER, Log.OPERATION_SAVE, adminUser.getUserName(), request);
                return this.list(message, null, null, request, model);
            } catch (Exception e) {
                message = "添加用户信息失败";
                logger.error(message, e);
                model.addAttribute("message", message);
                model.addAttribute("adminUser", adminUser);
                return BackUtils.returnPage(config, "admin", "add", request, model);
            }
        }
        message = "添加用户信息失败";
        model.addAttribute("message", message);
        model.addAttribute("adminUser", adminUser);
        return BackUtils.returnPage(config, "admin", "add", request, model);
    }

    @RequestMapping("/edit.do")
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        Admin adminUserCur = BackUtils.getCurrentUser(request);
        Boolean permission = hasPermission(adminUserCur);
        String message = null;
        if (!permission) {
            return "redirect:/index.do";
        }
        Config config = configService.findThisConfig();
        if (null == id) {
            message = "用户不存在";
        }
        Admin adminUser = adminService.findById(id);
        model.addAttribute("message", message);
        model.addAttribute("adminUser", adminUser);
        return BackUtils.returnPage(config, "admin", "edit", request, model);
    }

    @RequestMapping("/update.do")
    public String update(Admin adminUser, String role, HttpServletRequest request, ModelMap model) {
        Admin adminUserCur = BackUtils.getCurrentUser(request);
        Boolean permission = hasPermission(adminUserCur);
        String message = null;
        if (!permission) {
            return "redirect:/index.do";
        }
        Config config = configService.findThisConfig();
        Integer id = adminUser.getId();
        Admin adminUser2 = null;
        if (null != id) {
            adminUser2 = adminService.findById(id);
        } else {
            message = "用户不存在";
            model.addAttribute("message", message);
            model.addAttribute("adminUser", adminUser2);
            return BackUtils.returnPage(config, "admin", "edit", request, model);
        }
        try {
            if (null != adminUser) {
                if (StringUtils.isNotBlank(adminUser.getPassword())) {
                    String md5Encode = MD5.MD5Encode(adminUser.getPassword());
                    adminUser2.setPassword(md5Encode);
                }
                adminUser2.setRealName(adminUser.getRealName());
                adminUser2.setQq(adminUser.getQq());
                adminUser2.setMobile(adminUser.getMobile());
                adminUser2.setLevel(adminUser.getLevel());
                if (Integer.valueOf(1).equals(adminUser.getStatus())) {
                    adminUser2.setStatus(Integer.valueOf(1));
                } else {
                    adminUser2.setStatus(Integer.valueOf(0));
                }
                Date now = new Date();
                adminUser2.setUpdateTime(now);
                adminService.updateOne(adminUser2, request);
                this.doLog(id, Log.ITEM_USER, Log.OPERATION_UPDATE, adminUser2.getUserName(), request);
                message = "更新用户成功";
                model.addAttribute("message", message);
                return this.list(message, null, null, request, model);
            }
        } catch (Exception e) {
            message = "更新用户失败";
            logger.error(message, e);
        }
        model.addAttribute("message", message);
        model.addAttribute("adminUser", adminUser2);
        return BackUtils.returnPage(config, "admin", "edit", request, model);
    }

    /**
     * 后台用户登录记录
     *
     * @param pageNo
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/logList.do")
    public String logList(Integer otype, Integer itemType, Integer pageNo, String itemName, String operator, Date startDate, Date endDate, HttpServletRequest request, ModelMap model) {
        Admin adminUserCur = BackUtils.getCurrentUser(request);
        Boolean permission = hasPermission(adminUserCur);
        if (!permission) {
            return "redirect:/index.do";
        }
        Config config = configService.findThisConfig();
        Log log = new Log();
        if (StringUtils.isNotBlank(itemName)) {
            log.setItemName(itemName);
        }
        if (StringUtils.isNotBlank(operator)) {
            log.setItemName(operator);
        }
        Pagination pagination = null;
        try {
            pagination = logService.findList(startDate, endDate, itemName, operator, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request), otype, itemType);
        } catch (Exception e) {
            logger.error("获取列表失败", e);
        }
        model.addAttribute("pagination", pagination);
        model.addAttribute("itemName", itemName);
        model.addAttribute("operator", operator);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("otype", otype);
        model.addAttribute("itemType", itemType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return BackUtils.returnPage(config, "admin", "logList", request, model);
    }

    /**
     * 后台用户登录记录
     *
     * @param pageNo
     * @param keyWords
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/loginList.do")
    public String loginList(Integer pageNo, String keyWords, HttpServletRequest request, ModelMap model) {
        Admin adminUserCur = BackUtils.getCurrentUser(request);
        Boolean permission = hasPermission(adminUserCur);
        if (!permission) {
            return "redirect:/index.do";
        }
        Config config = configService.findThisConfig();
        LoginRecord loginRecord = new LoginRecord();
        if (StringUtils.isNotBlank(keyWords)) {
            loginRecord.setName(keyWords);
        }
        Pagination pagination = loginService.findListPage(loginRecord, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("keyWords", keyWords);
        model.addAttribute("pageNo", pageNo);
        return BackUtils.returnPage(config, "admin", "loginList", request, model);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/changePassword.do")
    public String changePassword(HttpServletRequest request, ModelMap model) {
        Admin adminUserCur = BackUtils.getCurrentUser(request);
        String message = null;
        Config config = configService.findThisConfig();
        Integer id = adminUserCur.getId();
        Admin adminUser = adminService.findById(id);
        model.addAttribute("message", message);
        model.addAttribute("adminUser", adminUser);
        return BackUtils.returnPage(config, "admin", "changePassword", request, model);
    }

    /**
     * 修改密码信息
     *
     * @param adminUser
     * @param newPassword
     * @return
     * @author wdy
     * @version ：2017年3月3日 上午10:18:28
     */
    @RequestMapping("/updateInfo.do")
    public String updateInfo(Admin adminUser, String newPassword, String rePassword, HttpServletRequest request, ModelMap model) {
        Config config = configService.findThisConfig();
        String message = null;
        Admin adminUserCur = BackUtils.getCurrentUser(request);
        Integer adminId = adminUserCur.getId();
        String password = adminUser.getPassword();

        adminUserCur = adminService.findById(adminId);
        if (StringUtils.isBlank(password)) {
            message = "原始密码为空";
            model.addAttribute("message", message);
            model.addAttribute("adminUser", adminUserCur);
            return BackUtils.returnPage(config, "admin", "changePassword", request, model);
        } else {
            password = password.trim();
            String psw = MD5.MD5Encode(password);
            if (!(psw.equals(adminUserCur.getPassword()))) {
                message = "原始密码错误";
                model.addAttribute("message", message);
                model.addAttribute("adminUser", adminUserCur);
                return BackUtils.returnPage(config, "admin", "changePassword", request, model);
            }
            adminUserCur.setRealName(adminUser.getRealName());
            adminUserCur.setMobile(adminUser.getMobile());
            adminUserCur.setQq(adminUser.getQq());
            if (StringUtils.isNotBlank(newPassword)) {
                if (newPassword.equals(rePassword)) {
                    adminUserCur.setPassword(MD5.MD5Encode(newPassword));
                } else {
                    message = "两次输入的密码不一致";
                    model.addAttribute("message", message);
                    model.addAttribute("adminUser", adminUserCur);
                    return BackUtils.returnPage(config, "admin", "changePassword", request, model);
                }
            }

            try {
                adminService.update(adminUserCur);
            } catch (Exception e) {
                message = "修改个人信息失败";
                logger.error(message, e);
                model.addAttribute("message", message);
                model.addAttribute("adminUser", adminUser);
                return BackUtils.returnPage(config, "admin", "changePassword", request, model);
            }
        }
        message = "修改个人信息成功";
        model.addAttribute("message", message);
        return BackUtils.returnPage(config, "admin", "changePassword", request, model);
    }


    @RequestMapping("/checkUserName.do")
    public void checkUserName(String userName, HttpServletRequest request, HttpServletResponse response) {
        boolean data = true;
        Admin adminUser = new Admin();
        adminUser.setUserName(userName);
        List<Admin> list = adminService.findList(adminUser);
        if (null != list && list.size() > 0) {
            data = false;
        }
        JSONObject jsonObject = new JSONObject();
        @SuppressWarnings("static-access")
        String jsonString = jsonObject.toJSONString(data);
        ResponseUtils.renderText(response, jsonString);
    }

    @RequestMapping("/checkPassword.do")
    public void checkPassword(String userName, String oldPassword, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userName)) {
            return;
        }
        if (StringUtils.isBlank(oldPassword)) {
            return;
        }
        boolean data = true;
        Admin adminUser = new Admin();
        adminUser.setUserName(userName);
        adminUser.setPassword(oldPassword);
        List<Admin> list = adminService.findList(adminUser);
        if (null != list && list.size() > 0) {
            data = false;
        }
        JSONObject jsonObject = new JSONObject();
        @SuppressWarnings("static-access")
        String jsonString = jsonObject.toJSONString(data);
        ResponseUtils.renderText(response, jsonString);
    }
}
