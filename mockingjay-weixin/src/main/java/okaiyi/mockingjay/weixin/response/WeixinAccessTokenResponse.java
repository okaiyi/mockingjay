package okaiyi.mockingjay.weixin.response;

public class WeixinAccessTokenResponse extends WeixinResponse{

	public WeixinAccessTokenResponse(String msg) {
		super(msg);
	}
	/**
	 * 获取accessToken
	 * @return
	 */
	public String getAccessToken(){
		return getDataTypeToString("access_token");
	}
	/**
	 * 获取凭证有效时间,单位秒
	 * @return
	 */
	public String getExpiresIn(){
		return getDataTypeToString("expires_in");
	}
}
