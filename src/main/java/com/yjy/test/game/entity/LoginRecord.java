package com.yjy.test.game.entity;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;


/**
 * 后台登录日志
 *
 * @author wdy
 * @version ：2017年6月28日 上午10:34:29
 */
@Entity
@Table(name = "cg_login_record")
public class LoginRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  //	主键
    private String name; // 用户名
    private Integer userId;//用户ID
    private Date loginDate; //登录时间
    private String loginIp;//登录IP
    private Integer category;//成功 失败
    private String content;

    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 2;

    public static final String LOGIN_SUCCESS_TITLE = "login success";
    public static final String LOGIN_FAILURE_TITLE = "login failure";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
