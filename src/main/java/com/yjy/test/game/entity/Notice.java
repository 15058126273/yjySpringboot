package com.yjy.test.game.entity;


import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统公告信息管理
 *
 * @author wdy
 * @version ：2017年5月22日 上午11:21:07
 */
@Entity
@Table(name = "cg_notice")
public class Notice extends BaseEntity {

    private static final long serialVersionUID = -5112031460082296995L;

    public static String TYPE_SERVICE = String.valueOf(2);//维护信息
    public static String TYPE_BROADCAST = String.valueOf(1);//广播
    public static String TYPE_NOTICE = String.valueOf(0);//公告

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;//公告内容
    private String type;//0，是通知，1 是广播 ，2 维护信息
    private Integer isDelete;//是否删除了
    private Integer adminId;//管理员id
    private Date addTime;//添加时间
    private Date updateTime;//更新时间


    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}
