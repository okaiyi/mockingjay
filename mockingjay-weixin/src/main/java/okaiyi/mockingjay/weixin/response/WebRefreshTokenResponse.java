package okaiyi.mockingjay.weixin.response;
/**
 * 刷新accessToken后的响应
 *
 */
public class WebRefreshTokenResponse extends WebAuthorizeAccessTokenResponse{

	public WebRefreshTokenResponse(String msg) {
		super(msg);
	}
}
