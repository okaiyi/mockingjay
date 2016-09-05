package okaiyi.mockingjay.net.http.okhttp.impl;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okaiyi.mockingjay.commons.fs.FileMimeType;
import okaiyi.mockingjay.commons.utils.IOStreamUtils;
import okaiyi.mockingjay.net.NetworkException;
import okaiyi.mockingjay.net.ResponseParser;
import okaiyi.mockingjay.net.http.HttpBlockConnector;
import okaiyi.mockingjay.net.http.HttpCookie;
import okaiyi.mockingjay.net.http.HttpFileuploadRequest;
import okaiyi.mockingjay.net.http.HttpHeaderNames;
import okaiyi.mockingjay.net.http.HttpPostRequest;
import okaiyi.mockingjay.net.http.HttpRequest;
import okaiyi.mockingjay.net.http.HttpStatus;
import okaiyi.mockingjay.net.http.HttpRequest.HttpRequestProtocol;
import okaiyi.mockingjay.net.http.HttpResponse;
import okaiyi.mockingjay.net.http.HttpUtils;
import okaiyi.mockingjay.net.http.HttpsHostVerifyer;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpBlockConnection<T> extends HttpBlockConnector<T>{
	private static OkHttpClient okHttpClient;
	private OkHttpClient.Builder clientBuilder;
	private Request.Builder requestBuilder;
	public OKHttpBlockConnection(){
		if(okHttpClient==null){
			okHttpClient=new OkHttpClient();
		}
	}
	@Override
	protected void init(HttpRequest request) throws NetworkException {
		clientBuilder=okHttpClient.newBuilder();
		clientBuilder.connectTimeout(getConnectTimeout(), TimeUnit.MILLISECONDS);
		clientBuilder.writeTimeout(getWriteTimeout(),TimeUnit.MILLISECONDS);
		clientBuilder.readTimeout(getReadTimeout(),TimeUnit.MILLISECONDS);
		//设置https
		if(request.getProtocol().equals(HttpRequestProtocol.HTTPS.toString())
				&&request.getHttpsHostVerifyer()!=null){
			try {
				final HttpsHostVerifyer hostVerifyer=request.getHttpsHostVerifyer();
				X509TrustManager x509TrustManager=hostVerifyer.getTrustManager();
				SSLContext sslContext = SSLContext.getInstance("TLS");
				if(x509TrustManager==null){
					TrustManagerFactory trustManagerFactory=TrustManagerFactory.getInstance(
							TrustManagerFactory.getDefaultAlgorithm());
					trustManagerFactory.init((KeyStore)null);
					TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
				    if (trustManagers.length == 1&&trustManagers[0] instanceof X509TrustManager) {
				    	x509TrustManager = (X509TrustManager) trustManagers[0];				    	
				    }
				}
				if(x509TrustManager!=null){
					sslContext.init(null, new TrustManager[] {x509TrustManager}, null);
					SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
					clientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager);
					clientBuilder.hostnameVerifier(new HostnameVerifier() {
						@Override
						public boolean verify(String hostname, SSLSession session) {
							return hostVerifyer.verify(hostname, session);
						}
					});
				}
			} catch (NoSuchAlgorithmException e) {
				throw new NetworkException(e);
			}catch (KeyStoreException e) {
				throw new NetworkException(e);
			}catch (KeyManagementException e) {
				throw new NetworkException(e);
			} 
		}
		requestBuilder=new Request.Builder();
		requestBuilder.url(request.getURL());
	}
	//构建处理结果
	private HttpResponse<T> generatorHttpResponse(HttpRequest request,Response resp,ResponseParser<T> parser)throws NetworkException{
		HttpResponse<T> hr=new HttpResponse<T>(request);
		hr.setHttpStatus(HttpStatus.valueOf(resp.code()));
		Headers headers=resp.headers();
		Set<String> names=headers.names();
		Map<HttpHeaderNames, String> responseHeader=new HashMap<HttpHeaderNames, String>();
		for(String name:names){
			HttpHeaderNames hn=HttpHeaderNames.getHeaderByName(name);
			if(hn!=null){
				responseHeader.put(HttpHeaderNames.getHeaderByName(name),headers.get(name));						
			}
		}
		hr.setResponseHeader(responseHeader);
		//设置cookie
		String cookies=hr.getResponseHeader().get(HttpHeaderNames.SET_COOKIE);
		hr.setCookies(HttpUtils.parseCookies(cookies));
		String contentType=resp.header(HttpHeaderNames.CONTENT_TYPE.toString(),
				"text/html;charset=utf-8");
		String charset=HttpUtils.getCharsetByContentType(contentType);
		hr.setCharset(charset);
		InputStream in=resp.body().byteStream();
		String contentLength=resp.header(HttpHeaderNames.CONTENT_LENGTH.toString(),"0");
		hr.setNetworkData(parser.parse(in,contentLength==null?0:Integer.parseInt(contentLength), charset));
		IOStreamUtils.close(in);
		return hr;
	}
	
	@Override
	protected HttpResponse<T> doGet(HttpRequest request, ResponseParser<T> parser)throws NetworkException {
		try {
			Request req=requestBuilder.build();
			Response resp=clientBuilder.build().newCall(req).execute();
			if(resp.isSuccessful()){
				return generatorHttpResponse(request, resp, parser);
			}
		} catch (IOException e) {
			throw new NetworkException(e);
		}
		return null;
	}

	@Override
	protected HttpResponse<T> doPost(HttpRequest request, ResponseParser<T> parser)throws NetworkException {
		if(request instanceof HttpPostRequest){
			try {
				HttpPostRequest postRequest=(HttpPostRequest)request;
				FormBody.Builder formBuild=new FormBody.Builder();
				Map<String, Object> params=postRequest.getParameter();
				Set<String> keys=params.keySet();
				for(String key:keys){
					formBuild.add(key, params.get(key).toString());
				}
				Request req=requestBuilder.post(formBuild.build()).build();
				Response resp= clientBuilder.build().newCall(req).execute();
				if(resp.isSuccessful()){
					return generatorHttpResponse(request, resp, parser);
				}
			} catch (IOException e) {
				throw new NetworkException(e);
			}
		}else{
			return doGet(request, parser);
		}
		return null;
	}

	@Override
	protected HttpResponse<T> doDelete(HttpRequest request,
			ResponseParser<T> parser)throws NetworkException {
		return doPost(request, parser);
	}

	@Override
	protected HttpResponse<T> doHead(HttpRequest request, ResponseParser<T> parser)throws NetworkException {
		return doPost(request, parser);
	}

	@Override
	protected HttpResponse<T> doOptions(HttpRequest request,
			ResponseParser<T> parser)throws NetworkException {
		return doPost(request, parser);
	}

	@Override
	protected HttpResponse<T> doPut(HttpRequest request, ResponseParser<T> parser)throws NetworkException{
		return doPost(request, parser);
	}

	@Override
	protected HttpResponse<T> doFormFileUpload(HttpRequest request,
			ResponseParser<T> parser)throws NetworkException {
		if(request instanceof HttpFileuploadRequest){
			try {
				HttpFileuploadRequest fileRequest=(HttpFileuploadRequest)request;
				MultipartBody.Builder bodyBuilder=new MultipartBody.Builder();
				bodyBuilder.setType(MultipartBody.FORM);
				Map<String, Object> params=fileRequest.getParameter();
				Set<String> keys=params.keySet();
				for(String key:keys){
					bodyBuilder.addFormDataPart(key,params.get(key).toString());
				}
				Map<String,File> fileMap=fileRequest.getFileMap();
				keys=fileMap.keySet();
				for(String fileName:keys){
					File file=fileMap.get(fileName);
					String mimeType=FileMimeType.getMimeType(file);
					bodyBuilder.addFormDataPart(fileName, fileName,
							RequestBody.create(MediaType.parse(mimeType), file));
				}
				Request req=requestBuilder.post(bodyBuilder.build()).build();
				Response resp= clientBuilder.build().newCall(req).execute();
				if(resp.isSuccessful()){
					return generatorHttpResponse(request, resp, parser);
				}
			}catch (IOException e) {
				throw new NetworkException(e);
			}
			return null;
		}else{
			return doPost(request, parser);
		}
	}

	@Override
	protected void addRequestCookie(Set<HttpCookie> cookies) {
		if(!cookies.isEmpty()){
			requestBuilder.addHeader(HttpHeaderNames.COOKIE.toString(),
					HttpUtils.getCookiesString(cookies, HttpUtils.DEFAULT_CHARSET));
		}
	}

	@Override
	protected void addRequestHeader(Map<HttpHeaderNames, String> requestHeader) {
		Set<HttpHeaderNames> keys=requestHeader.keySet();
		for(HttpHeaderNames key:keys){
			requestBuilder.addHeader(key.toString(),requestHeader.get(key));
		}
	}
}
