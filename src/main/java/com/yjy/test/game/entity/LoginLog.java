package com.yjy.test.game.entity;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;


/**
 * 前端用户登录日志
 *
 * @author wdy
 * @version ：2016年8月25日 上午9:34:02
 */
@Entity
@Table(name = "cg_login_log")
public class LoginLog extends BaseEntity {

    private static final long serialVersionUID = -3906030805141110495L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //	主键
    private String ip;//登录IP
    private Boolean isMobile;//是否使用手机
    private String browserName;//浏览器类型
    private String operatingSystem;//操作系统类型
    private String browserVersion;//浏览器版本
    private String customerModel;//客户端手机型号
    private Integer category;//成功 失败
    private Long userId;//用户Id
    private Date addTime;//新增时间

    private Date updateTime;//修改时间

    public static Integer CATEGORY_FAILURE = Integer.valueOf(0);//登录失败
    public static Integer CATEGORY_SUCCESS = Integer.valueOf(1);//登录成功

    //不持久化的
    private String agent;//客户端信息
    private boolean success;//是否成功
    private String nickName;//昵称

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public Boolean getIsMobile() {
        return isMobile;
    }

    public String getBrowserName() {
        return browserName;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public String getCustomerModel() {
        return customerModel;
    }

    public Integer getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setIsMobile(Boolean isMobile) {
        this.isMobile = isMobile;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public void setCustomerModel(String customerModel) {
        this.customerModel = customerModel;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}
