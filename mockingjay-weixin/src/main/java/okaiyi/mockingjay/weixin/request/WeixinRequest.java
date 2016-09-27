package okaiyi.mockingjay.weixin.request;

import java.util.HashMap;
import java.util.Map;

import okaiyi.mockingjay.net.NetworkException;
import okaiyi.mockingjay.net.http.HttpConnector;
import okaiyi.mockingjay.net.http.HttpGetRequest;
import okaiyi.mockingjay.net.http.HttpMethod;
import okaiyi.mockingjay.net.http.HttpPostRequest;
import okaiyi.mockingjay.net.http.HttpRequest;
import okaiyi.mockingjay.net.http.HttpResponse;
import okaiyi.mockingjay.net.http.okhttp.impl.OKHttpBlockConnection;
import okaiyi.mockingjay.net.parser.StringParser;
import okaiyi.mockingjay.weixin.WeixinInfo;
import okaiyi.mockingjay.weixin.response.WeixinResponse;

/**
 * 微信请求
 */
public abstract class WeixinRequest<RESPONSE extends WeixinResponse>{
	protected WeixinInfo weixinInfo;
	
	public WeixinRequest(WeixinInfo info){
		this.weixinInfo=info;
	}
	/**
	 * 获取请求url
	 */
	public abstract String getUrl();
	/**
	 * 获取请求方式
	 * @return
	 */
	public HttpMethod getMethod(){
		return HttpMethod.GET;
	}
	/**
	 * 如果使用post方式提交,需要重写getPostParams方法
	 * @return
	 * @throws NetworkException 
	 */
	public RESPONSE doRequest() throws NetworkException{
		HttpConnector<String> conn=new OKHttpBlockConnection<String>();
		HttpRequest request=null;
		if(getMethod().equals(HttpMethod.GET)){
			request=new HttpGetRequest(getUrl());
		}else{
			request=new HttpPostRequest(getUrl(),getPostParams());
		}
		HttpResponse<String> result=conn.doRequest(request, new StringParser());
		return parseToResponse(result.getResponseData().getData());
	};
	
	protected abstract RESPONSE parseToResponse(String result);
	
	protected Map<String, Object> getPostParams(){
		return new HashMap<String, Object>();
	}
}
