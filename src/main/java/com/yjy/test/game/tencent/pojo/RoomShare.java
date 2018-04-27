package com.yjy.test.game.tencent.pojo;

import com.yjy.test.game.entity.RoomUser;

import java.util.List;


/**
 * 分享后的房间查看信息
 *
 * @author wdy
 * @version ：2017年6月8日 下午5:08:58
 */
public class RoomShare {

    private String roomNo;//房间编号
    private Integer bankerMode;//上庄模式
    private Integer roundNum;//玩的圈数
    private Integer roomRoundNum;//房间的圈数

    private String myHeader;//我的头像
    private String myNickname;//我的昵称
    private Integer myIntegral;//我的钻石

    private List<RoomUser> list;//其他参与人

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getBankerMode() {
        return bankerMode;
    }

    public Integer getRoundNum() {
        return roundNum;
    }

    public Integer getRoomRoundNum() {
        return roomRoundNum;
    }

    public void setRoomRoundNum(Integer roomRoundNum) {
        this.roomRoundNum = roomRoundNum;
    }

    public String getMyHeader() {
        return myHeader;
    }

    public String getMyNickname() {
        return myNickname;
    }

    public Integer getMyIntegral() {
        return myIntegral;
    }

    public List<RoomUser> getList() {
        return list;
    }

    public void setBankerMode(Integer bankerMode) {
        this.bankerMode = bankerMode;
    }

    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
    }

    public void setMyHeader(String myHeader) {
        this.myHeader = myHeader;
    }

    public void setMyNickname(String myNickname) {
        this.myNickname = myNickname;
    }

    public void setMyIntegral(Integer myIntegral) {
        this.myIntegral = myIntegral;
    }

    public void setList(List<RoomUser> list) {
        this.list = list;
    }

}
