package com.yjy.test.game.web;

/**
 * web常量
 */
public abstract class Constants {
	/**
	 * 路径分隔符
	 */
	public static final String SPT = "/";
	/**
	 * 索引页
	 */
	public static final String INDEX = "index";
	/**
	 * 默认模板
	 */
	public static final String DEFAULT = "default";
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";
	/**
	 * 提示信息
	 */
	public static final String MESSAGE = "message";
	/**
	 * 跳转的显示名字
	 */
	public static final String REDIRECT_LEFT_NAME = "redirectLeftName";
	/**
	 * 跳转的链接
	 */
	public static final String REDIRECT_LEFT_URL = "redirectLeftUrl";
	/**
	 * 跳转的显示名字
	 */
	public static final String REDIRECT_RIGHT_NAME = "redirectRightName";
	/**
	 * 跳转的链接
	 */
	public static final String REDIRECT_RIGHT_URL = "redirectRightUrl";
	/**
	 * cookie中的JSESSIONID名称
	 */
	public static final String JSESSION_COOKIE = "JSESSIONID";
	/**
	 * url中的jsessionid名称
	 */
	public static final String JSESSION_URL = "jsessionid";
	/**
	 * HTTP POST请求
	 */
	public static final String POST = "POST";
	/**
	 * HTTP GET请求
	 */
	public static final String GET = "GET";
	
	public static final String appId = "wx5260b2091b7c616c";
	public static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN2B1eZl5foBzsZe"
																+ "RXGAQW+PttNSYi3N5XL+eS05EVJGVHyAX5qZKyEHdziJDla+/FDln4lcZx9UNT0R"
																+ "T6RknnX6biVKWuT+nrCzjb5lqiPuel6N3DOXvlrYh7JoNbBjIQI172exNM/3Tkbc"
																+ "Dt3JvZRjHpMiQgcF2LASZrmWvAEPAgMBAAECgYAz0EUaqcdL3dRibnbL//ZOhE19"
																+ "zQ2OLVV4urHZtDmrByyIGvdCEIYYKcjnfpfODsqymaPh2617lJUHfd8lUywFeWeU"
																+ "bRv+niLPHz2rGxcmITBCc5Woy5RD4KArfQqrFyKHguuW+C3AqpG/9PRc1/gniudF"
																+ "eHzeK6YDHcsXAAOH8QJBAPRnG4eVuPtJBJrLTbopP6Clk61wBzmClzW4Cs0CLT2b"
																+ "sK2SG76xlrfortdnRikrFEiei1C7LvOPMdfuFPDji40CQQDoBJpU6lDQ6ZLQK9cX"
																+ "NahqLhsz6sfq/Z5ZKqFoRArZJQUGl7ZtAagPB14gb9p8UXK59vTisB0Y/0TJPyam"
																+ "Z4oLAkEAgpU0mFTe6SQyWaKWfQjc/jB5nwlkUs37jLAw1Sh8k5ZD5/lQ6appJQol"
																+ "oOGFEyn5RHOxbAkXJPs1XnHOroZlYQJBALd5VFXMCWZ7HnbyBCA9dVWp6icPBVKl"
																+ "hj+6v3LFQ9LdPHEvLbpbsftYdy2tZhgjDFdUm+hbHldzHYELKFQ00msCQDV8NilV"
																+ "Vt/inj2VMgYibNJUehbF/s76TVN9GIPzXnxZU6eSMY+TxChHUSzFrgQwxes2NTpG"
																+ "wRq16ChOH9B8GeA=";
	
	public static final String alipayUrl = "https://openapi.alipay.com/gateway.do";
	
	/**
	 * 商户key
	 */
	public static final String wxpayKey = "zKrSnOxgAuX812spel0Y82UJob85yfLo";
	
	/**
	 * 商户号
	 */
	public static final String mchId = "1480889922";
	
	//=======【上报信息配置】===================================
	/**
	 * 接口调用上报等级，默认紧错误上报（注意：上报超时间为【1s】，上报无论成败【永不抛出异常】，
	 * 不会影响接口调用流程），开启上报之后，方便微信监控请求调用的质量，建议至少
	 * 开启错误上报。
	 * 上报等级，0.关闭上报; 1.仅错误出错上报; 2.全量上报
	 */
	public static final Integer REPORT_LEVENL = 1;
	
	/**
	 * 交易方式
	 */
	public static final String TRADE_TYPE = "JSAPI";
}
