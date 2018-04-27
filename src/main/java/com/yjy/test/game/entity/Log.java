package com.yjy.test.game.entity;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cg_log")
public class Log extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer adminId;//操作人员id
    private String operator;//操作人员用户名
    private Date operateDate;//操作时间
    private Integer itemId;//操作项id
    private Integer itemType;//操作项类型
    private Integer operateType;//操作类型
    private String itemName;//操作对象名称
    private String ip;

    public static final Integer ITEM_USER = 1;//用户
    public static final Integer ITEM_COMBO = 2;//套餐订单信息
    public static final Integer ITEM_NOTICE = 3;//系统公告

    public static final Integer OPERATION_SAVE = 1;//保存
    public static final Integer OPERATION_UPDATE = 2;//更新
    public static final Integer OPERATION_START = 3;//启用
    public static final Integer OPERATION_STOP = 4;//停用
    public static final Integer OPERATION_DELETE = 5;//删除

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
