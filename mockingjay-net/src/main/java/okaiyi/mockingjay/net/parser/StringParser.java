package okaiyi.mockingjay.net.parser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import okaiyi.mockingjay.commons.utils.IOStreamUtils;
import okaiyi.mockingjay.net.NetworkData;
import okaiyi.mockingjay.net.ResponseParser;
import okaiyi.mockingjay.net.data.StringNetworkData;
public class StringParser implements ResponseParser<String>{
	private int buf;
	
	public StringParser(){
		buf=2048;
	}
	
	public StringParser(int buf){
		this.buf=buf;
	}
	
	@Override
	public NetworkData<String> parse(InputStream in,String charset) {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		IOStreamUtils.writeToStream(in, out, true,buf);
		try {
			return new StringNetworkData(new String(out.toByteArray(),charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
