package com.yjy.test.game.web.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.yjy.test.game.base.Constants;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.entity.OptionItem;
import com.yjy.test.game.redis.RedisUtils;
import com.yjy.test.game.service.ConfigService;
import com.yjy.test.game.service.OptionItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 初始化系统参数
 * 和下拉的参数值
 *
 * @author wdy
 * @version ：2017年1月10日 上午11:44:22
 */
@WebServlet(name = "dictionaryServlet", loadOnStartup = 1)
public class DictionaryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(DictionaryServlet.class);

    private ConfigService configService;//系统的配置参数
    private OptionItemService optionItemService;//下拉框的参数

    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    @Autowired
    public void setOptionItemService(OptionItemService optionItemService) {
        this.optionItemService = optionItemService;
    }

    /**
     * struts2 标签中直接获取
     * <s:property value="#application.app"/>${applicationScope.app}
     * <s:select name="name" list="#application.applyType" listKey="fieldKey" listValue="fieldValue" headerKey="" headerValue="--请选择类型--"></s:select>
     * freemarker ${Application["optionMap"].fieldValue}
     */
    @Override
    public void init() throws ServletException {
        try {
            List<OptionItem> fields = optionItemService.findListByProperty("isUse", 1);
            List<OptionItem> optionSelects = null;
            List<OptionItem> needSelects = null;
            Map<String, String> optionMap = new HashMap<String, String>();
            if (fields != null && !fields.isEmpty()) {
                for (OptionItem os : fields) {
                    optionSelects = optionItemService.listByField(os.getField(), true);
                    needSelects = new ArrayList<OptionItem>();
                    for (OptionItem op : optionSelects) {
                        optionMap.put(os.getField() + op.getFieldKey(), op.getFieldValue());
                        if (Integer.valueOf(1).equals(op.getIsUse()))
                            needSelects.add(op);
                    }
                    this.getServletContext().setAttribute(os.getField(), needSelects);
                }
            }
            ServletContext servletContext = this.getServletContext();
            servletContext.setAttribute("optionMap", optionMap);

            //系统参数配置
            Map<String, String> configMap = new HashMap<String, String>();
            Config config = new Config();
            config.setStatus(OptionItem.STATUS_YES);
            List<Config> configs = configService.findList(config);
            for (Config cf : configs) {
                configMap.put(Constants.PRE_CONFIG + cf.getFieldKey(), cf.getFieldValue());
            }
            this.getServletContext().setAttribute(Constants.ALL_CONFIG_MAP, configMap);
            RedisUtils.getInstance().setObject(Constants.ALL_CONFIG_MAP, configMap);
        } catch (Exception e) {
            log.error("数据字典的初始化时发生异常", e);
        }
        super.init();
    }


}