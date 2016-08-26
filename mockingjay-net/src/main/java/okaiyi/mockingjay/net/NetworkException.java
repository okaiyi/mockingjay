package okaiyi.mockingjay.net;
/**
 * 网络请求异常
 *
 */
public class NetworkException extends Exception{
	private static final long serialVersionUID = -7665429596981786980L;
	
	public NetworkException(Throwable e){
		super(e);
	}
	
	public NetworkException(String msg){
		super(msg);
	}

}
