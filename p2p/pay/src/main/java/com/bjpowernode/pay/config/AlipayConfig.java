package com.bjpowernode.pay.config;

/**
 * 配置支付帐户有关信息及返回路径
 * 
 */
public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓如下配置账户基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static final String APP_ID =  "2016091500513331";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
   
      public static final String MERCHANT_PRIVATE_KEY ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCEVWcnNUmAcOvMub/OdhvQ3CZ3yxzRrCNrT6yMbD3xOhdVkqRDt+GpzJcRIUWS/ttK6zNgVXKoOVT7t2NOKPRO1nmmaNzOcTFhWUbzobPb5/3NVJ6mS1qd5g+vRie36nDu0bZlgUpF9gv4fRGFTtlzpyeUN5oLWEgFWQdeBrKFBTQtV5YfktJZNDkOq9EdRs7laVsDVOZ+dg6V/ERa680DeDtH0GZp5Z3n43v3t/YPltg7GdpD2gLqMVlxpUIF/EXEzmXr5wJa0u4fPlZ7rYP+F3tOi/7CGPSXJgySCsEqUQBMrU7KmjWdmPNqrUFIRUjurEy7SN+2WolHj9AphMzlAgMBAAECggEASLIpwo4xXI+r2YFI7wBO84Oc1j4MDNrFclQIc/Oaa4QAAZBXEKRZIVA0xMJEWCYNDbD7PjZykBs+pjDuKqew3gZQOaxtcDMab2H96iSwi2N8N2n+5VeL5iXWA/FELHJrq6YkzsohpSEvHkiGcXcJWdE/mtqYkxbiKZ3owCxpsUyGURYKKVrPenv0OSHCtbHXknSm9tILryH7gZRj0TM8344u8yXmQepjw4HBxjVrRq4k2qq+MmbFAHZKKhDbrSaRx3TqOWtGBe21mpwGMQtXVl0Et37VeIqSw9Yu2egXmyPoOhOtkk7SzzllAd0QobgAIZxZQjmF8S1zBzy6+LokAQKBgQDgbCLMmGFlf/yHBEr0O5qiSPjpNHu2kQfeJcq7r/Q3vc61UwoBxoRRLeXbFARlw7YRlYrg+JemJPKkqcv6U1CF9PcD8HpiuiZF1PWPxEfnzRjC8+h4phkV2/mSAlJ4DUTR4kl3uuerJcalSyPQ6bwZgb8ypY560kdUvR71ee8qZQKBgQCW9Ci39GghDaRMrTplHM0lDrFQCS1yE8JyV+62Leka62QpaGdFKuCVdKaDWg7ThTkUhHt4VxIDhbHEb5j1V3BMDJrF9NfmV57v4OubHr9Ulqz7XJ5ltAMWygOhQd1zHWRxqznnUQci4Pkrssj6Djy3ufl35ktH5xR/d/gKrD+wgQKBgDIBhmOGVLV0JkXDfBwdp+AfWG9GRYaxnEowq5LUqxs8jPG0Vhd9L4CTNJNzS2ONj2UtqCtm2QupUfPE7fGSXO/sXJohHHQhPGP1Bt6JAuiwt6LBDHzNretD/8E8CTo84qfNv0cRe4uUOEYejxV+723TjlHvt+bAMiq8lUhnahZNAoGAPuK8S9YvwwVR2veXiiPzemqih3srSYjdypDDS3XY1HQXqABsdS4lnV2ZOMM9xfSMEPYYEcwEHx+FLRZonFyu1Z+yoqEbiEQcXEZRILAweObEbXrqKehSGR5TYMx1ms012PzCBJGG7YrXgtLaUwG3XbrGKKLx3aRwr6Gk3Z1gcwECgYEA37oYlr/qqxFhPsm+/BCdkzv3mclPdJJPD9G3oSgVMNbDPSiuUyVBnMnE1m8DOW/S1vkxEod6fhuu81w/vi8F9FeLK7nMasPfcTWkLkFxSjaR5XdwtK3Usw0Ww1tU6osHlO+/6A1YxP4vnoTUD3VZreGqe+tRQffgZsgGnYIOZz4=";
	// 支付宝公钥，对应APPID下的应用公钥。
      public static final String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3khRPTcsrhvOub4YkqwU+0psFJXivrvHPHdhxo0AwwqJFc5xID8Td7TBwh32ZfA2eTJqbY5aRJg2ejpwpHDM3TQtSDrFlFabvZoKKKelQmXLbZy9eFDjCvZ0y+eFd65JOUiQVJ18Csn/L1OOrsf3EFD9V699EZELIySw1zGyAEprkIN7bwrCep7ez6UkIPJG32ZfRpgxsBGRuvnVqHETaNBureddkQhFGLq6fnphfCi1dNKGwvSroi7VNKNiYcquLrKd+uBxfu244sPMrdGYgbDO7yLwwUprzerG032vxLK6UNWLFhjAfC+Mw+66w/r/zyH3LrK8qhTslYUfpCNMawIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static final String NOTIFY_URL = "http://localhost:8083/pay/api/alipayNotify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
	public static final String RETURN_URL = "http://localhost:8083/pay/api/alipayBack";

	// 签名方式
	public static final String SIGN_TYPE = "RSA2";
	
	// 字符编码格式
	public static final String CHARSET = "utf-8";
	
	// 数据格式
	public static final String JSON = "json";
	
	// 支付宝网关（沙箱环境）
	public static final String GATEWAYURL = "https://openapi.alipaydev.com/gateway.do";
	
	//↑↑↑↑↑↑↑↑↑↑以上配置账户基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}