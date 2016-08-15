package okaiyi.mockingjay.json;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonStringBuilder {
	/**
	 * 空json数据
	 */
	public static final String EMPTY_JSON_DATA="{}";
	/**
	 * 将Map转换为json
	 * @param map
	 * @return
	 */
	public static final <K,V> JSONArray mapToJsonArray(Map<K,V> map){
		Set<K> keys=map.keySet();
		JSONArray array=new JSONArray();
		for(K k:keys){
			V v=map.get(k);
			JSONObject jo=new JSONObject();
			jo.put(k.toString(),v);
			array.put(jo);
		}
		return array;
	}
	
	
	
	public static final <T> JSONArray collectionToJsonArray(Collection<T> collection,String[] showMember){
		JSONArray array=new JSONArray();
		if(collection==null||collection.isEmpty()){
			return array;
		}
		Method[] methods=null;
		for(Iterator<T> it=collection.iterator();it.hasNext();){
			T obj=it.next();
			if(methods==null){
				methods=obj.getClass().getMethods();
			}
			JSONObject jo = new JSONObject();
			for(Method method:methods){
				String methodName=method.getName();
				boolean useMethod=false;
				//拦截get和is开头的方法名
				if(methodName.startsWith("get")||methodName.startsWith("is")){
					methodName=methodName.toLowerCase();
					if(showMember==null||showMember.length==0){
						useMethod=true;
					}else{
						
					}
				}
			}
		}
		/*Method[] methods=collection.iterator().next().getClass().getMethods();
		if(showMember==null){
			
		}*/
		return array;
	}
}
