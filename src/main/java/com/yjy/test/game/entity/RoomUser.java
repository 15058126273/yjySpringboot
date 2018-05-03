package com.yjy.test.game.entity;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 房间用户参与与表
 *
 * @author wdy
 * @version ：2017年5月24日 下午5:55:21
 */
@Entity
@Table(name = "cg_room_user")
public class RoomUser extends BaseEntity {

    private static final long serialVersionUID = -9013360811893082558L;

    public static final int ROLE_VISITOR = 0; //游客
    public static final int ROLE_PLAYER = 1; //闲家
    public static final int ROLE_BANKER = 2; //庄家

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // 用户id
    private Long roomId; // 房间id
    private String roomNo; // 房间号
    private Integer status; // 状态
    private Integer integral; // 当前金币
    private Integer playNum; // 参与牌局数
    private Integer isPlayer; // 是否是房间中的玩家
    private Integer currentRole; // 当前角色
    private Date addTime; // 添加时间
    private Date updateTime; // 更新时间

    // 要使 userId 字段与 user 同存则必须加上 insertable = false, updatable = false
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    //不持久化的
    @Transient
    private Integer roomStatus; // 房间状态
    @Transient
    private String nickName; // 用户昵称
    @Transient
    private String headImg; // 用户头像
    @Transient
    private Integer roomGameNum; // 圈数
    @Transient
    private Integer gameMode; // 游戏模式
    @Transient
    private String code; // 玩家编号

    public RoomUser() {
    }

    public RoomUser(Long id, Long roomId, String roomNo, Long userId, Integer integral, String nickName, String code) {
        this.id = id;
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.userId = userId;
        this.integral = integral;
        this.nickName = nickName;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(Integer currentRole) {
        this.currentRole = currentRole;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }

    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
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

    public Integer getIsPlayer() {
        return isPlayer;
    }

    public void setIsPlayer(Integer isPlayer) {
        this.isPlayer = isPlayer;
    }

    public Integer getRoomGameNum() {
        return roomGameNum;
    }

    public void setRoomGameNum(Integer roomGameNum) {
        this.roomGameNum = roomGameNum;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getGameMode() {
        return gameMode;
    }

    public void setGameMode(Integer gameMode) {
        this.gameMode = gameMode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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


}
