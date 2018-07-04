package com.modianli.power.payment.alipay.config;

import com.google.common.collect.Maps;

import com.modianli.power.payment.alipay.util.AlipaySubmit;
import com.modianli.power.payment.model.PaymentForm;

import java.util.Map;

public class AlipayConfig {

  // 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
  public static String partner = "2088721060884721";

  // 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
  public static String seller_id = partner;

  //商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
  public static String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCmFUqi9a8PJL29Z4usz3BIGGbhVCMbOCn0DVM807V+e3Pe7GGmfHTvnj667BBLSvlOepGDV//TIIadMNyxghbGPKbrKfBGVMGB35hHoxxljlKOpnUlvyQpSpBGE+DXf1yMyKCFNHn72XTlNnC0TdXdkWrAiG+ilq9g9cfhVeAydRRYm3RPLVufY974NSbCFvH6vPh3JGmsdLDgmeyLxXYnNvP7cnDNIlBUqLiLW5+8ogH6kiRFYxQfZw08zeIZ/LABDWPi37rU6uQZiprc/WL7T1esjz2UT8DTAq5QNblb+Xw3NpzMTs4IGktOqu3TuYK7CPyaUYMrJnMXdsErV7P7AgMBAAECggEAB29wlfcVzUHB1Nbr4+Ktjiy5fmD76V4MUsa98T0xJOZ1+4btoDx2J7wX5tpmqFhE7QSDuzOXWmZcYHEtkCzYxCs29dQ804k72IqgEKT2wn0qqQQ+vl/eSJLz/o8pQ7yJ8iuRNCVzSJklqJVVWl0Zs/+snc394XOJine2aRolHV0aAZ1JCYenOFYcS9IcmCDaq38SCb+SV4I7LhMRTd+EGFyCHSNoAdJ/XquDvHGVVSFFNECBNyuG0ol/jW3kBVUXGDhP24aDS1QqDnwVurnDvytP3/tTwNWyWZ7ROzVoqtEJIx0flexxf5J7mAIHCW1z01HEIuvp+Kkgb3ZEQXr18QKBgQDUWXcxq8kBxKOmMPpdxUo+eq2rLsikpPXz1CKqUmA8D7/z9VyQcZIH1F76EVB3oa8gT4EbYwZoXHX6SRzrntkWCuJR2TrW3Qbhezumcb/XmvcL8QOFf641jhZFuWqtjBxAQPyUYMVI/bNkrHEBpiKYViHaqmaBVaQzar0lc//FNwKBgQDIOSOuEAyCVzteIOPYUrRgDk39dkmc+SK2vWSkCjUQ60MhYp2Kvh2HZVZF5Zk5UbDjoSUmOsWMsaM8k++oi3IXfc+fZsxB3cMVDzOJ5k+m3eWN7pIy6yWKoXvz16dIhTOeXruhMh9LzSbuGDmoKxqO4sHV95eD9xe0XiLcIYLpXQKBgGbgvU2UQ3sW0I4otnONrM2eU9tWfCDARuSNhwnrhPI0UzQXUHQugahHbIhMsSZ0b8sAmbUyAZ24BfRvTZKdz+DM/x7WVppYVQgS0zBtdZs4Wa4wYxtfY4BQyVuYXCWeduVuVrBwPcSCHVKN9OXunl4dhKNS4PySCE5czSyDCYx7AoGBAJ6DxkCvCghC4YiDB3+i8pS9nncVJ/BzuUztonjQb/TGHS6YMVu/agdgbi4GOQmNKDr+wDeE/hOGK1LxWCKay6fXfgGX1Awtcp4Am/AH+3WDZZK5lttjGSSo9kY1rIWYlyZBn9BdqzNK5xBG85a1EWCgG4+3Zige/pBYmsambUstAoGBAMQry/I7s3dYIZPpVSlWSTNJ1OpsSISYNODu+Qjp4SFh8EuP2ncSgZe634HDpr41dDiIJUxNL2jFuAwsLIAxYGfeVVxU8Lh7iBxDEso5zostwhE2rI3v+vMRXjcOZyvI5l82I7TRK8j6Kp5V+wwH6GyGncz5C0B1xyQT1vlTq1Vx";

// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
  public static String
	  alipay_public_key =
	  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

   /* // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  public static String notify_url = "http://zhj.tunnel.qydev.com/api/public/orders/sync/verify";

  // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  public static String return_url = "http://zhj.tunnel.qydev.com/api/public/orders/verify";*/

  // 签名方式
  public static String sign_type = "RSA";

  // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
  public static String log_path = "/tmp";

  // 字符编码格式 目前支持 gbk 或 utf-8
  public static String input_charset = "utf-8";

  // 支付类型 ，无需修改
  public static String payment_type = "1";

  // 调用的接口名，无需修改
  public static String service = "create_direct_pay_by_user";

  // 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
  public static String anti_phishing_key = "";

  // 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
  public static String exter_invoke_ip = "";

  // 可用的支付渠道，用户只能在指定渠道范围内支付
  // https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.mShrbf&treeId=62&articleId=104743&docType=1#s6
  public static String enable_paymethod = "directPay^moneyFund^bankPay";

  // 商品类型
  // 1表示实物类商品 0表示虚拟类商品  默认为实物类商品
  public static String goods_type = "0";

  //设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭
  public static String it_b_pay = "30m";

  public static Map<String, String> buildRequestParams(PaymentForm paymentForm){
    //支付信息
    Map<String, String> paymentParams = Maps.newHashMap();
    paymentParams.put("service", AlipayConfig.service);
    paymentParams.put("partner", AlipayConfig.partner);
    paymentParams.put("seller_id", AlipayConfig.seller_id);
    paymentParams.put("_input_charset", AlipayConfig.input_charset);
    paymentParams.put("payment_type", AlipayConfig.payment_type);
    paymentParams.put("it_b_pay", AlipayConfig.it_b_pay);
    //paymentParams.put("notify_url", AlipayConfig.notify_url);
    //paymentParams.put("return_url", AlipayConfig.return_url);
    //paymentParams.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
    //paymentParams.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
    paymentParams.put("enable_paymethod", AlipayConfig.enable_paymethod);

    paymentParams.put("out_trade_no", paymentForm.getSerialNumber());//订单号
    paymentParams.put("subject", paymentForm.getSubject());//商品名称
    paymentParams.put("total_fee", /*paymentForm.getTotalAmount().toString()*/"0.01");//商品价格
    paymentParams.put("body", paymentForm.getSubjectDescription());//商品描述

    //待请求参数数组
    return AlipaySubmit.buildRequestPara(paymentParams);
  }

  public static Map<String, String> buildRequestParams(PaymentBeanConfig.AlipayConfigDetails alipayConfigDetails, PaymentForm paymentForm){
    //支付信息
    Map<String, String> paymentParams = Maps.newHashMap();
    paymentParams.put("service", AlipayConfig.service);
    paymentParams.put("partner", alipayConfigDetails.getPartner());
    paymentParams.put("seller_id", AlipayConfig.seller_id);
    paymentParams.put("_input_charset", AlipayConfig.input_charset);
    paymentParams.put("payment_type", AlipayConfig.payment_type);
    paymentParams.put("enable_paymethod", AlipayConfig.enable_paymethod);
    paymentParams.put("it_b_pay", AlipayConfig.it_b_pay);

    paymentParams.put("partner", AlipayConfig.partner);
    paymentParams.put("seller_id", AlipayConfig.seller_id);
    paymentParams.put("notify_url", alipayConfigDetails.getNotifyUrl());
    paymentParams.put("return_url", alipayConfigDetails.getReturnUrl());

    paymentParams.put("out_trade_no", paymentForm.getSerialNumber());//订单号
    paymentParams.put("subject", paymentForm.getSubject());//商品名称
    paymentParams.put("total_fee", paymentForm.getTotalAmount().toString());//商品价格
    paymentParams.put("body", paymentForm.getSubjectDescription());//商品描述

    //待请求参数数组
    return AlipaySubmit.buildRequestPara(paymentParams);
  }
}

