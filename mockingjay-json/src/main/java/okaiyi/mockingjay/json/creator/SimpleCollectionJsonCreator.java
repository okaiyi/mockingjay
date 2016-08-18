package okaiyi.mockingjay.json.creator;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 处理基础类型 
 *
 * @param <T>
 */
public class SimpleCollectionJsonCreator<T> implements JsonArrayCreator{
	private Collection<T> collection;
	private String fieldName;
	public SimpleCollectionJsonCreator(Collection<T> collection,String fieldName){
		this.collection=collection;
		this.fieldName=fieldName;
	}
	
	@Override
	public String buildToJsonString() {
		JSONObject jo=new JSONObject();
		if(collection==null||collection.isEmpty()){
			jo.put(fieldName, EMPTY_JSON_DATA);	
		}else{
			jo.put(fieldName, collection);			
		}
		return jo.toString();
	}

	@Override
	public JSONArray getJsonArray(){
		JSONArray array=new JSONArray();
		array.put(collection);
		return array;
	}

}
