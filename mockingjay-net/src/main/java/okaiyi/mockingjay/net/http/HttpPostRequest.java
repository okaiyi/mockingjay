package okaiyi.mockingjay.net.http;

import java.util.HashMap;
import java.util.Map;

/**
 * http post请求
 *
 */
public class HttpPostRequest extends HttpGetRequest{
	
	private Map<String, Object> params;
	
	
	
	public HttpPostRequest(String url) {
		super(url);
		params=new HashMap<String, Object>();
	}
	
	public HttpPostRequest(String url,Map<String, Object> params){
		super(url);
		this.params=params;
	}
	/**
	 * 获取post请求参数
	 * @return
	 */
	public Map<String, Object> getParameter() {
		return params;
	}
	/**
	 * 设置post请求参数
	 * @param params
	 */
	public void setParameters(Map<String, Object> params) {
		this.params = params;
	}
	/**
	 * 添加http参数
	 * @param name
	 * @param value
	 */
	public void addParameter(String name,Object value){
		params.put(name, value);
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.POST;
	}
	
}
