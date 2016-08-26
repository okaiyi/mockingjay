package okaiyi.mockingjay.net;

import java.net.URI;
import java.net.URL;

/**
 * 网络请求
 */
public interface NetworkRequest {
	/**
	 * 默认字符集
	 */
	public static final String DEFAULT_CHARSET="utf-8";
	
	/**
	 * 获取请求端口
	 * @return
	 */
	int getPort();
	/**
	 * 获取请求协议名称
	 * @return
	 */
	String getProtocol();
	/**
	 * 获取主机名或域名
	 * @return
	 */
	String getHostOrDomain();
	/**
	 * 获取统一资源标识符
	 * @return
	 */
	URI getURI();
	/**
	 * 获取URL
	 * @return
	 */
	URL getURL();
	/**
	 * 获取请求唯一标示
	 * @return
	 */
	String getIdentification();
	/**
	 * 获取字符编码
	 * @return
	 */
	String getCharset();
}
