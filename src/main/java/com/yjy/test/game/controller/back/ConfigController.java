package com.yjy.test.game.controller.back;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.base.Constants;
import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.OptionItem;
import com.yjy.test.game.redis.RedisUtils;
import com.yjy.test.game.service.ConfigService;
import com.yjy.test.game.util.BackUtils;
import com.yjy.test.game.web.CookieUtils;
import com.yjy.test.util.hibernate.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.yjy.test.util.hibernate.SimplePage.cpn;


/**
 * 系统配置管理
 *
 * @author wdy
 * @version ：2017年1月7日 下午12:04:23
 */
@Controller
@Component(value = "backConfig")
@RequestMapping("/config")
public class ConfigController extends BaseBackController {

    public static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/list.do")
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = configService.findThisConfig();
        Pagination p = configService.findAllPage(cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", p);
        return BackUtils.returnPage(config, "config", "list", request, model);
    }

    /**
     * 新增系统参数
     */
    @RequestMapping(value = "/add.do")
    public String add(HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        return BackUtils.returnPage(config, "config", "add", request, model);
    }

    /**
     * 保存系统参数
     *
     * @param bean
     * @return
     * @author wdy
     * @version ：2017年1月7日 下午6:11:44
     */
    @RequestMapping(value = "/save.do")
    public String save(Config bean, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        try {
            Date now = new Date();
            Admin admin = BackUtils.getCurrentUser(request);
            bean.setAddTime(now);
            bean.setUpdateTime(now);
            bean.setAdminId(admin.getId());
            bean.setStatus(OptionItem.STATUS_YES);
            bean = configService.save(bean);
            if (null != bean) {
                initConfig(request);
                return "redirect:list.do";
            }
        } catch (Exception e) {
            log.error("保存系统配置信息出错", e);
        }

        return BackUtils.returnPage(config, "config", "add", request, model);
    }

    /**
     * 修改系统参数信息
     */
    @RequestMapping(value = "/edit.do")
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        Config bean = configService.findById(id);
        model.addAttribute("bean", bean);
        return BackUtils.returnPage(config, "config", "edit", request, model);
    }

    /**
     * 修改系统参数信息
     *
     * @param id
     * @param config
     * @return
     * @author wdy
     * @version ：2017年1月9日 下午6:13:43
     */
    @RequestMapping(value = "/update.do")
    public String update(Integer id, Config config, HttpServletRequest request, ModelMap model) {
        Config bean = null;
        try {
            Date now = new Date();
            Admin admin = BackUtils.getCurrentUser(request);
            bean = configService.findById(id);
            bean.setFieldValue(config.getFieldValue());
            bean.setDescription(config.getDescription());
            bean.setStatus(config.getStatus());
            bean.setAdminId(admin.getId());
            bean.setUpdateTime(now);
            config = (Config) configService.update(bean);
            if (null != config) {
                initConfig(request);
                return "redirect:list.do";
            }
        } catch (Exception e) {
            log.error("更新系统配置参数出错");
        }

        model.addAttribute("bean", bean);
        return BackUtils.returnPage(config, "config", "edit", request, model);
    }

    /**
     * 删除配置项
     *
     * @param id      configId
     * @param request
     * @param model
     * @return
     * @author yjy
     * Created on 2018年3月22日 上午9:45:05
     */
    @RequestMapping(value = "delete.do")
    public String delete(Integer id, HttpServletRequest request, ModelMap model) {
        if (id != null) {
            try {
                configService.deleteById(id);
                initConfig(request);
            } catch (Exception e) {
                log.error("delete config error", e);
            }
        }
        return "redirect:list.do";
    }

    /**
     * 刷新系统参量的缓冲
     *
     * @param request
     * @author wdy
     * @version ：2017年1月11日 下午4:39:51
     */
    private void initConfig(HttpServletRequest request) {
        try {
            ServletContext servletContext = request.getSession().getServletContext();
            Map<String, String> configMap = new HashMap<String, String>();
            Config config = new Config();
            config.setStatus(OptionItem.STATUS_YES);
            List<Config> configs = configService.findList(config);
            for (Config cf : configs) {
                configMap.put(Constants.PRE_CONFIG + cf.getFieldKey(), cf.getFieldValue());
            }

            RedisUtils.getInstance().setObject(Constants.ALL_CONFIG_MAP, configMap);
            servletContext.setAttribute(Constants.ALL_CONFIG_MAP, configMap);
        } catch (Exception e) {
            log.error("初始化系统参量出错", e);
        }
        return;
    }

}
