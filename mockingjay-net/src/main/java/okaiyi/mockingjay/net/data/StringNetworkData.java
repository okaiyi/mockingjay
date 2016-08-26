package okaiyi.mockingjay.net.data;

import okaiyi.mockingjay.net.NetworkData;
public class StringNetworkData implements NetworkData<String>{
	private String string;
	public StringNetworkData(String string){
		this.string=string;
	}
	@Override
	public String getData() {
		return this.string;
	}
	
}
