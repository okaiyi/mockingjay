package okaiyi.mockingjay.weixin.request;

import okaiyi.mockingjay.weixin.WeixinInfo;
import okaiyi.mockingjay.weixin.response.WeixinAccessTokenResponse;

public class WeixinAccessTokenRequest extends WeixinRequest<WeixinAccessTokenResponse>{

	public WeixinAccessTokenRequest(WeixinInfo info) {
		super(info);
	}

	@Override
	public String getUrl() {
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&"
				+ "appid="+weixinInfo.getAppId()+"&secret="+weixinInfo.getSecret();
	}

	@Override
	protected WeixinAccessTokenResponse parseToResponse(String result) {
		return new WeixinAccessTokenResponse(result);
	}

}
