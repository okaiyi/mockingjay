package okaiyi.mockingjay.net.http;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class HttpGetRequest extends HttpRequest{
	private URI uri;
	
	private String charset;
	
	public HttpGetRequest(String url){
		String param=url.toLowerCase();
		if(param.startsWith("http://")){
			setProtocol(HttpRequestProtocol.HTTP);
			uri=URI.create(url);
		}else if(param.startsWith("https://")){
			setProtocol(HttpRequestProtocol.HTTPS);
			uri=URI.create(url);
		}else{
			setProtocol(HttpRequestProtocol.HTTP);
			uri=URI.create("http://"+url);
		}
		setPort(uri.getPort());
		charset=DEFAULT_CHARSET;
	}
	
	@Override
	public String getHostOrDomain() {
		return uri.getHost();
	}

	@Override
	public URI getURI() {
		return uri;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.GET;
	}

	@Override
	public URL getURL() {
		try {
			return uri.toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	
}
