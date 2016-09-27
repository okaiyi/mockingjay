package okaiyi.mockingjay.weixin.request;

import okaiyi.mockingjay.weixin.WeixinInfo;
import okaiyi.mockingjay.weixin.response.WebRefreshTokenResponse;

/**
 * 刷新网页授权access_token
 * 
 */
public class WebRefreshTokenRequest extends WeixinRequest<WebRefreshTokenResponse>{
	private String refreshToken;
	/**
	 * 
	 * @param info 微信信息
	 * @param refreshToken 刷新时用到的token,通过WebAuthorizeAccessTokenResponse获取
	 */
	public WebRefreshTokenRequest(WeixinInfo info,String refreshToken) {
		super(info);
		this.refreshToken=refreshToken;
	}

	@Override
	public String getUrl() {
		return "https://api.weixin.qq.com/sns/oauth2/refresh_token?"
				+ "appid="+weixinInfo.getAppId()+"&grant_type=refresh_token"
						+ "&refresh_token="+refreshToken;
	}

	@Override
	protected WebRefreshTokenResponse parseToResponse(String result) {
		return new WebRefreshTokenResponse(result);
	}
	
}
