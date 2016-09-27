package okaiyi.mockingjay.weixin.response;
/**
 * 微信网页授权code后换取accessToken
 *
 */
public class WebAuthorizeAccessTokenResponse extends WeixinResponse{
	/**
	 * 构建微信响应
	 * @param msg 微信的返回码
	 */
	public WebAuthorizeAccessTokenResponse(String msg){
		super(msg);
		if(!isError()){
			//不存在错误
		}
	}
	/**
	 * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 */
	public String getAccessToken() {
		return getDataTypeToString("access_token");
	}
	/**
	 * access_token接口调用凭证超时时间，单位（秒）
	 */
	public String getExpiresIn() {
		return getDataTypeToString("expires_in");
	}
	/**
	 * 用户刷新access_token,由于access_token拥有较短的有效期，当access_token超时后，
	 * 可以使用refresh_token进行刷新，refresh_token有效期为30天，
	 * 当refresh_token失效之后，需要用户重新授权。
	 */
	public String getRefreshToken() {
		return getDataTypeToString("refresh_token");
	}
	/**
	 * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和
	 * 公众号唯一的OpenID
	 */
	public String getOpenId() {
		return getDataTypeToString("openid");
	}
	/**
	 * 用户授权的作用域，使用逗号（,）分隔
	 */
	public String getScope() {
		return getDataTypeToString("scope");
	}
	
	
}
