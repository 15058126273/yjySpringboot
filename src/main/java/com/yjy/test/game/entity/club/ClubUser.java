package com.yjy.test.game.entity.club;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 俱乐部成员表
 *
 * @author yjy
 * Created on 2017年12月5日 上午10:15:57
 */
@Entity
@Table(name = "cg_club_user")
public class ClubUser extends BaseEntity {

    private static final long serialVersionUID = -2075005383930599290L;

    public static final int ROLE_MEMBER = 0; // 成员
    public static final int ROLE_MANAGER = 1; // 管理员
    public static final int ROLE_HOST = 2; // 创建人

    public static final int STATUS_CHECKING = 0; // 等待审核
    public static final int STATUS_JOINED = 1; // (加入成功)审核通过
    public static final int STATUS_REFUSED = 2; // 审核不通过
    public static final int STATUS_REMOVED = 3; // 被踢除的
    public static final int STATUS_LEFT = 4; // 离开的

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id
    private Long clubId; // 俱乐部id
    private Long userId; // 用户id
    private Integer role; // 角色
    private Integer status; // 状态
    private Date addTime; // 创建时间
    private Date updateTime; // 更新时间

    // 非持久化字段
    @Transient
    private String nickName; // 昵称
    @Transient
    private String headImg; // 头像
    @Transient
    private String userCode; // 用户code

    public ClubUser() {
    }

    public ClubUser(Long clubId, Long userId, Integer role) {
        this.clubId = clubId;
        this.userId = userId;
        this.role = role;
        this.init();
    }

    private void init() {
        this.status = STATUS_CHECKING;
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

    public Integer getRole() {
        return role;
    }

    public String getNickName() {
        return nickName;
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

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ClubUser [id=" + id + ", clubId=" + clubId + ", userId="
                + userId + ", role=" + role + ", status=" + status
                + ", addTime="
                + addTime + ", updateTime=" + updateTime + "]";
    }


}
