package okaiyi.mockingjay.net.parser;

import java.io.IOException;
import java.io.InputStream;

public class DownloadInputstream extends InputStream {
	private InputStream in;
	private int available;

	public DownloadInputstream(InputStream in,int available) {
		this.in = in;
		this.available=available;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}
	@Override
	public int available() throws IOException {
		return available;
	}
}
