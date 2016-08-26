package okaiyi.mockingjay.net.http;

import java.util.Map;
import java.util.Set;
import okaiyi.mockingjay.net.NetworkException;
import okaiyi.mockingjay.net.ResponseParser;

/**
 * Http同步阻塞式请求
 *
 */
public abstract class HttpBlockConnector<T> extends HttpConnector<T> {
	/**
	 * 在调用doRequest之前执行,顺序为onBefore,addRequestHeader,addRequestCookie,do(...);
	 */
	protected abstract void init(HttpRequest request) throws NetworkException;

	@Override
	public HttpResponse<T> doRequest(HttpRequest request,
			ResponseParser<T> parser) throws NetworkException {
		// 设置请求ID
		if (request.getIdentification() == null
				|| request.getIdentification().equals("")) {
			String nanoTime = String.valueOf(System.nanoTime());
			request.setIdentification(nanoTime);
		}
		request.addHttpHeader(HttpHeaderNames.HOST,request.getHostOrDomain());
		init(request);
		addRequestHeader(request.getHttpHeader());
		addRequestCookie(request.getCookies());
		HttpMethod method = request.getHttpMethod();
		HttpResponse<T> response = null;
		if (method.equals(HttpMethod.GET)) {
			response = doGet(request, parser);
		} else if (method.equals(HttpMethod.POST)) {
			response = doPost(request, parser);
		} else if (method.equals(HttpMethod.DELETE)) {
			response = doDelete(request, parser);
		} else if (method.equals(HttpMethod.HEAD)) {
			response = doHead(request, parser);
		} else if (method.equals(HttpMethod.OPTIONS)) {
			response = doOptions(request, parser);
		} else if (method.equals(HttpMethod.PUT)) {
			response = doPut(request, parser);
		} else if (method.equals(HttpMethod.FORM_FIEL_UPLOAD)) {
			response = doFormFileUpload(request, parser);
		}
		return response;
	}

	/**
	 * 处理get提交
	 * 
	 * @param request
	 * @param parser
	 * @return
	 */
	protected abstract HttpResponse<T> doGet(HttpRequest request,
			ResponseParser<T> parser) throws NetworkException;

	/**
	 * 处理post提交
	 * 
	 * @param request
	 * @param parser
	 * @return
	 */
	protected abstract HttpResponse<T> doPost(HttpRequest request,
			ResponseParser<T> parser) throws NetworkException;

	/**
	 * 处理delete
	 * 
	 * @param request
	 * @param parser
	 * @return
	 */
	protected abstract HttpResponse<T> doDelete(HttpRequest request,
			ResponseParser<T> parser) throws NetworkException;

	/**
	 * 处理options
	 * 
	 * @param request
	 * @param parser
	 * @return
	 */
	protected abstract HttpResponse<T> doHead(HttpRequest request,
			ResponseParser<T> parser) throws NetworkException;

	/**
	 * 处理put
	 * 
	 * @param request
	 * @param parser
	 * @return
	 */
	protected abstract HttpResponse<T> doOptions(HttpRequest request,
			ResponseParser<T> parser) throws NetworkException;

	/**
	 * 处理put
	 * 
	 * @param request
	 * @param parser
	 * @return
	 */
	protected abstract HttpResponse<T> doPut(HttpRequest request,
			ResponseParser<T> parser) throws NetworkException;

	/**
	 * 处理doFormFileUpload
	 * 
	 * @param request
	 * @param parser
	 * @return
	 */
	protected abstract HttpResponse<T> doFormFileUpload(HttpRequest request,
			ResponseParser<T> parser) throws NetworkException;

	protected abstract void addRequestCookie(Set<HttpCookie> cookies);

	protected abstract void addRequestHeader(
			Map<HttpHeaderNames, String> requestHeader);

}
