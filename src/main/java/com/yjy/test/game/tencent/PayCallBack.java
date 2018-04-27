package com.yjy.test.game.tencent;


/**
 * 支付成功回复
 * @author gwold-stt
 * @date 2017年5月27日
 */
public class PayCallBack{
    private String return_code;
    private String return_msg;

    public PayCallBack() {
        this.return_code = "SUCCESS";
        this.return_msg = "OK";
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
    
    public String toXML() {
    	String xml = "<xml>";
		xml += xmlStr("return_code", getReturn_code());
		xml += xmlStr("return_msg", getReturn_msg());
        xml +="</xml>";
        return xml; 
    }
    
    protected String xmlStr(String key, Integer value) {
		return "<" + key + ">" + value.toString() + "</" + key + ">";
	}
	
	protected String xmlStr(String key, String value) {
		return "<" + key + "><![CDATA[" + value + "]]></" + key + ">";
	}

}
