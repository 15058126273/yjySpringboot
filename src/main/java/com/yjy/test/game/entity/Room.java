package com.yjy.test.game.entity;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 房间信息表
 *
 * @author wdy
 * @version ：2017年5月24日 下午5:56:03
 */
@Entity
@Table(name = "cg_room")
public class Room extends BaseEntity {

    private static final long serialVersionUID = 4595259586226595656L;

    public static final int STATUS_INIT = -1; // 等待初始化
    public static final int STATUS_NORMAL = 0; // 正常
    public static final int STATUS_CLOSE = 1; // 已关闭
    public static final int STATUS_WAIT_CLOSE = 2; // 等待关闭

    public static final int GAME_STATUS_READY = 0; // 准备开始
    public static final int GAME_STATUS_WAITING = 1; // 等待牌局开始
    public static final int GAME_STATUS_GAME = 2; // 牌局进行中

    public static final int CLOSE_TYPE_OVER = 0; // 牌局结束
    public static final int CLOSE_TYPE_NOBODY = 1; // 没有人
    public static final int CLOSE_TYPE_SERVER_DOWN = 2; // 逻辑服务器宕机
    public static final int CLOSE_TYPE_DISSOLVE = 3; // 房间解散
    public static final int CLOSE_TYPE_CANCEL = 4; // 房间未开始游戏

    public static final int START_MODE_HOST = 0; // 房主开始
    public static final int START_MODE_AUTO = 1; // 自动开始

    public static final int GAME_MODE_THREE = 0; // 三精
    public static final int GAME_MODE_FIVE = 1; // 五精

    public static final int SCORE_MODE_NORMAL = 0; // 普通模式
    public static final int SCORE_MODE_ADD5 = 1; // 逢五进一

    // 房间默认配置
    public static final String ROOMGAMES = "1,2,4,8,12"; // 房间可以玩的局数（默认）
    public static final String BASEINTEGRALS = "1,2,3,4,5"; // 底分
    public static final String COST_MULTIPLE = "1,1,1,1,1"; // 不同底分对应钻石消耗倍数

    /******** 表实体 *******************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 房间id
    private Long userId; // 创建人id
    private Long clubId; // 俱乐部id
    private String roomNo; // 房间号
    private Integer status; // 房间状态
    private Integer gameNum; // 局数
    private Integer baseIntegral; // 底分
    private Integer gameMode; // 游戏模式
    private Integer createCost; // 房间花费
    private Integer finishGame; // 完成局数
    private Integer closeType; // 房间关闭类型
    private Integer gameStatus; // 房间牌局状态
    private Integer scoreMode; // 玩法模式
    private Integer doublePao; // 是否放铳
    private Integer startMode; // 开始方式
    private Integer jingStart; // 是否起手一砍精
    private String serverAddr; // 所处服务器地址
    private Date addTime; // 创建时间
    private Date updateTime; // 更新时间

    // 不持久化的
    @Transient
    private int maxPerson = 3; // 房间最大玩家数
    @Transient
    private String nickName; // 昵称
    @Transient
    private String headImg; // 头像
    @Transient
    private String code; // 用户code
    @Transient
    private Integer playerNum; // 玩家人数
    @Transient
    private Integer num; // 房间的数量

    public Room() {
    }

    public Room(Long clubId, Integer gameNum, Integer baseIntegral, Integer gameMode, Integer scoreMode,
                Integer doublePao, Integer startMode, Integer jingStart) {
        this.clubId = clubId;
        this.gameNum = gameNum;
        this.baseIntegral = baseIntegral;
        this.gameMode = gameMode;
        this.scoreMode = scoreMode;
        this.doublePao = doublePao;
        this.startMode = startMode;
        this.jingStart = jingStart;
    }

    public Room(Long clubId, Long userId, Integer gameNum, Integer baseIntegral, Integer gameMode,
                Integer scoreMode, Integer doublePao, Integer startMode, Integer jingStart) {
        this.userId = userId;
        this.clubId = clubId;
        this.gameNum = gameNum;
        this.baseIntegral = baseIntegral;
        this.gameMode = gameMode;
        this.scoreMode = scoreMode;
        this.doublePao = doublePao;
        this.startMode = startMode;
        this.jingStart = jingStart;
        this.init();
    }

    private void init() {
        this.status = STATUS_INIT;
        this.gameStatus = GAME_STATUS_READY;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
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

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public Integer getJingStart() {
        return jingStart;
    }

    public void setJingStart(Integer jingStart) {
        this.jingStart = jingStart;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public Integer getScoreMode() {
        return scoreMode;
    }

    public void setScoreMode(Integer scoreMode) {
        this.scoreMode = scoreMode;
    }

    public Integer getDoublePao() {
        return doublePao;
    }

    public void setDoublePao(Integer doublePao) {
        this.doublePao = doublePao;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGameNum() {
        return gameNum;
    }

    public void setGameNum(Integer gameNum) {
        this.gameNum = gameNum;
    }

    public Integer getBaseIntegral() {
        return baseIntegral;
    }

    public void setBaseIntegral(Integer baseIntegral) {
        this.baseIntegral = baseIntegral;
    }

    public Integer getGameMode() {
        return gameMode;
    }

    public void setGameMode(Integer gameMode) {
        this.gameMode = gameMode;
    }

    public Integer getCreateCost() {
        return createCost;
    }

    public void setCreateCost(Integer createCost) {
        this.createCost = createCost;
    }

    public Integer getFinishGame() {
        return finishGame;
    }

    public void setFinishGame(Integer finishGame) {
        this.finishGame = finishGame;
    }

    public Integer getCloseType() {
        return closeType;
    }

    public void setCloseType(Integer closeType) {
        this.closeType = closeType;
    }

    public Integer getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(Integer gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Integer getStartMode() {
        return startMode;
    }

    public void setStartMode(Integer startMode) {
        this.startMode = startMode;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(Integer playerNum) {
        this.playerNum = playerNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


}
