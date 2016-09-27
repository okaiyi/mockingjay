package okaiyi.mockingjay.weixin.response;

public class ApiTicketResponse extends WeixinResponse{

	public ApiTicketResponse(String msg) {
		super(msg);
	}
	/**
	 * 获取ticket
	 * @return
	 */
	public String getTicket(){
		return getDataTypeToString("ticket");
	}
	/**
	 * 获取超时时间
	 * @return
	 */
	public String getExpiresIn(){
		return getDataTypeToString("expires_in");
	}
}
