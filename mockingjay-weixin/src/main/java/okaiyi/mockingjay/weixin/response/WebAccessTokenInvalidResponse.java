package okaiyi.mockingjay.weixin.response;
/**
 * 验证token是否有效
 *
 */
public class WebAccessTokenInvalidResponse extends WeixinResponse{

	public WebAccessTokenInvalidResponse(String msg) {
		super(msg);
	}
	/**
	 * token是否还有效
	 * @return true表示有效,false表示失效
	 */
	public boolean isInvalid(){
		return getErrorCode().equals("0");
	}
}
