package okaiyi.mockingjay.net;
/**
 * 网络响应
 *
 */
public interface NetworkResponse<T>{
	/**
	 * 获取响应内容
	 * @return
	 */
	NetworkData<T> getResponseData();
}
