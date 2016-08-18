package okaiyi.mockingjay.json.creator;

import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapJsonCreator<K,V> implements JsonArrayCreator{
	private Map<K, V> map;
	private String fieldName;
	private JsonValuePolicy jsonValuePolicy;
	public MapJsonCreator(Map<K,V> map,String fieldName,JsonValuePolicy jsonValuePolicy){
		this.map=map;
		this.fieldName=fieldName;
		this.jsonValuePolicy=jsonValuePolicy;
	}
	
	@Override
	public String buildToJsonString() {
		JSONObject jo=new JSONObject();
		if(map==null||map.isEmpty()){
			jo.put(fieldName, EMPTY_JSON_DATA);
		}else{
			jo.put(fieldName, getJsonArray());
		}
		return jo.toString();
	}

	@Override
	public JSONArray getJsonArray(){
		JSONArray array=new JSONArray();
		Set<K> keys=map.keySet();
		for(K key:keys){
			JSONObject jo=new JSONObject();
			Object value=map.get(key);
			if(value!=null&&jsonValuePolicy!=null){
				JsonCreator creator=jsonValuePolicy.getCreator(key.toString(), value);
				if(creator==null){
					value=value.toString();
				}else{
					if(creator!=null&&creator instanceof JsonArrayCreator){
						value=((JsonArrayCreator)creator).getJsonArray();
					}else{
						value=creator.buildToJsonString();
					}
				}
				jo.put(key.toString(), value);
			}
			array.put(jo);
		}
		return array;
	}
	
}
