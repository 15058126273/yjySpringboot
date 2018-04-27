package com.yjy.test.game.tencent.pojo;

import java.util.List;

public class WxConfig {

	private String appId;
	private String nonceStr;
	private String timestamp;
	private String signature;
	private String jsapiTicket;
	private List<String> jsApiList;
	
	////分享参数
	private String shareTitle;
	private String shareLink;
	private String shareDesc;
	private String shareImg;
	private String shareType;
	private String shareDataUrl;
	
	private ShareConfig shareConfig;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public List<String> getJsApiList() {
		return jsApiList;
	}
	public void setJsApiList(List<String> jsApiList) {
		this.jsApiList = jsApiList;
	}
	public String getJsapiTicket() {
		return jsapiTicket;
	}
	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public String getShareLink() {
		return shareLink;
	}
	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}
	public String getShareDesc() {
		return shareDesc;
	}
	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}
	public String getShareImg() {
		return shareImg;
	}
	public void setShareImg(String shareImg) {
		this.shareImg = shareImg;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getShareDataUrl() {
		return shareDataUrl;
	}
	public void setShareDataUrl(String shareDataUrl) {
		this.shareDataUrl = shareDataUrl;
	}
	public ShareConfig getShareConfig() {
		return shareConfig;
	}
	public void setShareConfig(ShareConfig shareConfig) {
		this.shareConfig = shareConfig;
	}
	
	
}
