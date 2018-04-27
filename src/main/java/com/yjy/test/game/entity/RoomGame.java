package com.yjy.test.game.entity;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 房间牌局表
 * Created by yjy on 2017/07/03.
 */
@Entity
@Table(name = "cg_room_game")
public class RoomGame extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int STATUS_READY_ING = 0; // 准备中
    public static final int STATUS_SEND_ING = 1; // 发牌中
    public static final int STATUS_GAME_ING = 2; // 游戏中
    public static final int STATUS_FIGURE_ING = 3; // 结算中
    public static final int STATUS_END = 4; // 牌局结束

    public static final int GAME_STATUS_TAKE = 0; // 摸牌中
    public static final int GAME_STATUS_WAIT = 1; // 等待操作中
    public static final int GAME_STATUS_SHOW = 2; // 展示操作中

    public static final int OVER_TYPE_NONE = 0; // 平局
    public static final int OVER_TYPE_PAO = 1; // 放炮
    public static final int OVER_TYPE_SELF = 2; // 自摸

    /**********表实体*************************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 牌局id
    private Long roomId; // 房间id
    private String roomNo; // 房间号
    private Long bankerId; // 庄家id
    private Integer serial; // 序号
    private Integer status; // 牌局状态
    private Long winner; // 赢家
    private Long loser; // 输家
    private Integer overType; // 结束类型
    private Date addTime; // 开始时间
    private Date updateTime; //结束时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public Long getWinner() {
        return winner;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
    }

    public Long getLoser() {
        return loser;
    }

    public void setLoser(Long loser) {
        this.loser = loser;
    }

    public Integer getOverType() {
        return overType;
    }

    public void setOverType(Integer overType) {
        this.overType = overType;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Long getBankerId() {
        return bankerId;
    }

    public void setBankerId(Long bankerId) {
        this.bankerId = bankerId;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
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

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
