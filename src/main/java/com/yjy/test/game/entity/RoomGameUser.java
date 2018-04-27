package com.yjy.test.game.entity;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;

/**
 * 牌局用户表
 * Created by yjy on 2017/07/03.
 */
@Entity
@Table(name = "cg_room_game_user")
public class RoomGameUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int ROLE_VISITOR = 0; //游客
    public static final int ROLE_PLAYER = 1; //闲家
    public static final int ROLE_BANKER = 2; //庄家

    // 操作权限(可叠加 如 丢+藏 => 17)
    public static final int OPER_NONE = 0; // 无
    public static final int OPER_THROW = 1; // 丢牌
    public static final int OPER_DUI = 2; // 对
    public static final int OPER_ZHAO = 4; // 招
    public static final int OPER_BIGZHAO = 8; // 大招
    public static final int OPER_CANG = 16; // 藏
    public static final int OPER_BIGCANG = 32; // 大藏
    public static final int OPER_HU = 64; // 胡

    /*************表实体****************************************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // 用户id
    private Long roomId; // 房间id
    private Long gameId; // 牌局id
    private Integer role; // 角色
    private Integer huCount; // 胡数
    private Integer winIntegral; // 赚取金币
    private Integer integral; // 剩余金币

    private String initPoker; // 初始牌列表
    private String handPoker; // 手牌列表
    private String dzbfPoker; // 对招船范列表
    private String throwPoker; // 丢牌列表
    private String huPoker; // 胡牌列表

    public Integer getHuCount() {
        return huCount;
    }

    public void setHuCount(Integer huCount) {
        this.huCount = huCount;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getInitPoker() {
        return initPoker;
    }

    public void setInitPoker(String initPoker) {
        this.initPoker = initPoker;
    }

    public String getHandPoker() {
        return handPoker;
    }

    public void setHandPoker(String handPoker) {
        this.handPoker = handPoker;
    }

    public String getThrowPoker() {
        return throwPoker;
    }

    public void setThrowPoker(String throwPoker) {
        this.throwPoker = throwPoker;
    }

    public String getHuPoker() {
        return huPoker;
    }

    public void setHuPoker(String huPoker) {
        this.huPoker = huPoker;
    }

    public String getDzbfPoker() {
        return dzbfPoker;
    }

    public void setDzbfPoker(String dzbfPoker) {
        this.dzbfPoker = dzbfPoker;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getWinIntegral() {
        return winIntegral;
    }

    public void setWinIntegral(Integer winIntegral) {
        this.winIntegral = winIntegral;
    }
}
