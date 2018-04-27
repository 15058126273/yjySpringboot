package com.yjy.test.game.util.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 钻石充值交易表
 *
 * @author wdy
 * @version ：2017年5月11日 上午10:38:23
 */
public class DepositLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;//买方
    private String orderNo;//订单号
    private Integer num;//钻石数量
    private Integer balance;//买方当前余额
    private Integer type;//0：代理充值，1：自己充值
    private String username;//代理充值用户
    private Date addTime;//添加时间
    private Date updateTime;//更新时间

    public static Integer TYPE_USER = Integer.valueOf(0);//用户自己充值
    public static Integer TYPE_AGENT = Integer.valueOf(1);//代理充值
    public static Integer TYPE_SYSTEM = Integer.valueOf(2);//代理充值

    //不持久化对象
    private String sellerName;//
    private String buyerName;//买方用户名

    public Long getId() {
        return id;
    }

    public Integer getNum() {
        return num;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getBalance() {
        return balance;
    }

    public Date getAddTime() {
        return addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
