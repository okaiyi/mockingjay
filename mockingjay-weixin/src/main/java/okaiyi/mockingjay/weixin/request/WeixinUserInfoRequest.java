package okaiyi.mockingjay.weixin.request;

import okaiyi.mockingjay.weixin.WeixinInfo;
import okaiyi.mockingjay.weixin.response.WeixinUserInfoResponse;

/**
 * 如果网页授权作用域为snsapi_userinfo，
 * 则此时开发者可以通过access_token和openid拉取用户信息了。
 *
 */
public class WeixinUserInfoRequest extends WeixinRequest<WeixinUserInfoResponse>{
	private String accessToken;
	private String openId;
	public WeixinUserInfoRequest(WeixinInfo info,String accessToken,
			String openId) {
		super(info);
		this.accessToken=accessToken;
		this.openId=openId;
	}

	@Override
	public String getUrl() {
		return "https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
	}

	@Override
	protected WeixinUserInfoResponse parseToResponse(String result) {
		return new WeixinUserInfoResponse(result);
	}
	
}
