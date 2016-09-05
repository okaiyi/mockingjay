package okaiyi.mockingjay.net.parser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import okaiyi.mockingjay.commons.utils.IOStreamUtils;
import okaiyi.mockingjay.net.NetworkData;
import okaiyi.mockingjay.net.NetworkException;
import okaiyi.mockingjay.net.ResponseParser;
import okaiyi.mockingjay.net.data.TxtNetworkData;
public class StringParser extends ResponseParser<String>{
	public StringParser(){
		super();
	}
	public StringParser(int buf){
		this();
		this.buf=buf;
	}
	
	@Override
	public NetworkData<String> parse(InputStream in,int available,String charset)
	throws NetworkException{
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		IOStreamUtils.writeToStream(in, out, true,buf);
		try {
			return new TxtNetworkData(new String(out.toByteArray(),charset));
		} catch (UnsupportedEncodingException e) {
			throw new NetworkException(e);
		}
	}
}
