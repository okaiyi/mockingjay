package okaiyi.mockingjay.net;

import java.io.InputStream;
/**
 * 网络响应数据解析
 *
 */
public abstract class ResponseParser<T> {
	protected int buf;
	
	public ResponseParser(){
		buf=DEFAULT_BUFF;
	}
	
	public int getBuffer() {
		return buf;
	}
	public void setBuffer(int buf) {
		this.buf = buf;
	}

	/**
	 * 默认
	 */
	public static final int DEFAULT_BUFF=2048;
	/**
	 * 对网络输入流进行解析
	 * @param in 网络输入流
	 * @param length 可读长度
	 * @param charset 字符编码
	 * @return
	 * @throws NetworkException
	 */
	public abstract NetworkData<T> parse(InputStream in,int length,String charset)throws NetworkException;
}
