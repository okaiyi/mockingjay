package okaiyi.mockingjay.net;
/**
 * 网络请求数据
 *
 */
public interface NetworkData<T> {
	/**
	 * 获取数据
	 * @return
	 */
	T getData();
}
