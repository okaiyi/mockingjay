package okaiyi.mockingjay.network.test;

import java.io.File;
import java.util.Map;
import java.util.Set;

import okaiyi.mockingjay.commons.fs.FileDescriptor;
import okaiyi.mockingjay.commons.fs.IOMonitor;
import okaiyi.mockingjay.net.NetworkException;
import okaiyi.mockingjay.net.http.HttpConnector;
import okaiyi.mockingjay.net.http.HttpCookie;
import okaiyi.mockingjay.net.http.HttpGetRequest;
import okaiyi.mockingjay.net.http.HttpHeaderNames;
import okaiyi.mockingjay.net.http.HttpResponse;
import okaiyi.mockingjay.net.http.okhttp.impl.OKHttpBlockConnection;
import okaiyi.mockingjay.net.parser.DownloadParser;
import okaiyi.mockingjay.net.parser.StringParser;

import org.junit.Test;

public class HttpTest {
	@Test
	public void testGet() throws NetworkException{
		HttpGetRequest req=new HttpGetRequest("http://www.zg163.com/");
		HttpConnector<String> conn=new OKHttpBlockConnection<String>();
		conn.setConnectTimeout(100);
		HttpResponse<String> html=conn.doRequest(req, new StringParser());
		System.out.println(html.getHttpStatus());
		Map<HttpHeaderNames, String> headers=html.getResponseHeader();
		Set<HttpHeaderNames> names=headers.keySet();
		for(HttpHeaderNames name:names){
			System.out.println(name+":"+headers.get(name));
		}
		for(HttpCookie c:html.getCookies()){
			System.out.println(c.getName()+":"+c.getValue());
		}
		System.out.println(html.getCharset());
	}
	@Test
	public void testHead(){
		System.out.println(HttpHeaderNames.ACCEPT.toString());
	}
	@Test
	public void downloadTest() throws NetworkException{
		HttpGetRequest req=new HttpGetRequest("http://www.chamatou.cn/mobile/app/chamatou_biz_1_5.apk");
		HttpConnector<FileDescriptor> conn=new OKHttpBlockConnection<>();
		DownloadParser parser=new DownloadParser();
		parser.setBuffer(8388608);
		parser.setMonitor(new IOMonitor() {
			
			@Override
			public void process(int available, int total, int length) {
				System.out.println("a:"+available+",t:"+total+",l:"+length);
			}
		});
		HttpResponse<FileDescriptor> fd=conn.doRequest(req,parser);
		System.out.println(fd.getResponseData().getData().getFile().getAbsolutePath());
	}
	@Test
	public void windows(){
		String path="C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\";
		File file=new File(path);
		System.out.println(file.isFile());
		System.out.println(file.isDirectory());
	}
}
