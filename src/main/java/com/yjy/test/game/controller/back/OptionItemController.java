package com.yjy.test.game.controller.back;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.OptionItem;
import com.yjy.test.game.service.OptionItemService;
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
@RequestMapping("/optionItem")
public class OptionItemController extends BaseBackController {

    public static final Logger log = LoggerFactory.getLogger(OptionItemController.class);

    @Autowired
    private OptionItemService optionItemService;

    @RequestMapping(value = "/list.do")
    public String list(String fieldName, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        OptionItem optionItem = new OptionItem();
        optionItem.setIsDelete(Integer.valueOf(0));
        if (StringUtils.isNotBlank(fieldName)) {
            optionItem.setFieldName(fieldName);
        }
        Pagination p = optionItemService.findListPage(optionItem, cpn(pageNo), CookieUtils.getPageSize(request),
                new Sort.Order(Sort.Direction.ASC, "field"), new Sort.Order(Sort.Direction.ASC, "priority"));

        List<String> fields = optionItemService.getAllFieldName();
        model.addAttribute("fields", fields);
        model.addAttribute("pagination", p);
        model.addAttribute("fieldName", fieldName);
        return BackUtils.returnPage(config, "option_item", "list", request, model);
    }

    /**
     * 新增下拉框信息
     */
    @RequestMapping(value = "/add.do")
    public String add(HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        return BackUtils.returnPage(config, "option_item", "add", request, model);
    }

    /**
     * 保存商品信息
     *
     * @param optionItem
     * @return
     * @author wdy
     * @version ：2017年1月7日 下午6:11:44
     */
    @RequestMapping(value = "/save.do")
    public String save(OptionItem optionItem, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        String fieldName = null;
        String field = optionItem.getField();
        List<OptionItem> list = optionItemService.listByField(field, null);
        if (null != list && list.size() > 0) {
            OptionItem bean = list.get(0);
            fieldName = bean.getFieldName();
        }

        Date now = new Date();
        Admin admin = BackUtils.getCurrentUser(request);
        optionItem.setFieldName(fieldName);
        optionItem.setAddTime(now);
        optionItem.setUpdateTime(now);
        optionItem.setAdminId(admin.getId());
        optionItem.setIsDelete(Integer.valueOf(0));
        optionItem = optionItemService.save(optionItem);
        if (null != optionItem) {
            initDictionary(request);
            return "redirect:list.do";
        } else
            return BackUtils.returnPage(config, "option_item", "add", request, model);
    }

    /**
     * 修改商品信息
     */
    @RequestMapping(value = "/edit.do")
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        OptionItem bean = optionItemService.findById(id);
        model.addAttribute("bean", bean);
        return BackUtils.returnPage(config, "option_item", "edit", request, model);
    }

    /**
     * 修改商品信息
     *
     * @param id
     * @param optionItem
     * @return
     * @author wdy
     * @version ：2017年1月9日 下午6:13:43
     */
    @RequestMapping(value = "/update.do")
    public String update(Integer id, OptionItem optionItem, HttpServletRequest request, ModelMap model) {
        Config config = new Config();
        Date now = new Date();
        Admin admin = BackUtils.getCurrentUser(request);
        OptionItem bean = optionItemService.findById(id);
        bean.setFieldValue(optionItem.getFieldValue());
        bean.setIsUse(optionItem.getIsUse());
        bean.setPriority(optionItem.getPriority());
        bean.setAdminId(admin.getId());
        bean.setUpdateTime(now);
        optionItem = (OptionItem) optionItemService.update(bean);
        if (null != optionItem) {
            initDictionary(request);
            return "redirect:list.do";
        } else {
            model.addAttribute("bean", bean);
            return BackUtils.returnPage(config, "option_item", "edit", request, model);
        }
    }

    /**
     * 删除数据字典信息
     *
     * @param id
     * @return
     * @author wdy
     * @version ：2017年1月9日 下午6:14:20
     */
    @RequestMapping(value = "/delete.do")
    public String delete(Integer id, HttpServletRequest request, ModelMap model) {
        OptionItem optionItem = optionItemService.findById(id);
        Admin admin = BackUtils.getCurrentUser(request);
        optionItem.setAdminId(admin.getId());
        optionItem.setIsDelete(Integer.valueOf(1));
        optionItem = (OptionItem) optionItemService.update(optionItem);
        initDictionary(request);
        return "redirect:list.do";
    }

    /**
     * 刷新数据字段保存的值
     *
     * @param request
     * @author wdy
     * @version ：2017年1月11日 下午4:34:13
     */
    private void initDictionary(HttpServletRequest request) {
        try {
            ServletContext servletContext = request.getSession()
                    .getServletContext();
            List<OptionItem> fields = optionItemService.findListByProperty("isUse", 1);
            List<OptionItem> optionSelects = null;
            List<OptionItem> needSelects = null;
            Map<String, String> optionMap = new HashMap<String, String>();
            for (OptionItem os : fields) {
                optionSelects = optionItemService.listByField(os.getField(), true);
                needSelects = new ArrayList<OptionItem>();
                for (OptionItem op : optionSelects) {
                    optionMap.put(os.getField() + op.getFieldKey(), op.getFieldValue());
                    if (Integer.valueOf(1).equals(op.getIsUse()))
                        needSelects.add(op);
                }
                servletContext.setAttribute(os.getField(), needSelects);
            }
            servletContext.setAttribute("optionMap", optionMap);
        } catch (Exception e) {
            log.error("初始化数据字段出错", e);
        }
        return;
    }

}
