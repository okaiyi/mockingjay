package okaiyi.mockingjay.net;
/**
 * 网络同步请求连接器
 *
 */
public interface NetworkBlockConnector<REQ extends NetworkRequest,RESP extends NetworkResponse<T>,T> {
	/**
	 * 执行同步阻塞式的网络请求
	 * @param request 网络请求
	 * @param parser 解析网络请求
	 * @return
	 */
	RESP doRequest(REQ request,ResponseParser<T> parser)throws NetworkException;
	
}
