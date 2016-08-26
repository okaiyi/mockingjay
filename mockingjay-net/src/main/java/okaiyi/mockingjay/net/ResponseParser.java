package okaiyi.mockingjay.net;

import java.io.InputStream;

/**
 * 网络响应数据解析
 *
 */
public interface ResponseParser<T> {
	/**
	 * 对输入流进行解析
	 * @param in
	 * @return
	 */
	NetworkData<T> parse(InputStream in,String charset);
}
