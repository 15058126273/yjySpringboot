package com.yjy.test.game.entity.club;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * 俱乐部消息表
 *
 * @author yjy
 * Created on 2017年12月6日 上午9:34:07
 */
@Entity
@Table(name = "cg_club_message")
public class ClubMessage extends BaseEntity {

    private static final long serialVersionUID = -1353909238958898740L;

    public static final int TYPE_APPLY = 1; // 玩家申请加入消息
    public static final int TYPE_REPLY = 2; // 群主回复申请消息
    public static final int TYPE_REMOVE = 3; // 被踢消息
    public static final int TYPE_QUIT = 4; // 退出消息

    public static final int STATUS_NEW = 1; // 新消息
    public static final int STATUS_OLD = 2; // 旧消息

    public static final int RESULT_AGREE = 1; // 同意
    public static final int RESULT_REFUSE = 2; // 拒绝

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id
    private Long receiveId; // 消息接收人
    private Long sendId; // 消息发送人
    private Long clubId; // 俱乐部id
    private Long clubUserId; // 相关成员id
    private Integer type; // 类型
    private Integer status; // 已操作/已读状态
    private Integer result; // 申请结果
    private String remark; // 备注
    private Integer isDelete; // 是否删除
    private Date addTime; // 创建时间
    private Date updateTime; // 更新时间

    @ManyToOne
    @JoinColumn(name = "clubId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT ))
    private ClubUser clubUser;

    // 非持久化字段
    @Transient
    private String nickName; // 昵称
    @Transient
    private String headImg; // 头像
    @Transient
    private String userCode; // 用户code
    @Transient
    private String clubName; // 俱乐部名称

    public ClubMessage() {
    }

    public ClubMessage(Long sendId, Long receiveId, Long clubId, Long clubUserId, Integer type) {
        this.sendId = sendId;
        this.receiveId = receiveId;
        this.clubId = clubId;
        this.clubUserId = clubUserId;
        this.type = type;
        this.init();
    }

    private void init() {
        this.status = STATUS_NEW;
        this.isDelete = NO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public Long getSendId() {
        return sendId;
    }

    public String getNickName() {
        return nickName;
    }

    public Long getClubId() {
        return clubId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public Long getClubUserId() {
        return clubUserId;
    }

    public void setClubUserId(Long clubUserId) {
        this.clubUserId = clubUserId;
    }

    public ClubUser getClubUser() {
        return clubUser;
    }

    public void setClubUser(ClubUser clubUser) {
        this.clubUser = clubUser;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "ClubMessageDao [id=" + id + ", receiveId=" + receiveId
                + ", sendId=" + sendId + ", clubId=" + clubId + ", clubUserId="
                + clubUserId + ", type=" + type + ", status=" + status
                + ", result=" + result + ", remark=" + remark + ", isDelete="
                + isDelete + ", addTime=" + addTime + ", updateTime="
                + updateTime + ", nickName=" + nickName + ", headImg="
                + headImg + ", userCode=" + userCode + ", clubName=" + clubName
                + "]";
    }

}
