package okaiyi.mockingjay.weixin;
/**
 * 微信信息,普通javabean
 *
 */
public class WeixinInfo {
	/**
	 * appid
	 */
	private String appId;
	/**
	 * 字符编码
	 */
	private String charset;
	/**
	 * 公众号的secret
	 */
	private String secret;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
}
