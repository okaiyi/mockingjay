package okaiyi.mockingjay.json;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * key,value形式的json
 *
 */
public class EntryJsonString extends JsonString{
	
	private Map<String, String> map;
	
	public EntryJsonString(Map<String, String> entry){
		this.map=entry;
	}
	/**
	 * 添加参数
	 * @param key
	 * @param value
	 */
	public void put(String key,String value){
		map.put(key, value);
	}
	@Override
	protected String buildToString() {
		JSONObject root = new JSONObject();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			String l = key;
			if (keyPolicy != null) {
				l = keyPolicy.replace(key);
			}
			String v = map.get(key).toString();
			root.put(l, v);
		}
		return root.toString();
	}
}
