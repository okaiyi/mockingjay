package okaiyi.mockingjay.weixin.request;

import okaiyi.mockingjay.weixin.WeixinInfo;
import okaiyi.mockingjay.weixin.response.ApiTicketResponse;

/**
 * 获取微信js调用的ticket 
 * jsapi_ticket是公众号用于调用微信JS接口的临时票据。正常情况下，
 * jsapi_ticket的有效期为7200秒，通过access_token来获取。
 * 由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，
 * 影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket 。
 * 
 * 引入JS文件
	在需要调用JS接口的页面引入如下JS文件，（支持https）：http://res.wx.qq.com/open/js/jweixin-1.0.0.js
	如需使用摇一摇周边功能，请引入 http://res.wx.qq.com/open/js/jweixin-1.1.0.js
 * 
 */
public class ApiTicketRequest extends WeixinRequest<ApiTicketResponse>{
	private String accessToken;
	
	public ApiTicketRequest(WeixinInfo info,String accessToken) {
		super(info);
		this.accessToken=accessToken;
	}

	@Override
	public String getUrl() {
		return "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
	}

	@Override
	protected ApiTicketResponse parseToResponse(String result) {
		return new ApiTicketResponse(result);
	}


}
