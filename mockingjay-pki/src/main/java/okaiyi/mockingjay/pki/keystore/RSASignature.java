package okaiyi.mockingjay.pki.keystore;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA数据签名
 *
 */
public class RSASignature {
	private static  PrivateKey defPrivateKey;
	
	
	private static  PublicKey defPublicKey;
	
	static{
		try {
			defPrivateKey=KeyUtils.buildRSAPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJHahcoyOgre9SWHGWnCUmvq4eWWRXNrwLFokdeJ+FEhRmetuuXJzutFxio5EGUpmA94JWglzowH3PNWig/M8iI20cgDk1gRVsH4lgyq7CwDEOoHB1X5+By8bh7yN7LnQ/BWgOeoX5XB0QbwZgAEODqcWp5enkb/VdWxRL6xheXFAgMBAAECgYBisnjuhmZ4jL9fEbjEtFaJ6HvMnI+Kt1PBuX1XWWnLnLTD2/ApJxL6alDaqZrKmgboEOlfPJY0c+20TM9IOetu616XgeXFCYgwU1FaNxs8z5Nfku+uq7Vc8G/PmA1tDA38ZQKK0+pCwLSVgbLyCVghA8De/Lz9ahCxET74LKW/gQJBAN9STzCiYub5bW+jQTI5ETc4xPCRiY2VQOPt8wpKZN48K3LJh0+9gyWkgd+do1N87trTnVm9ikumooyA8gVYVlkCQQCnMj326INKA5diN3S4JJHm4btv6l8GpqXICHjjoFbS9XqpHqjJ7hHmdWcJBZNR41f4p9hq2Ar1r9QGN2dJP7VNAkA3kTB5Jn67gCbV12Zai86X0f1W6q+yVgf+bu/BiL9OeORKvJB5q+sdC7DxJjVEidqZAnytnrbxmfXqRg9yeweJAkA03jMvxtf06GvO98AllhK4kfpF6jNAFCPpu5wWRhYOQOcXYQV4TJpGaCs7dBLgWuXvT4ukRUo54+5ERPKY+9clAkEAjVd/hMTE3rtuNltmGvlWjBKscWGLP7MNTLKAOTeV0Mx8MfzT5Lex6e57ErHPs3tdJqcti23BSyWTVoSogGxYRw==");
			defPublicKey=KeyUtils.buildRSAPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCR2oXKMjoK3vUlhxlpwlJr6uHllkVza8CxaJHXifhRIUZnrbrlyc7rRcYqORBlKZgPeCVoJc6MB9zzVooPzPIiNtHIA5NYEVbB+JYMquwsAxDqBwdV+fgcvG4e8jey50PwVoDnqF+VwdEG8GYABDg6nFqeXp5G/1XVsUS+sYXlxQIDAQAB");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		
	}
	
	private  PrivateKey privateKey;
	private  PublicKey publicKey;
	private static final String signAlgorith="SHA1WithRSA";
	private Signature sign;
	private Signature verify;
	private String charset;
	public RSASignature(PrivateKey pk,PublicKey pubk,String charset){
		try {
			this.charset=charset;
			if(pk!=null){
				privateKey=pk;		
				sign=Signature.getInstance(signAlgorith);
				sign.initSign(privateKey);
			}
			if(pubk!=null){
				publicKey=pubk;	
				verify=Signature.getInstance(signAlgorith);
				verify.initVerify(publicKey);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param pk 签名私钥
	 * @param pubk 签名公钥
	 * @param charset 字符集
	 */
	public RSASignature(String pk, String pubk,String charset) {
		try {
			this.charset=charset;
			if(pk!=null){
				privateKey=KeyUtils.buildRSAPrivateKey(pk);		
				sign=Signature.getInstance(signAlgorith);
				sign.initSign(privateKey);
			}
			if(pubk!=null){
				publicKey=KeyUtils.buildRSAPublicKey(pubk);	
				verify=Signature.getInstance(signAlgorith);
				verify.initVerify(publicKey);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	public  byte[] signByP1(String text){
		byte[] bytes=null;
		try {
			bytes = text.getBytes(charset);
			sign.update(bytes);
			return sign.sign();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 验签
	 * @param key
	 * @param b64sign
	 * @param text
	 * @param charset
	 * @return
	 */
	public static final boolean verifyP1(PublicKey key,String b64sign,String text,String charset){
		try {
			Signature v=Signature.getInstance(signAlgorith);
			v.initVerify(key);
			byte[] bytes=text.getBytes(charset);
			byte[] sign=Base64.decodeBase64(b64sign);
			v.update(bytes);
			return v.verify(sign);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static final String defaultSignByP1(String text){
		return signByP1(defPrivateKey, text, "utf-8");
	}
	public static final boolean defaultVerifyByP1(String b64Sign,String text){
		return verifyP1(defPublicKey, b64Sign, text, "utf-8");
	}
	
	public static final byte[] signByP1ToBytes(PrivateKey key,String text,String charset){
		try {
			Signature s=Signature.getInstance(signAlgorith);
			s.initSign(key);
			byte[] bytes=text.getBytes(charset);
			s.update(bytes);
			return s.sign();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * 签名
	 * @param key
	 * @param text
	 * @param charset
	 * @return
	 */
	public static final String signByP1(PrivateKey key,String text,String charset){
		return Base64.encodeBase64String(signByP1ToBytes(key, text, charset));
	}
	/**
	 * 用自有秘钥进行P1签名
	 * @param text 签名原文
	 * @return 签名失败返回原文
	 */
	public  String signByP1ToString(String text){
		byte[] bytes=signByP1(text);
		if(bytes!=null){
			return Base64.encodeBase64String(bytes);			
		}
		return text;
	}
	public boolean verifyP1(String text,byte[] signByte){
		String b64Sign=Base64.encodeBase64String(signByte);
		return verifyP1(text, b64Sign);
	}
	/**
	 * 验证签名
	 * @param text 原文
	 * @param b64Sign 签名值得base64编码
	 * @return
	 */
	public  boolean verifyP1(String text,String b64Sign){
		try {
			byte[] bytes=text.getBytes(charset);
			byte[] sign=Base64.decodeBase64(b64Sign);
			verify.update(bytes);
			return verify.verify(sign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static PrivateKey getDefaultPrivateKey(){
		return defPrivateKey;
	}
	public static PublicKey getDefaultPublicKey(){
		return defPublicKey;
	}
}