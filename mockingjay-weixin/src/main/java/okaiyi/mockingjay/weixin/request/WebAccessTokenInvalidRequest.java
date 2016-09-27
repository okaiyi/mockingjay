package okaiyi.mockingjay.weixin.request;

import okaiyi.mockingjay.weixin.WeixinInfo;
import okaiyi.mockingjay.weixin.response.WebAccessTokenInvalidResponse;

/**
 * 验证web授权的token是否有效
 *
 */
public class WebAccessTokenInvalidRequest extends WeixinRequest<WebAccessTokenInvalidResponse>{
	private String accessToken;
	private String openId;
	public WebAccessTokenInvalidRequest(WeixinInfo info,String accessToken,
			String openId) {
		super(info);
		this.accessToken=accessToken;
		this.openId=openId;
	}

	@Override
	public String getUrl() {
		return "https://api.weixin.qq.com/sns/auth?access_token="+accessToken+"&openid="+openId;
	}

	@Override
	protected WebAccessTokenInvalidResponse parseToResponse(String result) {
		return new WebAccessTokenInvalidResponse(result);
	}

}
