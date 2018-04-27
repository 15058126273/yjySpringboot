package com.yjy.test.game.entity;


import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统配置信息
 *
 * @author wdy
 * @version ：2016年11月28日 下午5:21:25
 */
@Entity
@Table(name = "cg_option_item")
public class OptionItem extends BaseEntity {

    private static final long serialVersionUID = -7293504862396553640L;

    public static String STATUS_YES = String.valueOf(1);//正常
    public static String STATUS_NO = String.valueOf(0);//禁用

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String field;//字段
    private String fieldName;//字段名字
    private String fieldKey;//标识
    private String fieldValue;//显示值
    private Integer isUse;//是否使用
    private String description;//详情
    private Integer priority;//排序
    private Integer isDelete;//是否删除
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

    public String getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPriority() {
        return priority;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}
