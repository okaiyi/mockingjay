package okaiyi.mockingjay.net.http;
import java.util.Map;
import java.util.Set;

import okaiyi.mockingjay.net.NetworkData;
import okaiyi.mockingjay.net.NetworkResponse;
public  class HttpResponse<T> implements NetworkResponse<T>{

	private HttpRequest request;
	
	private Set<HttpCookie> cookies;
	//http返回码
	private HttpStatus httpStatus;
	
	private NetworkData<T> networkData;
	
	private Map<HttpHeaderNames, String> responseHeader;
	
	private String charset;
	
	public HttpResponse(HttpRequest req){
		this.request=req;
		charset=req.getCharset();
	}
	/**
	 * 获取响应头
	 * @return
	 */
	public Map<HttpHeaderNames, String> getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(Map<HttpHeaderNames, String> responseHeader) {
		this.responseHeader = responseHeader;
	}
	/**
	 * 获取http状态码
	 * @return
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public String getIdentification() {
		return request.getIdentification();
	}

	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset){
		this.charset=charset;
	}
	/**
	 * 获取服务器返回的cookies
	 * @return
	 */
	public Set<HttpCookie> getCookies() {
		return cookies;
	}

	public void setCookies(Set<HttpCookie> cookies) {
		this.cookies = cookies;
	}
	
	public NetworkData<T> getResponseData() {
		return networkData;
	}
	public void setNetworkData(NetworkData<T> networkData) {
		this.networkData = networkData;
	}
	
}
