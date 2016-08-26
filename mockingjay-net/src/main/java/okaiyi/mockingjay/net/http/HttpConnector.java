package okaiyi.mockingjay.net.http;

import okaiyi.mockingjay.net.NetworkBlockConnector;
import okaiyi.mockingjay.net.NetworkException;
import okaiyi.mockingjay.net.ResponseParser;

public abstract class HttpConnector<T> implements NetworkBlockConnector<HttpRequest, HttpResponse<T>, T>{
	private int connectTimeout;//连接超时间隔
	private int writeTimeout;//写超时
	private int readTimeout;//读超时
	
	public HttpConnector(){
		connectTimeout=5000;
		writeTimeout=12000;
		readTimeout=12000;
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}


	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}


	public int getWriteTimeout() {
		return writeTimeout;
	}


	public void setWriteTimeout(int writeTimeout) {
		this.writeTimeout = writeTimeout;
	}


	public int getReadTimeout() {
		return readTimeout;
	}


	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}


	@Override
	public abstract HttpResponse<T> doRequest(HttpRequest request,
			ResponseParser<T> parser) throws NetworkException;
}
