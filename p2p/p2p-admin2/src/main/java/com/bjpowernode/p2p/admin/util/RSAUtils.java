package com.bjpowernode.p2p.admin.util;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 签名处理工具类
 * 
 * @author yanglijun
 *
 */
public class RSAUtils {
	
	public static final String CHARSET = "utf-8";

	/**
	 * RSA 私钥签名
	 * 
	 * @param src
	 * @param priKey
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String sign (String src, String priKey) {
		try {
			Signature sigEng = Signature.getInstance("SHA1withRSA");
			byte[] pribyte = Base64.getDecoder().decode(priKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
			sigEng.initSign(privateKey);
			sigEng.update(src.getBytes(RSAUtils.CHARSET));
			byte[] signature = sigEng.sign();
			return Base64.getEncoder().encodeToString(signature);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA 公钥验证签名
	 * 
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @param encode
	 * @return
	 */
	public static boolean checkSign (String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.getDecoder().decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes(RSAUtils.CHARSET));
			boolean bverify = signature.verify(Base64.getDecoder().decode(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}