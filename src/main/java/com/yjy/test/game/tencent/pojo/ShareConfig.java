package com.yjy.test.game.tencent.pojo;
/**
 * 分享参数
 * @author wdy
 * @version ：2017年6月12日 下午5:37:30
 */
public class ShareConfig {

    ////默认分享参数
	private String shareTitle;
	private String shareLink;
	private String shareDesc;
	private String shareImg;
	private String shareType;
	private String shareDataUrl;
	
	//房间信息分享
	private String roomShareTitle;
	private String roomShareLink;
	private String roomShareDesc;
	private String roomShareImg;
	
	//游戏结果分享
	private String resultShareTitle;
	private String resultShareLink;
	private String resultShareDesc;
	private String resultShareImg;
	
	public String getShareTitle() {
		return shareTitle;
	}
	public String getShareLink() {
		return shareLink;
	}
	public String getShareDesc() {
		return shareDesc;
	}
	public String getShareImg() {
		return shareImg;
	}
	public String getShareType() {
		return shareType;
	}
	public String getShareDataUrl() {
		return shareDataUrl;
	}
	public String getRoomShareTitle() {
		return roomShareTitle;
	}
	
	public String getRoomShareLink() {
		return roomShareLink;
	}
	public String getRoomShareDesc() {
		return roomShareDesc;
	}
	public String getRoomShareImg() {
		return roomShareImg;
	}
	public void setRoomShareLink(String roomShareLink) {
		this.roomShareLink = roomShareLink;
	}
	public void setRoomShareDesc(String roomShareDesc) {
		this.roomShareDesc = roomShareDesc;
	}
	public void setRoomShareImg(String roomShareImg) {
		this.roomShareImg = roomShareImg;
	}
	public String getResultShareTitle() {
		return resultShareTitle;
	}
	public String getResultShareLink() {
		return resultShareLink;
	}
	public String getResultShareDesc() {
		return resultShareDesc;
	}
	public String getResultShareImg() {
		return resultShareImg;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}
	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}
	public void setShareImg(String shareImg) {
		this.shareImg = shareImg;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public void setShareDataUrl(String shareDataUrl) {
		this.shareDataUrl = shareDataUrl;
	}
	public void setRoomShareTitle(String roomShareTitle) {
		this.roomShareTitle = roomShareTitle;
	}
	public void setResultShareTitle(String resultShareTitle) {
		this.resultShareTitle = resultShareTitle;
	}
	public void setResultShareLink(String resultShareLink) {
		this.resultShareLink = resultShareLink;
	}
	public void setResultShareDesc(String resultShareDesc) {
		this.resultShareDesc = resultShareDesc;
	}
	public void setResultShareImg(String resultShareImg) {
		this.resultShareImg = resultShareImg;
	}
	
	
}
