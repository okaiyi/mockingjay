package okaiyi.mockingjay.net.data;

import okaiyi.mockingjay.net.NetworkData;
/**
 * 文本标示的网络数据
 *
 */
public class TxtNetworkData implements NetworkData<String>{
	private String string;
	public TxtNetworkData(String string){
		this.string=string;
	}
	@Override
	public String getData() {
		return this.string;
	}
	
}
