package com.cardgame.flower.common.entity.room;

import com.yjy.test.game.entity.RoomGame;
import com.yjy.test.game.entity.RoomUser;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 房间
 * Created by yjy on 2017/7/3.
 */
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int STATUS_INIT = -1; // 等待初始化
    public static final int STATUS_NORMAL = 0; // 正常
    public static final int STATUS_CLOSE = 1; // 已关闭
    public static final int STATUS_WAIT_CLOSE = 2; //等待关闭

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

    // 房间默认配置
    public static final String ROOMGAMES = "1,2,4,8,12"; // 房间可以玩的局数（默认）
    public static final String BASEINTEGRALS = "1,2,3,4,5"; // 底分
    public static final String COST_MULTIPLE = "1,1,1,1,1"; // 不同底分对应钻石消耗倍数

    /*********参数**********************/
    private int maxPerson = 3; // 房间最大玩家数
    private long readyLastTime = 5000L; // 准备持续时间
    private long sendLastTime = 500L; // 发牌持续时间
    private long figureLastTime = 8000L; // 结算持续时间

    private boolean isFinish = false; //房间牌局是否结束

    /********表实体*******************/
    private Long id; // 房间id
    private Long userId; // 创建人id
    private Long clubId; // 俱乐部id
    private String roomNo; // 房间号
    private Integer status; // 房间状态
    private Integer gameNum; //局数
    private Integer baseIntegral; // 底分
    private Integer gameMode; // 游戏模式
    private Integer createCost; //房间花费
    private Integer finishGame; //完成局数
    private Integer closeType; //房间关闭类型
    private Integer gameStatus; // 房间牌局状态
    private Integer startMode; // 开始方式
    private String serverAddr; // 所处服务器地址
    private Date addTime; // 创建时间
    private Date updateTime; // 更新时间

    /********缓存******************/
    private Long[] seats = new Long[maxPerson]; // 座位 (固定3人)
    private Map<Long, RoomUser> userMap; // 房间用户信息集合
    private Set<Long> userSet; // 房间在线用户集合
    private Set<Long> playerSet; // 房间玩家集合
    private Set<Long> readySet; // 已准备的用户集合
    private Set<Long> removedSet; // 被踢除用户集合
    private RoomGame currentGame; // 当前牌局
    private Long currentBanker; // 当前庄家
    private Long lastFinishTime; // 最后一把牌的结束时间
    private Long planCloseTime; // 预设的房间关闭时间

    public Room() {}


    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }

    public long getReadyLastTime() {
        return readyLastTime;
    }

    public void setReadyLastTime(long readyLastTime) {
        this.readyLastTime = readyLastTime;
    }

    public long getSendLastTime() {
        return sendLastTime;
    }

    public void setSendLastTime(long sendLastTime) {
        this.sendLastTime = sendLastTime;
    }

    public Set<Long> getReadySet() {
        return readySet;
    }

    public void setReadySet(Set<Long> readySet) {
        this.readySet = readySet;
    }

    public long getFigureLastTime() {
        return figureLastTime;
    }

    public void setFigureLastTime(long figureLastTime) {
        this.figureLastTime = figureLastTime;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
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

    public String getRoomNo() {
        return roomNo;
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

    public Long[] getSeats() {
        return seats;
    }

    public void setSeats(Long[] seats) {
        this.seats = seats;
    }

    public Set<Long> getPlayerSet() {
        return playerSet;
    }

    public void setPlayerSet(Set<Long> playerSet) {
        this.playerSet = playerSet;
    }

    public RoomGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(RoomGame currentGame) {
        this.currentGame = currentGame;
    }

    public Long getCurrentBanker() {
        return currentBanker;
    }

    public void setCurrentBanker(Long currentBanker) {
        this.currentBanker = currentBanker;
    }

    public Set<Long> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<Long> userSet) {
        this.userSet = userSet;
    }

    public Map<Long, RoomUser> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Long, RoomUser> userMap) {
        this.userMap = userMap;
    }

    public Set<Long> getRemovedSet() {
        return removedSet;
    }

    public void setRemovedSet(Set<Long> removedSet) {
        this.removedSet = removedSet;
    }

    public Long getLastFinishTime() {
        return lastFinishTime;
    }

    public void setLastFinishTime(Long lastFinishTime) {
        this.lastFinishTime = lastFinishTime;
    }

    public Long getPlanCloseTime() {
        return planCloseTime;
    }

    public void setPlanCloseTime(Long planCloseTime) {
        this.planCloseTime = planCloseTime;
    }
}
