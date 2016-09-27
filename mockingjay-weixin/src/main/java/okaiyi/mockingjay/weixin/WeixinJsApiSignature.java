package okaiyi.mockingjay.weixin;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 微信jsapi签名
 * 签名生成规则如下：

	参与签名的字段包括有效的 jsapi_ticket（获取方式详见微信 JSSDK 文档）， noncestr （随机字符串，
	由开发者随机生成），timestamp （由开发者生成的当前时间戳）， url（当前网页的URL，不包含#及其后面部分。
	注意：对于没有只有域名没有 path 的 URL ，浏览器会自动加上 / 作为 path，
	如打开 http://qq.com 则获取到的 URL 为 http://qq.com/）。
	对所有待签名参数按照字段名的 ASCII 码从小到大排序（字典序）后，
	使用 URL 键值对的格式（即key1=value1&key2=value2…）拼接成字符串 string1。
	这里需要注意的是所有参数名均为小写字符。
	接下来对 string1 作 sha1 加密，字段名和字段值都采用原始值，不进行 URL 转义。
	即 signature=sha1(string1)。

 *
 */
public class WeixinJsApiSignature {
	private String ticket;
	private String noncestr;
	private String timestamp;
	private String url;
	public WeixinJsApiSignature(String ticket, String noncestr,
			String timestamp, String url) {
		super();
		this.ticket = ticket;
		this.noncestr = noncestr;
		this.timestamp = timestamp;
		this.url = url;
	}
	
	public String getText(){
		return "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="
				+url;
	}
	public String getSignature(){
		return DigestUtils.sha1Hex(getText());
	}

	public String getTicket() {
		return ticket;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getUrl() {
		return url;
	}
	
}
