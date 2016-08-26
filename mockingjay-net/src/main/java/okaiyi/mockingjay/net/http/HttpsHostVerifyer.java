package okaiyi.mockingjay.net.http;

import java.security.KeyStore;

import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * https主机校验
 *
 */
public interface HttpsHostVerifyer {
	/**
	 * 获取证书校验规则
	 * @return
	 */
	X509TrustManager getTrustManager();
	/**
	 * 主机校验
	 * @param hostname
	 * @param session
	 * @return 返回true代表校验通过
	 */
	boolean verify(String hostname, SSLSession session);
	/**
	 * 获取秘钥库
	 * @return
	 */
	KeyStore getKeyStore();
}
