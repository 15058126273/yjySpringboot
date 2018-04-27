package com.yjy.test.game.util.pojo;

import com.yjy.test.game.util.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 钻石消费记录
 *
 * @author wdy
 * @version ：2017年7月7日 上午9:31:29
 */
public class ConsumeLog implements Serializable {

    private static final long serialVersionUID = 2529576897783509461L;

    private Long id;
    private Long userId; //用户id
    private Integer num; //消耗钻石数量
    private Integer balance; //消费后余额
    private Integer type; //消费类型
    private String description; //描述
    private Integer platform; //消费平台
    private Date addTime; //消费时间

    //不持久化对象
    private String buyerName;//玩家用户名
    private String hour;//小时展示

    public ConsumeLog() {
    }

    public ConsumeLog(Integer num, Date curDate) {
        this.num = num;
        this.addTime = curDate;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getNum() {
        return num;
    }

    public Integer getBalance() {
        return balance;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPlatform() {
        return platform;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Date getDay() {
        Date day = null;
        if (null != addTime) {
            DateFormatUtils.parseDate(addTime);
        }
        return day;
    }

}
