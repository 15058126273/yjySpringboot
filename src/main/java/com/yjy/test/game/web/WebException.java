package com.yjy.test.game.web;

/**
 * 程序自定义异常的实现
 * @author wdy
 * @version ：2016年10月12日 上午11:14:48
 */
public class WebException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String code ;  //异常对应的返回码
    private String message;  //异常对应的描述信息
     
    public WebException() {
        super();
    }
 
    public WebException(String message) {
        super(message);
        this.message = message;
    }
 
    public WebException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
