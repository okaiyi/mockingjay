package okaiyi.mockingjay.weixin.request;

import okaiyi.mockingjay.weixin.WeixinInfo;
import okaiyi.mockingjay.weixin.response.WebAuthorizeAccessTokenResponse;

/**
 * 通过网页授权后获得的code来换取access_token
 *
 */
public class WebAuthorizeAccessTokenRequest extends WeixinRequest<WebAuthorizeAccessTokenResponse>{
	private String code;
	public WebAuthorizeAccessTokenRequest(WeixinInfo info,String code) {
		super(info);
		this.code=code;
	}

	@Override
	public String getUrl() {
		return "https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid="+weixinInfo.getAppId()+"&secret="+weixinInfo.getSecret()+"&"
						+ "code="+code+"&grant_type=authorization_code";
	}

	@Override
	protected WebAuthorizeAccessTokenResponse parseToResponse(String result) {
		return new WebAuthorizeAccessTokenResponse(result);
	}
	
}
