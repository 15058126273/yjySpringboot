package com.yjy.test.game.controller.back;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.Log;
import com.yjy.test.game.entity.Notice;
import com.yjy.test.game.service.NoticeService;
import com.yjy.test.game.util.BackUtils;
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
 * 支付套餐的控制层
 *
 * @author wdy
 * @version ：2017年5月25日 上午9:45:42
 */
@Controller
@Component(value = "backNotice")
@RequestMapping("/notice")
public class NoticeController extends BaseBackController {

    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "/list.do")
    public String list(String succ, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            Notice notice = new Notice();
            notice.setIsDelete(NO);
            Pagination p = noticeService.findListPage(notice, cpn(pageNo), CookieUtils.getPageSize(request),
                    new Sort.Order(Sort.Direction.DESC, "id"));
            model.addAttribute("pagination", p);
            if (StringUtils.isNotBlank(succ)) {
                model.addAttribute(MESSAGE, succ);
            }
            return BackUtils.returnPage(config, "notice", "list", request, model);
        } catch (Exception e) {
            log.error("查询系统公告信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查询系统公告的信息出错", null, model);
    }

    @RequestMapping("/add.do")
    public String add(HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        return BackUtils.returnPage(config, "notice", "add", request, model);
    }

    @RequestMapping("/save.do")
    public String save(Notice notice, HttpServletRequest request, ModelMap model) {
        Admin adminCur = BackUtils.getCurrentUser(request);
        Config config = new Config();
        if (null != notice) {
            try {
                Date now = new Date();
                notice.setAdminId(adminCur.getId());
                notice.setIsDelete(NO);
                notice.setAddTime(now);
                notice.setUpdateTime(now);
                noticeService.saveOne(notice);
                message = "新增系统公告成功";
                this.doLog(null, Log.ITEM_NOTICE, Log.OPERATION_SAVE, adminCur.getUserName(), request);
                return this.list(message, null, request, model);
            } catch (Exception e) {
                message = "添加系统公告失败";
                log.error(message, e);
            }
        }
        message = "添加系统公告信息失败";
        model.addAttribute("message", message);
        model.addAttribute("bean", notice);
        return BackUtils.returnPage(config, "notice", "add", request, model);
    }

    @RequestMapping("/edit.do")
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        if (null == id) {
            message = "系统公告信息不存在";
            return BackUtils.returnErrorPage(config, request, message, null, model);
        }
        Notice notice = noticeService.findById(id);
        model.addAttribute("bean", notice);
        return BackUtils.returnPage(config, "notice", "edit", request, model);
    }

    /**
     * 更新信息
     *
     * @param notice
     * @return
     * @author wdy
     * @version ：2017年5月12日 上午11:06:52
     */
    @RequestMapping("/update.do")
    public String update(Integer id, Notice notice, HttpServletRequest request, ModelMap model) {
        Admin adminCur = BackUtils.getCurrentUser(request);
        Config config = new Config();
        Notice bean = null;
        if (null != id) {
            bean = noticeService.findById(id);
        }
        if (null == bean || null == notice) {
            message = "套餐信息不存在";
            return BackUtils.returnErrorPage(config, request, message, null, model);
        }
        try {
            bean.setContent(notice.getContent());
            Date now = new Date();
            bean.setUpdateTime(now);
            bean.setAdminId(adminCur.getId());
            noticeService.updateOne(bean);
            this.doLog(id, Log.ITEM_NOTICE, Log.OPERATION_UPDATE, adminCur.getUserName(), request);
            message = "更新系统公告信息成功";
            model.addAttribute("message", message);
            return this.list(message, null, request, model);
        } catch (Exception e) {
            message = "更新系统公告信息失败";
            log.error(message, e);
        }
        model.addAttribute("message", message);
        model.addAttribute("bean", notice);
        return BackUtils.returnPage(config, "notice", "edit", request, model);
    }

    @RequestMapping(value = "/detail.do")
    public String detail(Integer id, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            Notice bean = noticeService.findById(id);
            model.addAttribute("bean", bean);
            return BackUtils.returnPage(config, "notice", "detail", request, model);
        } catch (Exception e) {
            log.error("查看系统公告信息出错", e);
        }
        return BackUtils.returnErrorPage(config, request, "查看系统公告信息出错", "/notice/list.do", model);
    }

    /**
     * 删除系统公告
     *
     * @param id
     * @return
     * @author wdy
     * @version ：2017年5月16日 下午5:45:58
     */
    @RequestMapping(value = "/delete.do")
    public String delete(Integer id, HttpServletRequest request, ModelMap model) {
        Admin adminCur = BackUtils.getCurrentUser(request);
        Config config = new Config();
        if (null == id) {
            message = "提交参数为空";
            return BackUtils.returnErrorPage(config, request, message, null, model);
        }
        Notice notice = noticeService.findById(id);
        if (null == notice) {
            message = "系统公告信息不存在";
            return BackUtils.returnErrorPage(config, request, message, null, model);
        }
        try {
            notice.setIsDelete(YES);
            notice.setAdminId(adminCur.getId());
            notice.setUpdateTime(new Date());
            noticeService.update(notice);
            message = "删除系统公告信息成功";
            model.addAttribute("message", message);
            return this.list(message, null, request, model);
        } catch (Exception e) {
            log.error("禁用系统公告信息出错", e);
        }
        message = "删除系统公告信息出错";
        return BackUtils.returnErrorPage(config, request, message, null, model);
    }

}
