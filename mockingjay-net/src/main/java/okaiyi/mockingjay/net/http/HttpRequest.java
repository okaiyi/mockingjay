package okaiyi.mockingjay.net.http;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import okaiyi.mockingjay.net.NetworkRequest;
public abstract class HttpRequest implements NetworkRequest{
	
	public static enum HttpRequestProtocol{
		HTTP{
			@Override
			public String toString() {
				return "http";
			}
		},HTTPS{
			@Override
			public String toString() {
				return "https";
			}
		}
	}
	
	public static final int HTTP_DEFAULT_PROT=80;
	
	public static final int HTTPS_DEFAULT_PROT=443;
	
	private static int EMPTY_PORT=-1;
	//http端口
	private int port;
	//cookies
	private Set<HttpCookie> cookies;
	//协议
	protected HttpRequestProtocol protocol;
	//请求头
	private Map<HttpHeaderNames,String> httpHeader;
	//校验规则
	private HttpsHostVerifyer httpsHostVerifyer;
	
	public HttpsHostVerifyer getHttpsHostVerifyer() {
		return httpsHostVerifyer;
	}
	
	private String identification;
	@Override
	public String getIdentification() {
		return identification;
	}
	/**
	 * 设置请求唯一标示
	 * @param id
	 * @return
	 */
	public void setIdentification(String id){
		this.identification=id;
	}
	/**
	 * 设置证书校验规则,改设置在请求协议为https时有效
	 */
	public void setHttpsHostVerifyer(HttpsHostVerifyer httpsHostVerifyer) {
		this.httpsHostVerifyer = httpsHostVerifyer;
	}

	public HttpRequest(){
		port=EMPTY_PORT;
		protocol=HttpRequestProtocol.HTTP;
		cookies=new HashSet<HttpCookie>();
		setDefaultHttpHeader();
	}
	
	private void setDefaultHttpHeader(){
		httpHeader=new HashMap<HttpHeaderNames, String>();
		httpHeader.put(HttpHeaderNames.USER_AGENT,"Mozilla/5.0 (Windows NT 6.3; WOW64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
		httpHeader.put(HttpHeaderNames.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.8");
		httpHeader.put(HttpHeaderNames.ACCEPT,"text/html,application/xhtml+xml,application/json,text/css,text/javascript,application/xml;q=0.9,image/webp,*/*;q=0.8\"");
		httpHeader.put(HttpHeaderNames.CONNECTION, "keep-alive");
	}
	/**
	 * 获取http请求头
	 * @return
	 */
	public Map<HttpHeaderNames, String> getHttpHeader() {
		return httpHeader;
	}
	/**
	 * 添加http请求头
	 * @param httpHeader
	 */
	public void addHttpHeaders(Map<HttpHeaderNames, String> httpHeader) {
		Set<HttpHeaderNames> keys=httpHeader.keySet();
		for(HttpHeaderNames key:keys){
			this.httpHeader.put(key, httpHeader.get(key));
		}		
	}
	/**
	 * 添加http请求头
	 * @param names
	 * @param value
	 */
	public void addHttpHeader(HttpHeaderNames names,String value) {
		this.httpHeader.put(names,value);
	}

	public void setProtocol(HttpRequestProtocol protocol) {
		this.protocol = protocol;
	}



	@Override
	public int getPort() {
		if(port!=EMPTY_PORT){
			return port;
		}else{
			return getProtocol().equals(HttpRequestProtocol.HTTP.toString())?HTTP_DEFAULT_PROT:
				HTTPS_DEFAULT_PROT;			
		}
	}
	@Override
	public String getProtocol() {
		return protocol.toString();
	}
	/**
	 * 设置http请求端口
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * 获取cookies
	 * @return
	 */
	public Set<HttpCookie> getCookies() {
		return cookies;
	}
	/**
	 * 添加cookies
	 * @param cookies
	 */
	public void addCookies(Set<HttpCookie> cookies) {
		if(cookies!=null){
			for(HttpCookie cookie:cookies){
				this.cookies.add(cookie);
			}
		}
	}
	/**
	 * 添加cookie
	 * @param name cookie名称
	 * @param value cookie值
	 */
	public void addCookie(String name,String value) {
		this.cookies.add(new HttpCookie(name, value));
	}
	/**
	 * 获取http请求方法
	 * @return
	 */
	public abstract HttpMethod getHttpMethod();
}
