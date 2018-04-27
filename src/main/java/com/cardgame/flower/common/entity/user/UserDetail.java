package com.cardgame.flower.common.entity.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 * Created by yjy on 2017/4/28.
 */
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int STATUS_OFFLINE = 0;
    public static final int STATUS_ONLINE = 1;

    public static final int SEX_MAN = 1; // 男
    public static final int SEX_WOMAN = 2; // 女
    public static final int SEX_NONE = 0; // 无

    /***********表实体*********************/
    private Long id;
    private String openId; //openid
    private String code; //
    private String nickName; //昵称
    private String headImg; //头像
    private Integer sex; // 性别
    private Date addTime; //添加时间
    private Date updateTime; //最后更新时间

    /**********缓存*********************/
    private String roomNo; //当前所处房间号

    /*********传输********************/
    private Integer integral; //金币
    private Integer status; // 当前状态

    public UserDetail() {
        super();
    }

    public UserDetail(Long id) {
        this.id = id;
        this.init();
    }

    public void init() {
        this.status = STATUS_ONLINE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadImg() {
        return headImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return "UserDetail [id=" + id + ", openId=" + openId + ", code=" + code
                + ", nickName=" + nickName + ", headImg=" + headImg + ", sex="
                + sex + ", addTime=" + addTime + ", updateTime=" + updateTime
                + ", roomNo=" + roomNo + ", integral=" + integral + ", status="
                + status + "]";
    }


}
