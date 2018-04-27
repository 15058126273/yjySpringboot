package com.yjy.test.game.entity.club;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 俱乐部表
 *
 * @author yjy
 * Created on 2017年12月5日 上午9:34:07
 */
@Entity
@Table(name = "cg_club")
public class Club extends BaseEntity {

    private static final long serialVersionUID = -1353909238958898740L;

    public static final int STATUS_NORMAL = 0; // 正常
    public static final int STATUS_FORBIDDEN = 1; // 禁用
    public static final int STATUS_DELETED = 2; // 已删除

    public static final int CONDITION_NONE = 0; // 无条件
    public static final int CONDITION_WXCODE = 1; // 微信号

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 俱乐部id
    private Long userId; // 创建人
    private String name; // 俱乐部名称
    private String headImg; // 头像(默认创建人头像)
    private String address; // 所在地
    private Double longitude; // 经度
    private Double latitude; // 纬度
    private String code; // 俱乐部编号
    private Integer maxPerson; // 最大人数
    private Integer status; // 状态
    private Integer canJoin; // 是否允许加入
    @Column(name = "cond")
    private Integer condition; // 加入条件
    private Integer recommend; // 系统推荐
    private Integer userCount; // 当前成员人数
    private String introduce; // 简介
    private Date addTime; // 创建时间
    private Date updateTime; // 更新时间

    // 非持久化字段
    @Transient
    private Integer roomCount; // 房间数

    public Club() {
    }

    public Club(Long userId, String headImg, String name, String address, Integer canJoin, Integer condition,
                Double longitude, Double latitude) {
        this.userId = userId;
        this.headImg = headImg;
        this.name = name;
        this.address = address;
        this.canJoin = canJoin;
        this.condition = condition;
        this.longitude = longitude;
        this.latitude = latitude;
        this.init();
    }

    private void init() {
        if (maxPerson == null)
            this.maxPerson = 50;
        if (status == null)
            this.status = STATUS_NORMAL;
        if (canJoin == null)
            this.canJoin = YES;
        if (condition == null)
            this.condition = CONDITION_WXCODE;
        if (recommend == null)
            this.recommend = YES;
        if (userCount == null)
            this.userCount = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getCanJoin() {
        return canJoin;
    }

    public void setCanJoin(Integer canJoin) {
        this.canJoin = canJoin;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCode() {
        return code;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(Integer maxPerson) {
        this.maxPerson = maxPerson;
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

    @Override
    public String toString() {
        return "Club [id=" + id + ", userId=" + userId + ", name=" + name
                + ", headImg=" + headImg + ", address=" + address + ", code="
                + code + ", maxPerson=" + maxPerson + ", status=" + status
                + ", canJoin=" + canJoin + ", condition=" + condition
                + ", recommend=" + recommend + ", userCount=" + userCount
                + ", addTime=" + addTime + ", updateTime=" + updateTime + "]";
    }

}
