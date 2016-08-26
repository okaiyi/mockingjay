package okaiyi.mockingjay.network.test;

import java.security.KeyStore;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okaiyi.mockingjay.net.NetworkException;
import okaiyi.mockingjay.net.http.HttpConnector;
import okaiyi.mockingjay.net.http.HttpCookie;
import okaiyi.mockingjay.net.http.HttpGetRequest;
import okaiyi.mockingjay.net.http.HttpHeaderNames;
import okaiyi.mockingjay.net.http.HttpResponse;
import okaiyi.mockingjay.net.http.HttpsHostVerifyer;
import okaiyi.mockingjay.net.http.HttpRequest.HttpRequestProtocol;
import okaiyi.mockingjay.net.http.okhttp.impl.OKHttpBlockConnection;
import okaiyi.mockingjay.net.parser.StringParser;

import org.junit.Test;

public class HttpTest {
	@Test
	public void testGet() throws NetworkException{
		HttpGetRequest req=new HttpGetRequest("http://www.zg163.net");
		HttpConnector<String> conn=new OKHttpBlockConnection<String>();
		HttpResponse<String> html=conn.doRequest(req, new StringParser());
		System.out.println(html.getHttpStatus());
		Map<HttpHeaderNames, String> headers=html.getResponseHeader();
		Set<HttpHeaderNames> names=headers.keySet();
		for(HttpHeaderNames name:names){
			System.out.println(name+":"+headers.get(name));
		}
		for(HttpCookie c:html.getCookies()){
			System.out.println(c.getName()+":"+c.getValue());
		}
		System.out.println(html.getCharset());
	}
	@Test
	public void testHead(){
		System.out.println(HttpHeaderNames.ACCEPT.toString());
	}
}
