package com.yjy.test.game.tencent.pojo;

import java.util.HashMap;
import java.util.Map;

public class WxPayReport {

	private String appid;
	private String macId;
	private String nonceStr;
	private String sign;
	private String interfaceUrl;
	private Integer executeTime;
	private String returnCode;
	private String resultCode;
	private String userIp;
	private String outTradeNo;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getInterfaceUrl() {
		return interfaceUrl;
	}
	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}
	public Integer getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(Integer executeTime) {
		this.executeTime = executeTime;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	public String toXML() {
		Map<String, Object> map = this.mapRes();
		String xml = "<xml>";
		for (Map.Entry<String, Object> entry : map.entrySet()) {  
			xml += xmlStr(entry.getKey(), (String)entry.getValue());
		}  
        xml +="</xml>";
        return xml; 
	}
	
	private String xmlStr(String key, String value) {
		return "<" + key + "><![CDATA[" + value + "]]></" + key + ">";
	}
	
	public Map<String, Object> mapRes() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", getAppid());
		map.put("mac_id", getMacId());
		map.put("nonce_str", getNonceStr());
		map.put("interface_url", getInterfaceUrl());
		map.put("execute_time", getExecuteTime());
		map.put("return_code", getReturnCode());
		map.put("result_code", getResultCode());
		map.put("user_ip", getUserIp());
		map.put("out_trade_no", getOutTradeNo());
		return map;
	}
	
}
